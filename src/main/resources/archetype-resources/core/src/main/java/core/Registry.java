#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

/** Registry and initializer of global resources */
public class Registry {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public final Game game;

	private boolean initialized = false;

	public Registry() {
		game = new Game(this);
	}

	/** Call exactly once in {@link Game#create()} */
	public void initialize() {
		if (initialized) {
			throw new IllegalStateException();
		}
		initialized = true;
	}

}
