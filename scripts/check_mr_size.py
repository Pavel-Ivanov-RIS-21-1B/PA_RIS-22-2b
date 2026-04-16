import os
import sys
import json
import subprocess
from github import Github

def get_branch_type(branch):
    if branch.startswith('feature/'):
        return 'feature', 300
    elif branch.startswith('refactor/'):
        return 'refactor', 400
    elif branch.startswith('bugfix/'):
        return 'bugfix', 150
    return None, None

def get_push_size():
    result = subprocess.run(
        ['git', 'diff', '--shortstat', 'HEAD~1', 'HEAD'],
        capture_output=True,
        text=True,
        shell=True
    )
    output = result.stdout
    import re
    match = re.search(r'(\d+) insertions?', output)
    additions = int(match.group(1)) if match else 0
    match = re.search(r'(\d+) deletions?', output)
    deletions = int(match.group(1)) if match else 0
    return additions + deletions

def main():
    with open(os.environ['GITHUB_EVENT_PATH']) as f:
        event = json.load(f)
    
    branch = None
    size = None
    
    if 'pull_request' in event:
        pr_number = event['pull_request']['number']
        branch = event['pull_request']['head']['ref']
        repo_name = os.environ['GITHUB_REPOSITORY']
        token = os.environ['GITHUB_TOKEN']
        g = Github(token)
        pr = g.get_repo(repo_name).get_pull(pr_number)
        size = pr.additions + pr.deletions
        print(f"PR #{pr_number}, branch: {branch}, changes: {size}")
        
    elif 'push' in event:
        branch = event['push']['ref'].replace('refs/heads/', '')
        size = get_push_size()
        print(f"Push to branch: {branch}, changes: {size}")
    
    branch_type, limit = get_branch_type(branch)
    if not branch_type:
        print(f"Skip: unknown branch type {branch}")
        sys.exit(0)
    
    if size > limit:
        print(f"ERROR: {size} lines exceeds limit {limit} for {branch_type}")
        sys.exit(1)
    
    print(f"OK: {size} lines within limit {limit}")
    sys.exit(0)

if __name__ == "__main__":
    main()
