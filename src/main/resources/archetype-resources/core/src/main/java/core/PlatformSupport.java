#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core;

public interface PlatformSupport {

	public String getPlatformId();

	public void initializePlatform();

}
