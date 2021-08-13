package RMS.ui.main;

import RMS.dataBase.DataBaseHandler;
import RMS.ui.addOrder.AddOrderController;
import RMS.ui.manageOrder.ManageOrderController;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        loadWindow("/RMS/ui/listMenu/list_menu.fxml", "Menu List");
    }

    @FXML
    void showPayment(ActionEvent event) {
        loadWindow("/RMS/ui/managePayment/manage_payment.fxml", "Manage Payment");
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
    public void transferMessage(String text) { ;
        DataBaseHandler handler = DataBaseHandler.getInstance();
        String qu = "SELECT * FROM `staff` WHERE id =" + "'" + text + "'";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String FirstName = rs.getString("firstName");
                String LastName = rs.getString("lastName");
                loginAs.setText(FirstName + " " + LastName);
            }
        } catch (SQLException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }

        // Transfer the data to Add order Controller
        AddOrderController addOrderController = new AddOrderController();
        addOrderController.manageInfo(text);

    }


}
