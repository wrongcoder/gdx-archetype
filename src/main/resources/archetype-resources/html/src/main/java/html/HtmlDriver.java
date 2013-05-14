#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.html;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import ${package}.core.Game;

public class HtmlDriver extends GwtApplication {

	@Override
	public void onModuleLoad() {
		super.onModuleLoad();
		Game.instance().setPostInit(new RemoveLoadingMessage());
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return Game.instance();
	}

	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(Game.WIDTH, Game.HEIGHT);
	}

	private static class RemoveLoadingMessage implements Runnable {
		@Override
		public void run() {
			final Element loadingElement = DOM.getElementById("loading");
			if (loadingElement != null) {
				loadingElement.removeFromParent();
			}
		}
	}

}
