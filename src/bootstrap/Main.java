package bootstrap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentationLayer.MainViewController;

import java.io.IOException;

public class Main extends Application {
    public static final String WINDOW_TITLE = "DAOPattern Example";

    @Override
    public void start(Stage primaryStage) throws IOException {
        // FXML Datei laden und in das Haupt-Fenster laden
        Parent root = FXMLLoader.load(MainViewController.class.getResource("MainView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(532);
        primaryStage.setMinHeight(314);

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
