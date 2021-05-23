package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fl = new FXMLLoader(getClass().getResource("window.fxml"));
            fl.setController(new ControllerMain(null, null));
            Parent p = fl.load();
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(p));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
