package subtitle.handler;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import subtitle.controller.MainController;
import util.FileEncodeDetector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kevin
 * @version $Id: DropFileHandler.java, v 0.1 2019年08月10日 12:09 AM kevin Exp $
 */
public class DropFileHandler implements EventHandler<DragEvent> {

    private MainController controller;

    public DropFileHandler(MainController controller) {
        this.controller = controller;
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            File srtFile = db.getFiles().get(0);
            //System.err.println(srtFile.getParent());
            controller.getOriginalFileName().setText(srtFile.getName());
            controller.getNewFileName().setText(genDefaultNewFileName(srtFile.getName()));
            controller.setFullPath(srtFile.getParent());

            //根据猜测文件编码,更新编码列表
            try {
                List<Charset> guessCharSetList = FileEncodeDetector.guessCharset(srtFile);
                //清空原编码列表
                controller.getSrcEncSelector().getItems().clear();
                controller.getSrcEncSelector().setItems(
                        FXCollections.observableArrayList(guessCharSetList.stream().map(charset -> charset.name()).collect(
                                Collectors.toList())));
                //源文件编码方式
                controller.getSrcEncSelector().getSelectionModel().select(0);

                //目标文件编码方式
                controller.getTargetEncSelector().getSelectionModel().select(guessCharSetList.get(0).name());

            } catch (IOException e) {
                e.printStackTrace();
            }
            success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
    }

    private String genDefaultNewFileName(String oldName) {
        int index = oldName.lastIndexOf(".srt");
        if (index == -1) {
            index = oldName.lastIndexOf(".");
            if (index != -1) {
                return oldName.substring(0, index) + "_new" + oldName.substring(index);
            } else {
                return oldName + "_new";
            }
        }
        return oldName.substring(0, index) + "_new.srt";
    }
}
