#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

public interface PlatformSupport {

	public void initializePlatform();

	/** Hacks around libGDX issue 1640 */
	public float fixFloat(final float f);

}
