README for ${projectTitle} developers
===============================================================================


Before you begin
-------------------------------------------------------------------------------

1.	You must generate the TextureAtlas assets before running the project
	for the first time. To do this, run the `compile` phase of the build:

		${artifactId}${symbol_dollar} mvn compile

		[INFO] --- exec-maven-plugin:1.2.1:java (pack-textures) @ ${artifactId}-core ---
		Packing....
		Writing 256x256: /path/to/${artifactId}/core/target/gdx/assets/textures.png

		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------

	If you're using IntelliJ IDEA, you can instead open the Maven Projects
	tab, navigate down to ${projectTitle} Parent > Lifecycle, then
	double-click on `compile`.

	You should do this every time you change anything inside the
	core/src/main/textures directory.


Assets
-------------------------------------------------------------------------------

There are two places assets can go:

*	__core/src/main/assets__: Any files placed in here will be available
	through Gdx.files.internal or AssetManager. Generally, anything that's
	not an image should go in here.

*	__core/src/main/textures__: Any images placed in here will automatically
	be packed into a TextureAtlas file named textures.pack, which in turn
	will be available through Gdx.files.internal or AssetManager. See the
	[TexturePacker](http://code.google.com/p/libgdx/wiki/TexturePacker)
	page on the LibGDX wiki for more information.


Distribution
-------------------------------------------------------------------------------

1.	If you are making a branch for this version in your source control system,
	now is a good time to do so.

2.	Change the version number in all four POMs. While this is technically
	optional, the version number will appear in your artifacts, so you are
	advised to set an appropriate user-facing version number. Note that it is
	a best practice to avoid reusing non-SNAPSHOT version numbers.

3.	Update the ProGuard map by running the `package` phase of the build:

		${artifactId}${symbol_dollar} mvn clean package

		[INFO] --- proguard-maven-plugin:2.0.6:proguard (default) @ ${artifactId}-core ---
		[INFO] execute ProGuard
		 [proguard] ProGuard, version 4.8
		 [proguard] Reading program jar [${artifactId}-core.jar] (filtered)
		 [proguard] Reading library jar [gdx.jar]
		 [proguard] Reading library jar [rt.jar]
		 [proguard] Preparing output jar [${artifactId}-core-min.jar]
		 [proguard]   Copying resources from program jar [${artifactId}-core.jar] (filtered)
		[INFO] ------------------------------------------------------------------------
		[INFO] Reactor Summary:
		[INFO]
		[INFO] ${projectTitle} Parent ................ SUCCESS [0.386s]
		[INFO] ${projectTitle} Core .................. SUCCESS [3.345s]
		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------

	You should now commit core/src/main/build/proguard_map.txt to source control.

4.	Run the `package` phase for all the targets you require:

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

5.	Collect the distribution artifacts from each target directory.

	* __desktop/target/${artifactId}-desktop-[version].zip__
	* __html/target/${artifactId}-html-[version].war__

6.	To deploy the html target, unzip the war file into any directory that is served
	out by a web server. You can also deploy the war file to any Servlet 3 container.

