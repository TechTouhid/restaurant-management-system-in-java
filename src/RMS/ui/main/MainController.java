package RMS.ui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainController implements Initializable {
    @FXML
    private MenuItem fileCloseMenu;

    @FXML
    private Button showMenuButton;

    @FXML
    private Button orderManageButton;

    @FXML
    private Button manageEmployeeButton;

    @FXML
    private Button manageMenuButton;

    @FXML
    private Button showPaymentButton;

    @FXML
    private Button settingButton;

    @FXML
    private Label loginAs;

    @FXML
    private StackPane stackPaneRoot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void fileCloseMenuFunc(ActionEvent event) {
        Stage stage = (Stage) stackPaneRoot.getScene().getWindow();
        stage.close();
    }

    @FXML
    void manageEmployee(ActionEvent event) {
        loadWindow("/RMS/ui/manageStaff/manage_staff.fxml", "Manage Employee");
    }

    @FXML
    void manageMenu(ActionEvent event) {
        loadWindow("/RMS/ui/manageMenu/manage_menu.fxml", "Manage Menu Item");
    }

    @FXML
    void orderManage(ActionEvent event) {
        loadWindow("/RMS/ui/manageOrder/manage_order.fxml", "Manage Order");
    }

    @FXML
    void setting(ActionEvent event) {

    }

    @FXML
    void showMenu(ActionEvent event) {
        loadWindow("/RMS/ui/listMenu/list_menu.fxml", "Manage Employee");
    }

    @FXML
    void showPayment(ActionEvent event) {

    }

    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene((parent)));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}