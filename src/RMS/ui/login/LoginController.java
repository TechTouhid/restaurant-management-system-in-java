package RMS.ui.login;

import RMS.dataBase.DataBaseHandler;
import RMS.ui.listMenu.ListMenuController;
import RMS.ui.main.MainController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField userID;

    @FXML
    private Label titleLabel;

    @FXML
    private JFXPasswordField password;
    DataBaseHandler dataBaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataBaseHandler = DataBaseHandler.getInstance();
    }

    @FXML
    void handelLoginButtonAction(ActionEvent event) {
        DataBaseHandler handler = DataBaseHandler.getInstance();
        titleLabel.setText("Restaurant Management Login");
        titleLabel.setStyle("-fx-background-color: black;-fx-text-fill: white");
        String uId = userID.getText();
        String pWord = password.getText();
        String quid = "SELECT id FROM staff WHERE id =" + "'" + uId + "'";
        String quPWard = "SELECT password FROM staff WHERE password =" + "'" + pWord + "'";
        if (uId.isEmpty() || pWord.isEmpty()) {
            titleLabel.setText("Fields are empty!");
            titleLabel.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white");
        }
        try {
            ResultSet rsId = handler.execQuery(quid);
            ResultSet rsPWord = handler.execQuery(quPWard);
            String userIdStr = "";
            String userPassword = "";
            while (rsId.next()) { userIdStr = rsId.getString("id");}
            while (rsPWord.next()) {userPassword = rsPWord.getString("password");}
            if (!uId.isEmpty() && !pWord.isEmpty()) {
                if (uId.equals(userIdStr) && pWord.equals(userPassword)) {
                    closeStage();
                    loadMain();
                } else {
                    titleLabel.setText("Invalid Credentials");
                    titleLabel.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white");
                }
            }
        }catch (SQLException e) {
            Logger.getLogger(ListMenuController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void handelCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    private void closeStage() {
        // getting current window using the element form that window
        ((Stage)userID.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RMS/ui/main/main.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Restaurant Management System");
            stage.setScene(new Scene((root)));
            stage.show();
            MainController mainController = loader.getController();
            mainController.transferMessage(userID.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
