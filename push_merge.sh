#!/bin/bash
git add .
git commit -m "docs: Add extensive codebase comments and docstrings for better open-source readability"
git push
gh pr merge --auto --merge
