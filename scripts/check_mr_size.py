# scripts/check_mr_size.py
import os
import sys
import json
from github import Github

def get_branch_type(branch):
    if branch.startswith('feature/'):
        return 'feature', 300
    elif branch.startswith('refactor/'):
        return 'refactor', 400
    elif branch.startswith('bugfix/'):
        return 'bugfix', 150
    return None, None

def main():
    with open(os.environ['GITHUB_EVENT_PATH']) as f:
        event = json.load(f)
        pr_number = event['pull_request']['number']
        branch = event['pull_request']['head']['ref']

    branch_type, limit = get_branch_type(branch)
    if not branch_type:
        print(f"Skip: unknown branch type {branch}")
        sys.exit(0)

    repo_name = os.environ['GITHUB_REPOSITORY']
    token = os.environ['GITHUB_TOKEN']
    
    g = Github(token)
    pr = g.get_repo(repo_name).get_pull(pr_number)
    size = pr.additions + pr.deletions

    print(f"Branch: {branch_type}, Changes: {size} lines, Limit: {limit}")

    if size > limit:
        print(f"ERROR: PR size exceeds limit for {branch_type}")
        sys.exit(1)
    
    print("OK")
    sys.exit(0)

if __name__ == "__main__":
    main()
