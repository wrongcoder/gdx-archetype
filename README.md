# LibGDX Archetype

This [Maven](https://maven.apache.org/) archetype contains my current best practices for LibGDX projects. Only desktop and html targets are supported because those are the only ones that I use. You are warned that I am not a professional game developer.

## A demonstration

* [Run the html target](http://wrongcoder.github.io/gdx-archetype/demo/)
* [Download the desktop target](http://wrongcoder.github.io/gdx-archetype/demo/demo-desktop-1.0.0.BUILD-SNAPSHOT.zip)

## Some features

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
    * ```desktop/target/demo-desktop-1.0.0.BUILD-SNAPSHOT.zip```
    * ```html/target/demo-html-1.0.0.BUILD-SNAPSHOT.war```

## Credits

This archetype is loosely based on the original [LibGDX archetype](https://github.com/libgdx/libgdx-maven-archetype) and includes the same sample images, favicon.ico and libgdx-logo.png.  Thanks also to the contributors of the LibGDX wiki and various bloggers across the net.

