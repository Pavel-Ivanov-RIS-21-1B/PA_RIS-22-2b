const sqlite3 = require('sqlite3');
const db = new sqlite3.Database(':memory:');

db.serialize(() => {
  db.run("CREATE TABLE tasks (id INTEGER PRIMARY KEY, title TEXT, status TEXT)");
  db.run("CREATE TABLE comments (id INTEGER PRIMARY KEY, task_id INTEGER, text TEXT)");
  
  db.run("INSERT INTO tasks VALUES (1, 'Задача 1', 'open'), (2, 'Задача 2', 'done')");
  db.run("INSERT INTO comments VALUES (1, 1, 'Коммент к задаче 1')");
});

module.exports = db;
