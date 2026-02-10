# Commit Rules

## Commit Scope

- **Split commits by purpose**
  - Each commit should contain only one purpose (feature addition, bug fix, refactoring, etc.)
  - Do not combine multiple different purposes into one commit
  - Test code additions/modifications may be included in the same commit as the corresponding implementation code

## Commit Messages

- Write concisely and clearly
- Describe why the change was made, not just what was changed
- Write in Japanese
- Format: `<type>: <summary of changes>`
  - Type examples: `feat` (feature), `fix` (bug fix), `refactor` (refactoring), `test` (test addition), `docs` (documentation)

## Pre-Commit Checks

- Verify static analysis (Checkstyle, SpotBugs) passes
- Verify all unit tests succeed
- Do not include unnecessary files (IDE config files, build artifacts, etc.)

## Push

- **Always push after committing**
- Execute commit and push as a series of operations to prevent loss of work
- Ensure safety by always saving work to the remote repository
