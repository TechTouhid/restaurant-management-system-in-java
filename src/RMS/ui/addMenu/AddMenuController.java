package RMS.ui.addMenu;

import RMS.dataBase.DataBaseHandler;
import RMS.ui.manageMenu.ManageMenuController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMenuController implements Initializable {
    ObservableList<String> listItems = FXCollections.observableArrayList(
            "Main", "Drink", "Alcohol", "Dessert");

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField menuID;

    @FXML
    private JFXTextField menuName;

    @FXML
    private JFXTextField menuPrice;

    @FXML
    private JFXComboBox<String> menuType;

    @FXML
    private JFXButton saveMenuButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton clearButton;

    private Boolean isInEditMode = Boolean.FALSE;

    DataBaseHandler dataBaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuType.setItems(listItems);
        dataBaseHandler = DataBaseHandler.getInstance();
    }

    @FXML
    void addMenu(ActionEvent event) {
        String mID = menuID.getText();
        String mName = menuName.getText();
        String mPrice = menuPrice.getText();
        String mType = menuType.getSelectionModel().getSelectedItem();

        if (mID.isEmpty() || mName.isEmpty() || mPrice.isEmpty() || mType.isEmpty()) {
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
        String qu = "INSERT INTO menu_item (id, name, price, menu_type) VALUE (" +
                "'" + mID + "'," +
                "'" + mName + "'," +
                "'" + mPrice + "'," +
                "'" + mType + "'" +
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
    void clearFields(ActionEvent event) {
        menuID.clear();
        menuName.clear();
        menuPrice.clear();
        menuType.getSelectionModel().clearSelection();
    }

    @FXML
    void cancelWindow(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void inflateUI(ManageMenuController.Menu menu) {
        menuID.setText(menu.getId());
        menuName.setText(menu.getName());
        menuPrice.setText(menu.getPrice());
        menuType.setValue(menu.getType());
        menuID.setEditable(false);
        isInEditMode = Boolean.TRUE;

    }

    private void handelEditMenuItem() {
        ManageMenuController.Menu menu = new ManageMenuController.Menu(menuID.getText(), menuName.getText(), menuPrice.getText(), menuType.getSelectionModel().getSelectedItem());
        if (dataBaseHandler.updateMenu(menu)) {
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
