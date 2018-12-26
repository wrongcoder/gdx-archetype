#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

README for ${projectTitle} developers
===============================================================================


Before you begin
-------------------------------------------------------------------------------

1.	You must generate the TextureAtlas assets before running the project
	for the first time. To do this, run the _process-resources_ phase of
	the build:

		${artifactId}${symbol_dollar} mvn process-resources

		[INFO] --- exec-maven-plugin:1.3.2:java (pack-textures) @ ${artifactId}-core ---
		Packing....
		Writing 256x64: /path/to/${artifactId}/core/target/gdx/assets/textures.png

		[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ ${artifactId}-core ---
		[INFO] Using 'UTF-8' encoding to copy filtered resources.
		[INFO] Copying 1 resource to /path/to/${artifactId}/core/target/gdx/assets
		[INFO] Copying 26 resources to /path/to/${artifactId}/core/target/gdx/assets

		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------

	If you're using IntelliJ IDEA, you can instead open the Maven Projects
	tab, navigate down to ${projectTitle} Parent > Lifecycle, then
	double-click on _compile_.

	You should do this every time you change anything inside the
	_core/src/main/textures_ directory or delete the generated assets.


Assets
-------------------------------------------------------------------------------

There are two places assets can go:

*	__core/src/main/assets__: Any files placed in here will be available
	through Gdx.files.internal or AssetManager. Generally, anything that's
	not an image should go in here.

*	__core/src/main/textures__: Any images placed in here will automatically
	be packed into a TextureAtlas file named textures.pack, which in turn
	will be available through Gdx.files.internal or AssetManager. See the
	[TexturePacker](https://github.com/libgdx/libgdx/wiki/Texture-packer)
	page on the LibGDX wiki for more information.


Building
-------------------------------------------------------------------------------

This project has two build targets: _desktop_ and _html_. These targets are
tied to Maven profiles of the same name.

There is also an additional Maven profile named _dev_. The _dev_ profile puts
the GWT compiler into draft compile mode and enables the DETAILED output style.

To produce a typical development build, execute:

	${artifactId}${symbol_dollar} mvn -P desktop,html,dev clean package

Now you can pick up the build artifacts:

* desktop/target/${artifactId}-desktop-[version].zip
* html/target/${artifactId}-html-[version].war


Developing
-------------------------------------------------------------------------------

Execute the _${package}.desktop.DesktopDriver_ main class. Make
changes and recompile. Close the window then re-execute the main class to see
the changes take effect.

To avoid having to restart in some cases, your development environment may be
able to reload classes using
[JVM HotSwap](https://www.jetbrains.com/idea/webhelp/debugger-hotswap.html).

Alternatively, you could use the
[Spring Loaded](https://github.com/spring-projects/spring-loaded) Java agent
to reload modified class files automatically. To do this, pass these arguments
to the JVM:

	-noverify -javaagent:${symbol_dollar}{MAVEN_REPOSITORY}/org/springframework/springloaded/${springloadedVersion}/springloaded-${springloadedVersion}.jar

where `${symbol_dollar}{MAVEN_REPOSITORY}` is your local Maven repository, usually
_${symbol_dollar}HOME/.m2/repository_. (If you're using IntelliJ IDEA, the
`${symbol_dollar}{MAVEN_REPOSITORY}` will be replaced for you automatically.)


Testing the HTML target
-------------------------------------------------------------------------------

Generally, you should develop using the _desktop_ target, and just check the
_html_ target every once in a while. Developing using the GWT SuperDev mode
is another possibility.

To start up a web server to test the _html_ target, execute:

	${artifactId}${symbol_dollar} mvn -P html,dev clean package tomcat7:run

	[INFO] --- tomcat7-maven-plugin:2.1:run (default-cli) @ ${artifactId}-html ---
	[INFO] Running war on http://localhost:8080/${artifactId}
	[INFO] Creating Tomcat server configuration at /path/to/${artifactId}/html/target/tomcat
	[INFO] create webapp with contextPath: /${artifactId}
	Aug 10, 2013 6:10:23 PM org.apache.coyote.AbstractProtocol init
	INFO: Initializing ProtocolHandler ["http-bio-8080"]
	Aug 10, 2013 6:10:23 PM org.apache.catalina.core.StandardService startInternal
	INFO: Starting service Tomcat
	Aug 10, 2013 6:10:23 PM org.apache.catalina.core.StandardEngine startInternal
	INFO: Starting Servlet Engine: Apache Tomcat/7.0.37
	Aug 10, 2013 6:10:25 PM org.apache.coyote.AbstractProtocol start
	INFO: Starting ProtocolHandler ["http-bio-8080"]

Now you can load
[http://localhost:8080/${artifactId}](http://localhost:8080/${artifactId})
in your web browser.

Press ^C to stop the web server.


Generating font files (Hiero)
-------------------------------------------------------------------------------

You can use [Hiero](https://github.com/libgdx/libgdx/wiki/Hiero) to generate
additional font files (*.fnt/*.png) to use in your game.

To run Hiero, run the _exec:java_ goal with the _hiero_ profile activated:

	${artifactId}${symbol_dollar} mvn -P hiero exec:java


Distribution
-------------------------------------------------------------------------------

1.	If you are making a branch for this version in your source control system,
	now is a good time to do so.

2.	Change the version number in all four POMs. While this is technically
	optional, the version number will appear in your artifacts, so you are
	advised to set an appropriate user-facing version number. Note that it is
	a best practice to avoid reusing non-SNAPSHOT version numbers.

3.	Run the _package_ phase for all the targets you require:

		${artifactId}${symbol_dollar} mvn -P desktop,html package

		[INFO] ------------------------------------------------------------------------
		[INFO] Reactor Summary:
		[INFO]
		[INFO] ${projectTitle} Parent ............. SUCCESS [0.338s]
		[INFO] ${projectTitle} Core ............... SUCCESS [3.338s]
		[INFO] ${projectTitle} Desktop ............ SUCCESS [0.564s]
		[INFO] ${projectTitle} HTML ............... SUCCESS [1:24.175s]
		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------

    You should now commit _desktop/src/main/build/proguard_map.txt_
    to source control.

    Using the _dev_ profile is __not recommended__ for release builds.

4.	Collect the distribution artifacts from each target directory.

	* desktop/target/${artifactId}-desktop-[version].zip
	* html/target/${artifactId}-html-[version].war

5.	To deploy the html target, unzip the war file into any directory that is
	served out by a web server. You can also deploy the war file to any
	Servlet 3 container.
