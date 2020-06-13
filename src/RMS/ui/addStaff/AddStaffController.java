package RMS.ui.addStaff;

import RMS.dataBase.DataBaseHandler;
import RMS.ui.manageMenu.ManageMenuController;
import RMS.ui.manageStaff.ManageStaffController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStaffController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField stuffID;

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton saveStuffButton;

    @FXML
    private JFXButton clearButton;

    @FXML
    private JFXButton cancelButton;
    private Boolean isInEditMode = Boolean.FALSE;
    DataBaseHandler dataBaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataBaseHandler = DataBaseHandler.getInstance();
    }

    @FXML
    void addStuff(ActionEvent event) {
        String sID = stuffID.getText();
        String fName = firstName.getText();
        String lName = lastName.getText();
        String sPassword = password.getText();

        if (sID.isEmpty() || fName.isEmpty() || lName.isEmpty() || sPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Info in The Fields!");
            alert.showAndWait();
            return;
        }
        if (isInEditMode) {
            handelEditMenuItem();
            return;
        }
        String qu = "INSERT INTO staff (id, firstName, lastName, password) VALUE (" +
                "'" + sID + "'," +
                "'" + fName + "'," +
                "'" + lName + "'," +
                "'" + sPassword + "'" +
                ")";

        if (dataBaseHandler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }

    @FXML
    void cancelWindow(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clearFields(ActionEvent event) {
        stuffID.setText("");
        firstName.setText("");
        lastName.setText("");
        password.setText("");
    }

    public void inflateUI(ManageStaffController.Staff staff) {
        stuffID.setText(staff.getId());
        firstName.setText(staff.getFirstName());
        lastName.setText(staff.getLastName());
        password.setText(staff.getPassword());
        stuffID.setEditable(false);
        isInEditMode = Boolean.TRUE;
    }

    private void handelEditMenuItem() {
        ManageStaffController.Staff staff = new ManageStaffController.Staff(stuffID.getText(), firstName.getText(), lastName.getText(), password.getText());
        if (dataBaseHandler.updateStaff(staff)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Menu updated");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }
}

