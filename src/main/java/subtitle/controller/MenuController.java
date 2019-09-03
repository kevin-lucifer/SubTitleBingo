package subtitle.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * @author kevin
 * @version : MenuController.java, v 0.1 2019年08月28日 5:51 PM kevin Exp $
 */
public class MenuController {

    @FXML
    private VBox menuBarBox;

    @FXML
    public void initialize() {
        menuBarBox.setPadding(new Insets(0));
    }

    @FXML
    public void handleAboutAction(final ActionEvent event) {
        AboutController.popUpAbout();
    }

    @FXML
    public void handleExitAction(final ActionEvent event) {
        System.exit(0);
    }

    /**
     * Getter method for property <tt>menuBarBox</tt>.
     *
     * @return property value of menuBarBox
     */
    public VBox getMenuBarBox() {
        return menuBarBox;
    }

    /**
     * Setter method for property <tt>menuBarBox</tt>.
     *
     * @param menuBarBox value to be assigned to property menuBarBox
     */
    public void setMenuBarBox(VBox menuBarBox) {
        this.menuBarBox = menuBarBox;
    }
}