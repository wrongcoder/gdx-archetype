#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.html;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import ${package}.core.PlatformSupport;
import ${package}.core.Registry;

public class HtmlDriver extends GwtApplication {

	private final Registry r = new Registry(new HtmlPlatformSupport());

	@Override
	public void onModuleLoad() {
		super.onModuleLoad();
		centreGameDisplay();
		removeLoadingMessage();
	}

	@Override
	public ApplicationListener getApplicationListener() {
		return r.game;
	}

	@Override
	public GwtApplicationConfiguration getConfig() {
		return new GwtApplicationConfiguration(Registry.WIDTH, Registry.HEIGHT);
	}

	private static void centreGameDisplay() {
		final Element tableElement = Document.get().getElementsByTagName("table").getItem(0);
		if (tableElement != null) {
			final String style = tableElement.getAttribute("style")
					+ " position: absolute;"
					+ " top: 50%; margin-top: -" + Registry.HEIGHT / 2 + "px;"
					+ " left: 50%; margin-left: -" + Registry.WIDTH / 2 + "px;";
			tableElement.setAttribute("style", style);
		}
	}

	private static void removeLoadingMessage() {
		final Element loadingElement = Document.get().getElementById("loading");
		if (loadingElement != null) {
			loadingElement.removeFromParent();
		}
	}

	private void setVersionString() {
		final Element versionElement = Document.get().getElementById("version");
		if (versionElement != null) {
			versionElement.setInnerText(r.game.getFullVersion());
		}
	}

	private static void markCanvasLoaded() {
		final Element bodyElement = Document.get().getElementsByTagName("body").getItem(0);
		if (bodyElement != null) {
			bodyElement.removeAttribute("style");
			bodyElement.addClassName("loaded");
		}
	}

	private static void removeInternetExplorerNotice() {
		final Element ie10Element = Document.get().getElementById("ie10");
		if (ie10Element != null) {
			ie10Element.removeFromParent();
		}
	}

	private class HtmlPlatformSupport implements PlatformSupport {

		@Override
		public void initializePlatform() {
			setVersionString();
			markCanvasLoaded();
			removeInternetExplorerNotice();
			Gdx.app.setLogLevel(Application.LOG_ERROR);
		}

	}

}
