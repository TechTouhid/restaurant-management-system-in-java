package RMS;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(new StackPane());

        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();

        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(900);
        primaryStage.show();
    }
}

