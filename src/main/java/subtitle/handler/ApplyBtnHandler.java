
package subtitle.handler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import subtitle.controller.Controller;
import util.SubTitleFileHandler;

import java.nio.charset.Charset;

/**
 *
 * @author kevin
 * @version $Id: ApplyBtnHandler.java, v 0.1 2019年08月10日 12:56 AM kevin Exp $
 */
public class ApplyBtnHandler implements EventHandler<MouseEvent> {

    private Controller controller;

    public ApplyBtnHandler(Controller controller) {
        this.controller = controller;
    }

    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(MouseEvent event) {
        SubTitleFileHandler subTitleFileHandler;
        try {
            subTitleFileHandler = new SubTitleFileHandler(controller.getFullPath(), controller.getOriginalFileName().getText(),
                    controller.getNewFileName().getText(),
                    Charset.forName(controller.getSrcEncSelector().getSelectionModel().getSelectedItem()),
                    Charset.forName(controller.getTargetEncSelector().getSelectionModel().getSelectedItem()));
            subTitleFileHandler.handelFile(Integer.parseInt(controller.getOffset().getText().isBlank() ? "0" :
                    controller.getOffset().getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        event.consume();
    }
}