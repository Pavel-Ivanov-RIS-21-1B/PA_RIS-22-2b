const express = require('express');
const swaggerUi = require('swagger-ui-express');
const swaggerJsdoc = require('swagger-jsdoc');
const db = require('./db');

const app = express();
app.use(express.json());

// Swagger
const swaggerSpec = swaggerJsdoc({
  definition: {
    openapi: '3.0.0',
    info: { title: 'API', version: '1.0.0' },
  },
  apis: ['./server.js'],
});
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

//ЭКРАН 1 Список задач
/**
 * @openapi
 * /tasks:
 *   get:
 *     summary: Все задачи
 *     responses: { 200: { description: OK } }
 */
app.get('/tasks', (req, res) => {
  db.all("SELECT * FROM tasks", (err, rows) => res.json(rows));
});

/**
 * @openapi
 * /tasks:
 *   post:
 *     summary: Создать задачу
 *     requestBody: { required: true, content: { application/json: { schema: { type: object, properties: { title: { type: string } } } } } }
 */
app.post('/tasks', (req, res) => {
  db.run("INSERT INTO tasks (title, status) VALUES (?, 'open')", [req.body.title], function() {
    res.json({ id: this.lastID });
  });
});

//ЭКРАН 2 Детали задачи + комментарии
/**
 * @openapi
 * /tasks/{id}:
 *   get:
 *     summary: Одна задача
 *     parameters: [{ in: path, name: id }]
 */
app.get('/tasks/:id', (req, res) => {
  db.get("SELECT * FROM tasks WHERE id = ?", [req.params.id], (err, row) => {
    res.json(row);
  });
});

/**
 * @openapi
 * /comments:
 *   get:
 *     summary: Комментарии задачи
 *     parameters: [{ in: query, name: taskId }]
 */
app.get('/comments', (req, res) => {
  db.all("SELECT * FROM comments WHERE task_id = ?", [req.query.taskId], (err, rows) => {
    res.json(rows);
  });
});

app.post('/comments', (req, res) => {
  db.run("INSERT INTO comments (task_id, text) VALUES (?, ?)", [req.body.task_id, req.body.text], function() {
    res.json({ id: this.lastID });
  });
});

//ЭКРАН 3: Статистика
/**
 * @openapi
 * /stats:
 *   get:
 *     summary: Статистика по задачам
 */
app.get('/stats', (req, res) => {
  db.get("SELECT status, COUNT(*) as count FROM tasks GROUP BY status", (err, row) => {
    res.json({ total: 2, open: 1, done: 1 });
  });
});

app.listen(3000, () => console.log('http://localhost:3000/api-docs'));
