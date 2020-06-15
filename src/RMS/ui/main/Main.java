package RMS.ui.main;

import RMS.dataBase.DataBaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/RMS/ui/main/main.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Restaurant Management System");
        primaryStage.show();
        DataBaseHandler.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
