# SpotBugs Guide

Provides materials for using [SpotBugs](https://spotbugs.readthedocs.io/en/latest/index.html) in projects.

SpotBugs is the successor to FindBugs. 
Development of FindBugs has been stopped, but SpotBugs is a fork of the source code that supports Java 8 or later versions. 
SpotBugs is still being actively developed.
Using SpotBugs instead of FindBugs is recommended for projects that use Java 8 or later versions.

This repository contains documents such as configuration methods and operation rules.
The SpotBugs described in this repository incorporates [Unauthorized API Check Tool](../unpublished-api/README.md) as a plugin.
If you do not want to use Unauthorized API Check Tool, delete the plugin setting of the Unauthorized API Check Tool from the SpotBugs settings, and delete the description about the Unauthorized API Check Tool from the documentation.

- [SpotBugs usage guide](./docs/README.md)
- Sample project (see `spotbugs-example` directory)
