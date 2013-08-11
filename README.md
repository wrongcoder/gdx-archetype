# LibGDX Archetype

This [Maven](https://maven.apache.org/) archetype contains my current best practices for [LibGDX](http://libgdx.badlogicgames.com/) projects. Only desktop and html targets are supported because those are the only ones that I use.

## What is it?

Professional software developers disagree about a lot of things that don't really matter. Very often, all choices are equally valid, but each developer prefers the one they proposed anyway.

In essence, this is my base code for [Ludum Dare](http://www.ludumdare.com/compo/). Having a properly working build system right off the bat means I'm not going to spend the first night (or longer) tweaking it to get rid of irritations. Having a bit of a starting point containing a few simple things I'd need to make any sort of game, named and laid out the way I like, is nice as well. On the other hand, having a ton of sample code or features included is not the point of this project.

## What is it not?

It's not a game engine, nor is it an advanced starting point for any particular type of game.

There are lots of excellent game engines out there, some of which are even based on LibGDX. If you're looking for one of those, [Fixel-GDX](https://github.com/flixel-gdx/flixel-gdx) looks pretty good. Check it out.

## A demonstration

* [Run the html target](http://wrongcoder.github.io/gdx-archetype/demo/)
* [Download the desktop target](http://wrongcoder.github.io/gdx-archetype/demo/demo-desktop-1.0.0.BUILD-SNAPSHOT.zip)

## Some features

* all: automatically executes TexturePacker2 as part of the generate-resources phase
* all: uses AssetManager and displays a loading screen while update() runs
* all: does not package source files inside the core JAR
* all: only writes generated resources inside target directories
* all: has cleaned up dependencyManagement and pluginManagement blocks
* desktop: includes a JRE re-launcher for JVMs that don't provide a big enough heap
* desktop: produces a ZIP containing a double-clickable JAR and a lib folder -- no more fat-JARs lumping everything together
* desktop: automatically passes the core module through ProGuard
* html: enables SoundMaanager2 click-to-flash support
* html: centres the game in the browser window at start
* html: displays a message if the game seems to be unable to load
* html: enables starting the HTML5 target in a test web server by running one command
* html: drops GAE support stuff (I consider this a feature)

## Quick start

1. Install the archetype

    ```shell
    $ mvn install
    ```

1. Generate a project using the archetype

    ```shell
    $ mvn archetype:generate -DarchetypeGroupId=com.vdxp -DarchetypeArtifactId=gdx-archetype

    [INFO] --- maven-archetype-plugin:2.2:generate (default-cli) @ standalone-pom ---
    [INFO] Generating project in Interactive mode
    [INFO] Archetype [com.vdxp:gdx-archetype:0.9.3.RELEASE] found in catalog local
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

    Copyright © 2013 wrongcoder

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

This archetype is loosely based on the original [LibGDX archetype](https://github.com/libgdx/libgdx-maven-archetype) and includes the same sample images, favicon.ico and libgdx-logo.png.

Thanks to Badlogic Games and contributors for their work on [LibGDX](http://libgdx.badlogicgames.com/). Thanks also to the contributors of the LibGDX wiki and various bloggers across the net.

