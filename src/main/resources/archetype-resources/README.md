#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

README for ${projectTitle} developers
===============================================================================


Before you begin
-------------------------------------------------------------------------------

1.	You must generate the TextureAtlas assets before running the project
	for the first time. To do this, run the _compile_ phase of the build:

		${artifactId}${symbol_dollar} mvn compile

		[INFO] --- exec-maven-plugin:1.2.1:java (pack-textures) @ ${artifactId}-core ---
		Packing....
		Writing 256x256: /path/to/${artifactId}/core/target/gdx/assets/textures.png

		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------

	If you're using IntelliJ IDEA, you can instead open the Maven Projects
	tab, navigate down to ${projectTitle} Parent > Lifecycle, then
	double-click on _compile_.

	You should do this every time you change anything inside the
	_core/src/main/textures_ directory.


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

	-noverify -javaagent:${symbol_dollar}{MAVEN_REPOSITORY}/org/springframework/springloaded/1.1.5.RELEASE/springloaded-1.1.5.RELEASE.jar

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


Troubleshooting
-------------------------------------------------------------------------------

${symbol_pound}${symbol_pound}${symbol_pound} (Linux) Help! I get an IOException at build time.

	Caused by: java.io.IOException: Cannot run program ".m2/repository/com/akathist/maven/plugins/launch4j/launch4j-maven-plugin/1.5.2/launch4j-maven-plugin-1.5.2-workdir-linux/bin/windres": error=2, No such file or directory

Launch4j ships as a 32-bit executable. You must install the 32-bit libc6 on
your system to run it.

On a modern multiarch distribution, `aptitude install libc6:i386` should take
care of it. See
[https://wiki.debian.org/Multiarch/HOWTO](https://wiki.debian.org/Multiarch/HOWTO)
for details.

On non-multiarch distributions, try looking for a package named something
like `ia32-libs`.
