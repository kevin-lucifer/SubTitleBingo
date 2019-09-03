package subtitle.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import java.awt.*;
import java.net.URI;

/**
 * @author kevin
 * @version : HyperlinkRedirectListener.java, v 0.1 2019年09月03日 3:09 PM kevin Exp $
 */
public class HyperlinkRedirectListener implements ChangeListener<Worker.State>, EventListener {
    private static final String CLICK_EVENT = "click";
    private static final String ANCHOR_TAG  = "a";

    private final WebView webView;

    public HyperlinkRedirectListener(WebView webView) {
        this.webView = webView;
    }

    @Override
    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
        if (Worker.State.SUCCEEDED.equals(newValue)) {
            Document document = webView.getEngine().getDocument();
            NodeList anchors = document.getElementsByTagName(ANCHOR_TAG);
            for (int i = 0; i < anchors.getLength(); i++) {
                Node node = anchors.item(i);
                EventTarget eventTarget = (EventTarget) node;
                eventTarget.addEventListener(CLICK_EVENT, this, false);
            }
        }
    }

    @Override
    public void handleEvent(Event event) {
        HTMLAnchorElement anchorElement = (HTMLAnchorElement) event.getCurrentTarget();
        String href = anchorElement.getHref();

        if (Desktop.isDesktopSupported()) {
            openLinkInSystemBrowser(href);
        } else {
            System.err.println(String.format("OS does not support desktop operations like browsing. Cannot open link '{}'.", href));
        }

        event.preventDefault();
    }

    private void openLinkInSystemBrowser(String url) {
        System.out.println(String.format("Opening link '{}' in default system browser.", url));

        try {
            URI uri = new URI(url);
            Desktop.getDesktop().browse(uri);
        } catch (Throwable e) {
            System.err.println(String.format("Error on opening link '{}' in system browser.", url));
        }
    }
}
