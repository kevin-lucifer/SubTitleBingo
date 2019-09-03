package subtitle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.BlendMode;
import javafx.scene.web.WebView;
import subtitle.listener.HyperlinkRedirectListener;

/**
 * @author kevin
 * @version : PopUpController.java, v 0.1 2019年08月28日 4:51 PM kevin Exp $
 */
public class AboutController {

    public static void popUpAbout() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Hello", ButtonType.OK);

        WebView webView = new WebView();

        webView.getEngine().loadContent("<p>Hello~</p>\n"
                + "<p>&nbsp; &nbsp; Nice to know u. Follow my twitter/FB or leave me a message in git.We could Make a better&nbsp;world "
                + "together.</p>\n"
                + "<p>&nbsp; &nbsp;Twitter:&nbsp; &nbsp;&nbsp;<a href=\"https://twitter.com/kevinlucifer313\">https://twitter"
                + ".com/kevinlucifer313</a></p>\n"
                + "<p>&nbsp; &nbsp;Facebook:&nbsp;<a href=\"https://www.facebook.com/kevinlucifer84\">https://www.facebook"
                + ".com/kevinlucifer84</a></p>\n"
                + "<p>&nbsp; &nbsp;gitHub:&nbsp; &nbsp; &nbsp;<a href=\"https://github.com/kevin-lucifer/SubTitleBingo\">https://github"
                + ".com/kevin-lucifer/SubTitleBingo</a> &nbsp; &nbsp; &nbsp; &nbsp;</p>");

        String styleStr = AboutController.class.getClassLoader().getResource("MainWindow.css").toExternalForm();

        //open link in system default browser
        webView.getEngine().getLoadWorker().stateProperty().addListener(new HyperlinkRedirectListener(webView));

        webView.setBlendMode(BlendMode.DARKEN);
        webView.setPrefHeight(200);

        alert.setTitle("About");
        alert.getDialogPane().getStylesheets().add(styleStr);

        alert.getDialogPane().setContent(webView);
        alert.show();
    }

    @FXML
    public void initialize() {

    }
}