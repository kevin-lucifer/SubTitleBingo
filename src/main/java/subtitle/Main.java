package subtitle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("MainWindow.fxml")));
        primaryStage.setTitle("Subtitle Bingo");
        primaryStage.setScene(new Scene(root, 300, 320));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(380);
        primaryStage.show();
    }
}
