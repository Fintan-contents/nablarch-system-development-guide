# Version Management Rules

## Branching rules

As a rule, it is in accordance with [git flow](https://nvie.com/posts/a-successful-git-branching-model/).

During development, you only need to be aware of the `develop` and `feature` branches.
You do not have to worry about the `master`,`release`, etc. during development.

The basic procedure is as follows.
- Create a `feature` branch from `develop`.
- When development of `feature` is completed, merge it into `develop`.
- If `feature` grows long, incorporate changes from `develop` as appropriate.

To create a feature branch, use `feature/<feature name>`.

`<Feature name>` is named from the function or story to be created.
A name that allows the content to be imagined is preferred, but as the change is described in the commit comment, there is no problem even if you cannot distinguish it with the branch name.


## Merge to develop branch

In the `develop` branch, merge only Pull Requests, which have been verified to work and reviewed.

The `develop`  branch is used by all developers. Embedding compile errors and bugs in this branch will hinder the work of all developers. 
For example, do not push the following code.

- Code that causes a compile error
- A configuration file that causes a format error (application cannot be started)

If you find that your commit has caused an error, take immediate action.
Remove the error as soon as possible; you can even change it to a comment. 
This is to avoid troubling other developers.


## Life of feature branch

If you extend the`feature` branch too long, the difference from the `develop`branch will increase and may cause problems when merging.
Do not increase the difference from `develop` as follows:

- Develop `feature` in small units and merge them to `develop`
- If the `feature` branch grows, incorporate the `develop` changes into the `feature` along the way

Before merging a `feature` branch into a `develop` branch, make sure that `develop` has not increased (by `git fetch`, `git pull`, etc.).
As a result of checking, if the difference of feature from `develop` is significant, import the `develop` into the `feature`, check the operation, and then merge it with `develop`.


## Prohibit force push

Force push is prohibited.

Do not try to rebase by force. It is okay even if the commit log can be messy.

The `develop` branch is set to prohibit force push in the GitBucket settings.
