# LibGDX Archetype

This Maven archetype contains my current best practices for LibGDX projects.  Only desktop and html targets are supported because those are the only ones that I use. You are warned that I am not a professional game developer.

A demonstration:

* [Run the html target](http://wrongcoder.github.io/gdx-archetype/demo/)
* [Download the desktop target](http://wrongcoder.github.io/gdx-archetype/demo/demo-desktop-1.0.0.BUILD-SNAPSHOT.zip)

Some features:

* all: executes TexturePacker2 as part of the generate-resources phase
* all: uses AssetManager with a placeholder loading screen
* all: does not package source files inside the core JAR
* all: only writes generated resources inside target directories
* all: cleaned up dependencyManagement and pluginManagement
* desktop: JRE re-launcher for JVMs that don't provide a big enough heap
* desktop: produces a ZIP containing a double-clickable JAR and a lib folder -- no more fat-JARs lumping everything together
* desktop: core module is passed through ProGuard
* html: displays a message if the game is taking a long time to load
* html: drops GAE support stuff (I consider this a feature)

This archetype is loosely based on the original [LibGDX archetype](https://github.com/libgdx/libgdx-maven-archetype) and includes the same sample images, favicon.ico and libgdx-logo.png.  Thanks also to the contributors of the LibGDX wiki and various bloggers across the net.

