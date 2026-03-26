#!/bin/bash

# Exit on error
set -e

# Initialize git if not already
git init
git checkout -b main || git branch -m main

# Create directories
mkdir -p .github/ISSUE_TEMPLATE .vscode

# 1. CONTRIBUTING.md
cat << 'MD' > CONTRIBUTING.md
# Contributing to Wave

Welcome! Wave is an Android NFC project built targeting developers who use VS Code.

## Development Environment
Since we use VS Code, to build the project, run the following from your terminal:
\`\`\`bash
./gradlew assembleDebug
\`\`\`

## Hardware Requirements
**Important:** You *must* test your changes on a physical Android device equipped with an NFC reader. Android emulators do not support NFC hardware emulation properly. Testing should be performed with physical NFC tags.
MD

# 2. CODE_OF_CONDUCT.md
cat << 'MD' > CODE_OF_CONDUCT.md
# Contributor Covenant Code of Conduct

## Our Pledge
We as members, contributors, and leaders pledge to make participation in our community a harassment-free experience for everyone.

## Our Standards
Examples of behavior that contributes to a positive environment for our community include:
* Demonstrating empathy and kindness toward other people
* Being respectful of differing opinions, viewpoints, and experiences
* Giving and gracefully accepting constructive feedback
MD

# 3. PULL_REQUEST_TEMPLATE.md
cat << 'MD' > .github/PULL_REQUEST_TEMPLATE.md
## Description
<!-- Describe your changes in detail here. -->

## Hardware Testing Details
Since emulators cannot be used for testing NFC, please provide your physical testing setup:
- **Android Device Model:** [e.g. Google Pixel 7]
- **Android OS Version:** [e.g. Android 14]
- **NFC Tag Type Used:** [e.g. NTAG215]

## Checklist
- [ ] I have tested this code on a physical Android device with a real NFC tag.
- [ ] My code follows the VS Code auto-formatting rules outlined in this repository.
MD

# 4. BUG_REPORT.md
cat << 'MD' > .github/ISSUE_TEMPLATE/bug_report.md
---
name: Bug report
about: Create a report to help us improve Wave
title: ''
labels: bug
assignees: ''
---

**Describe the bug**
A clear and concise description of what the bug is.

**Hardware Information:**
- Device: [e.g. Pixel 6]
- OS Version: [e.g. Android 13]
- NFC Tag Type: [e.g. NTAG213]
MD

# 5. FEATURE_REQUEST.md
cat << 'MD' > .github/ISSUE_TEMPLATE/feature_request.md
---
name: Feature request
about: Suggest an idea for this project
title: ''
labels: enhancement
assignees: ''
---

**Is your feature request related to a problem?**
A clear and concise description of what the problem is.

**Describe the solution you'd like**
A clear and concise description of what you want to happen.
MD

# 6. .vscode/extensions.json
cat << 'JSON' > .vscode/extensions.json
{
  "recommendations": [
    "fwcd.kotlin",
    "mathiasfrohlich.Kotlin",
    "vscjava.vscode-java-pack",
    "richardwillis.vscode-gradle"
  ]
}
JSON

# 7. .vscode/settings.json
cat << 'JSON' > .vscode/settings.json
{
  "editor.formatOnSave": true,
  "editor.codeActionsOnSave": {
    "source.organizeImports": "explicit"
  },
  "[kotlin]": {
    "editor.defaultFormatter": "fwcd.kotlin"
  },
  "[java]": {
    "editor.defaultFormatter": "redhat.java"
  }
}
JSON

# Git operations
git add .
git commit -m "chore: initial open-source setup and community guardrails" || true

# Create / push repo using GH CLI
gh repo create Wave --public --source=. --remote=origin --push || echo "Repo creation failed or already exists"

# Assign GH owner for the repo (assume authenticated user)
OWNER=$(gh api user -q .login)

# Protect main branch
gh api -X PUT "repos/$OWNER/Wave/branches/main/protection" \
  -H "Accept: application/vnd.github.v3+json" \
  -f required_status_checks=null \
  -f enforce_admins=true \
  -f required_pull_request_reviews[required_approving_review_count]=1 \
  -f required_pull_request_reviews[dismiss_stale_reviews]=true \
  -f required_pull_request_reviews[require_code_owner_reviews]=false \
  -f restrictions=null \
  -f allow_force_pushes=false

echo "DONE"
