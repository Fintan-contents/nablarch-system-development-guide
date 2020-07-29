# Nablarch Style Guide

This repository provides coding conventions for Java and other languages, and an introduction guide to static analysis tools such as Checkstyle and SpotBugs.

- [Java Style Guide](./java/README.md)
- [JavaScript Style Guide](./js/README.md)
- [JSP Style Guide](./jsp/README.md)
- [SQL Style Guide](./sql/README.md)
- [Shell Script Style Guide](./shell/README.md)

## License

![](https://i.creativecommons.org/l/by-sa/4.0/88x31.png)<br/>
The documents in this repository are provided under the [Creative Commons Attribution-ShareAlike 4.0 International License](http://creativecommons.org/licenses/by-sa/4.0/).

The following files and sample projects are provided under the [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt). 

- [Java code formatter](./java/assets/nablarch-code-formatter.xml)
- Sample projects stored under `java/staticanalysis/checkstyle/checkstyle-example/`. 
- Sample projects stored under `java/staticanalysis/spotbugs/spotbugs-example/`.

## Customize the style guide

While this repository will provide a complete guide to help you get started with your project, depending on the project, you may want to add more conventions or remove unnecessary conventions.

In such cases, you are free to copy and customize this repository.

Describes how to customize the project when you are using GitHub or using an in-house Git repository.

### Fork with GitHub and customize

This section described how to customize your project when you are using GitHub.

First, fork this repository.
There is a button marked fork at the top right of this repository; please press the button to fork.

Customize the newly created repository by forking.

### Customize by copying to the internal Git repository of the project

This section describes how to customize a project that does not use GitHub and has an in-house Git repository.
Here, as an example, let us assume that the project uses GitBucket.

First, create an empty repository on the in-house GitBucket. 
Let us assume that you have created an empty repository named `nablarch-style-guide`.

Then, `clone` this repository.

```console
git clone https://github.com/nablarch-development-standards/nablarch-style-guide.git
```

Add the empty repository created in the in-house GitBucket as a remote repository to the local repository created by `clone`.

```console
git remote add intra http://localhost:8080/git/example-group/nablarch-style-guide.git
```

Finally, `push` to the remote repository that you added.

```console
git push intra master
```

The content of this repository is now copied to the repository created on the in-house GitBucket.
Customize the copied repository.

## Version

The version of this style guide is represented by connecting the major and minor versions with a period.
The major version is incremented when a style guide for a new language is added or when a new item is added to the Java coding conventions. 
The minor version will be incremented when typographical errors are corrected or the expression is changed without changing the meaning.

For change history, refer to [CHANGELOG.md](./CHANGELOG.md).

