package RMS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
////        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//        primaryStage.setTitle("Restaurant Management System");
//        primaryStage.setScene(new Scene(root, 850, 500));
//        primaryStage.show();
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}


public class Main extends Application {
    public static void main(String[] args) { launch(args); }
    @Override public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new StackPane());

        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();

        stage.setScene(scene);
        stage.show();
    }
}
