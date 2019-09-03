package subtitle.controller;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.TextFields;
import subtitle.handler.ApplyBtnHandler;
import subtitle.handler.DropFileHandler;
import util.EncodingUtil;

public class MainController {
    @FXML
    private Pane             functionPane;
    @FXML
    private TextField        newFileName;
    @FXML
    private TextField        originalFileName;
    @FXML
    private TextField        offset;
    @FXML
    private Button           apply;
    @FXML
    private ComboBox<String> srcEncSelector;
    @FXML
    private ComboBox<String> targetEncSelector;
    private String           fullPath;

    public final ObjectProperty<EventHandler<? super DragEvent>> onDragDroppedProperty() {
        //do nothing
        return null;
    }

    public void onDropedFile() {
        functionPane.onDragDroppedProperty();
    }

    @FXML
    private void initialize() {
        functionPane.setOnDragOver(event -> {
                    //System.err.println("in on drag over");
                    if (event.getGestureSource() != functionPane
                            && event.getDragboard().hasFiles()) {
                        /* allow for both copying and moving, whatever user chooses */
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
        );
        functionPane.setOnDragDropped(new DropFileHandler(this));
        apply.setOnMouseClicked(new ApplyBtnHandler(this));

        this.srcEncSelector.setItems(EncodingUtil.getSupportedEncodingList());
        this.srcEncSelector.setEditable(true);
        TextFields.bindAutoCompletion(srcEncSelector.getEditor(), srcEncSelector.getItems());

        this.targetEncSelector.setItems(EncodingUtil.getSupportedEncodingList());
        this.targetEncSelector.setEditable(true);
        TextFields.bindAutoCompletion(targetEncSelector.getEditor(), targetEncSelector.getItems());

        this.srcEncSelector.getSelectionModel().select("GB18030");
        this.targetEncSelector.getSelectionModel().select("GB18030");

    }

    public Pane getFunctionPane() {
        return functionPane;
    }

    public void setFunctionPane(Pane functionPane) {
        this.functionPane = functionPane;
    }

    public TextField getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(TextField newFileName) {
        this.newFileName = newFileName;
    }

    public TextField getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(TextField originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Button getApply() {
        return apply;
    }

    public void setApply(Button apply) {
        this.apply = apply;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public TextField getOffset() {
        return offset;
    }

    public void setOffset(TextField offset) {
        this.offset = offset;
    }

    /**
     * Getter method for property <tt>srcEncSelector</tt>.
     *
     * @return property value of srcEncSelector
     */
    public ComboBox<String> getSrcEncSelector() {
        return srcEncSelector;
    }

    /**
     * Setter method for property <tt>srcEncSelector</tt>.
     *
     * @param srcEncSelector value to be assigned to property srcEncSelector
     */
    public void setSrcEncSelector(ComboBox<String> srcEncSelector) {
        this.srcEncSelector = srcEncSelector;
    }

    /**
     * Getter method for property <tt>targetEncSelector</tt>.
     *
     * @return property value of targetEncSelector
     */
    public ComboBox<String> getTargetEncSelector() {
        return targetEncSelector;
    }

    /**
     * Setter method for property <tt>targetEncSelector</tt>.
     *
     * @param targetEncSelector value to be assigned to property targetEncSelector
     */
    public void setTargetEncSelector(ComboBox<String> targetEncSelector) {
        this.targetEncSelector = targetEncSelector;
    }
}
