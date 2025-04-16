# Contributing to poibox

Thank you for your interest in contributing to poibox! This document provides guidelines to help you contribute effectively.

**Translations:**
*   [한국어 (Korean)](CONTRIBUTING.ko-kr.md)

## Getting Started

*   **Fetch the Code:** Get the latest code from the `main` branch.
    ```bash
    git clone https://github.com/jeng832/poibox.git
    cd poibox
    ```
*   **Check Issues:** Before starting work, it's a good idea to check GitHub Issues for existing relevant issues or create a new one to discuss your plans.

## Branching Policy

We use a simplified Gitflow model centered around the `main` branch.

### Core Branches

*   **`main`**: Always holds stable, production-ready code. Direct commits are prohibited; only merges from `release` or `hotfix` branches are allowed. All release versions are tagged from this branch.
*   **`feature/*`**: Used for developing new features, refactoring, or non-critical bug fixes. Branch off from `main` and submit a Pull Request (PR) back to `main` upon completion. (e.g., `feature/add-new-feature`, `feature/ISSUE-123-fix-bug`)
*   **`release/*`**: Used to prepare for new production releases. Branch off from `main` to handle version bumps, final testing, and documentation updates. Merge back into `main` upon completion and create a tag. (e.g., `release/1.0.0`)
*   **`hotfix/*`**: Used to address urgent bugs in the deployed `main` branch. Branch off from `main`, make the fix, and merge back into `main` and any ongoing `release` branch if necessary. (e.g., `hotfix/critical-bug-fix`)

### Development Workflow (`feature` Branch)

1.  Create a new `feature` branch from the latest `main`. (`git checkout -b feature/your-feature-name origin/main`)
2.  Develop the feature on your branch, making logical commits. (See Commit Message Guidelines)
3.  (Recommended) Periodically rebase your branch onto `origin/main` to stay up-to-date. (`git fetch origin`, `git rebase origin/main`)
4.  Push your completed feature branch to the remote repository. (`git push -u origin feature/your-feature-name`)
5.  Open a Pull Request (PR) targeting the `main` branch.
6.  Address any feedback from code reviews.
7.  Once approved and CI checks pass, merge the PR into `main` (Squash and merge recommended).
8.  Delete the feature branch after merging.

### Release Workflow (`release` Branch)

1.  Create a `release` branch from the `main` branch at the point you want to release. (`git checkout -b release/version-number origin/main`)
2.  Perform release preparations on the `release` branch (version updates, final tests, docs) and commit.
3.  Once ready, merge the `release` branch into `main`. (`git checkout main`, `git merge --no-ff release/version-number`)
4.  Tag the merge commit on `main` with the version number and push the tag. (`git tag -a vX.Y.Z -m "Release version X.Y.Z"`, `git push origin vX.Y.Z`)
5.  (Optional) The `release` branch can be kept or deleted.
6.  Deploy to Maven Central, etc.
7.  Ensure ongoing `feature` branches are updated with the latest changes from `main` (rebase).

## Commit Message Guidelines

We recommend following the [Conventional Commits](https://www.conventionalcommits.org/) format.

*   **Types**: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
*   **Format**: `<type>[optional scope]: <description>` (e.g., `feat: Add support for vertical headers`, `fix(parser): Correct date parsing logic`)

## Pull Request (PR) Process

*   Target PRs against the `main` branch.
*   Clearly describe your changes in the PR title and body (what and why).
*   Link to relevant issues in the body (`Closes #123`).
*   Request code reviews and address feedback diligently.
*   Ensure all CI checks pass and reviews are approved before merging. We recommend **Squash and merge** for `feature` branches.

## Bug Reports and Feature Requests

*   Please use GitHub Issues to report bugs or suggest new features.
*   Search existing issues before creating a new one.

Thank you! 