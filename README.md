# LibGDX Archetype

This [Maven](https://maven.apache.org/) [archetype](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html) contains my current best practices for [LibGDX](http://libgdx.badlogicgames.com/) projects. Only desktop and html targets are supported because those are the only ones that I use.

## Ludum Dare!

It's the end of Submission Hour. The entry has been submitted. You've made some last-minute changes. Quick, make the final build!

```shell
$ mvn -P desktop,html package
$ scp desktop/target/game.zip html/target/game.war webhost.example.com:
$ ssh webhost.example.com unzip -d LD48 game.war
```

## What is it?

Tabs or spaces? Gradle or Maven? Screen or MyScreen? Streamlined bare-bones basics or full-blown demo project?

Professional software developers disagree about a lot of things that don't really matter. Very often, all choices are equally valid, but each developer prefers the one that they proposed anyway.

This is my base code for [Ludum Dare](http://www.ludumdare.com/compo/). Having a properly working build system right off the bat means I don't have to spend the first night (or longer) tweaking the build to get rid of irritations. Having a bit of a starting point, containing a few simple things I'd need to make any sort of game, named and laid out the way I like, is nice as well.

On the other hand, having a ton of sample code or features included is not the point of this project.

## What is it not?

It's not a complete game engine, at least not any more than LibGDX itself is, nor is it an advanced starting point for any particular type of game.

It's not the way to start projects that is recommended by the LibGDX developers. See the [LibGDX Wiki](https://github.com/libgdx/libgdx/wiki/) for their [Gradle-based setup guide](https://github.com/libgdx/libgdx/wiki/Project-Setup-Gradle).

There are lots of excellent game engines out there, some of which are even based on LibGDX. If you're looking for one of those, [Fixel-GDX](https://github.com/flixel-gdx/flixel-gdx) looks pretty good. Check it out. And there's nothing wrong with [Unity3D](http://unity3d.com/unity/download/) or [GameMaker](http://www.yoyogames.com/studio) or anything else, either. Use whatever you like.

## A demonstration

I've put a lot of effort into the fit and finish of projects generated from this archetype, especially around convenient distribution and compatibility. You should find that both of these targets are easy to use on your computer.

* [Run the html target](http://wrongcoder.github.io/gdx-archetype/demo/)
* [Download the desktop target](http://wrongcoder.github.io/gdx-archetype/demo/demo-desktop-1.0.0.BUILD-SNAPSHOT.zip)

On a real game project, the html target may feel a bit slower than the desktop target. This can be mitigated by profiling and optimizing hot spots, like for any performance-sensitive piece of software.

## Some features

* all: automatically executes TexturePacker2 as part of the generate-resources phase
* all: uses AssetManager and displays a loading screen while update() runs
* all: does not package source files inside the core JAR
* all: only writes generated resources inside target directories
* all: has cleaned up dependencyManagement and pluginManagement blocks
* desktop: includes a Launch4j EXE to help Windows users locate a JRE
* desktop: includes a JRE re-launcher for ancient JVMs that don't provide a big enough heap
* desktop: produces a ZIP containing a double-clickable JAR and a lib folder (no more huge fat-JARs)
* desktop: automatically passes the core module through ProGuard
* html: enables SoundManager2 click-to-flash support
* html: centres the game in the browser window at start
* html: displays a message if the game is unable to load
* html: can start the HTML5 target in a test web server by running one command
* html: drops GAE support stuff (I consider this a feature)

## Quick start

1. Generate a project using the archetype

    ```shell
    $ mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate -DarchetypeGroupId=com.vdxp.gdx -DarchetypeArtifactId=gdx-archetype -DarchetypeCatalog=https://raw.githubusercontent.com/wrongcoder/maven/master/

    [INFO] --- maven-archetype-plugin:2.2:generate (default-cli) @ standalone-pom ---
    [INFO] Generating project in Interactive mode
    [INFO] Archetype [com.vdxp.gdx:gdx-archetype:1.1.0.RELEASE] found in catalog https://raw.githubusercontent.com/wrongcoder/maven/master/
    [INFO] Using property: groupId = com.example.demo
    [INFO] Using property: artifactId = demo
    [INFO] Using property: version = 1.0.0.BUILD-SNAPSHOT
    [INFO] Using property: package = com.example.demo
    [INFO] Using property: projectTitle = Demo Project
    ```

1. Build the generated project

    ```shell
    $ cd demo
    $ mvn -P desktop,html package

    [INFO] ------------------------------------------------------------------------
    [INFO] Reactor Summary:
    [INFO] 
    [INFO] Demo Project Parent ............................... SUCCESS [0.237s]
    [INFO] Demo Project Core ................................. SUCCESS [3.151s]
    [INFO] Demo Project Desktop .............................. SUCCESS [0.880s]
    [INFO] Demo Project HTML ................................. SUCCESS [2:03.167s]
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    ```

1. Collect the build artifacts
    * `desktop/target/demo-desktop-1.0.0.BUILD-SNAPSHOT.zip`
    * `html/target/demo-html-1.0.0.BUILD-SNAPSHOT.war`

1. Read the generated [README file](src/main/resources/archetype-resources/README.md) for more information

## License

Everything original to this LibGDX Archetype project is:

    Copyright © 2018 wrongcoder

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## Credits

This archetype is loosely based on the original [LibGDX archetype](https://github.com/libgdx/libgdx-maven-archetype) and includes the same sample image libgdx-logo.png.

Thanks to [Badlogic Games](http://www.badlogicgames.com) and contributors for their work on [LibGDX](http://libgdx.badlogicgames.com/). Other than the relatively minor polish introduced by this project, everything is due to the capabilities of this library. Thanks also to the contributors of the LibGDX wiki and various bloggers across the net for explaining how to use it.

## Why haven't you merged your changes into the original libgdx-maven-archetype?

I simply haven't found the right opportunity to do it, especially given how much these projects have diverged in both purpose and code. If you have the time and expertise to port anything from here to there, or vice versa, both projects can only benefit from your efforts. Please contact me if there is anything I can do to facilitate this.
