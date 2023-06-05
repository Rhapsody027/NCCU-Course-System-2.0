// package nccu;

// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.web.WebEngine;
// import javafx.scene.web.WebView;
// import javafx.stage.Stage;

// import static
// com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

// import com.teamdev.jxbrowser.browser.Browser;
// import com.teamdev.jxbrowser.engine.Engine;
// import com.teamdev.jxbrowser.engine.EngineOptions;
// import com.teamdev.jxbrowser.engine.RenderingMode;
// import com.teamdev.jxbrowser.view.javafx.BrowserView;

// public class webTest extends Application {

// String url = "https://www.facebook.com/";

// public static void main(String[] args) {
// launch();

// }

// @Override
// public void start(Stage stage) throws Exception {
// WebView webView = new WebView();
// WebEngine webEngine = webView.getEngine();
// webEngine.load(url);

// try (Engine engine =
// Engine.newInstance(EngineOptions.newBuilder(RenderingMode.OFF_SCREEN).build()))
// {
// Browser browser = engine.newBrowser();
// BrowserView webView = BrowserView.newInstance(browser);

// Scene scene = new Scene(webView, 640, 480);
// stage.setScene(scene);
// stage.show();

// browser.navigation().loadUrl(
// "https://newdoc.nccu.edu.tw/teaschm/1112/schmPrv.jsp-yy=111&smt=2&num=031004&gop=01&s=1.html");
// }

// }
// }
