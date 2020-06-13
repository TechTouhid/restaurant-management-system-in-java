package RMS.ui.manageStaff;

import RMS.dataBase.DataBaseHandler;
import RMS.ui.addMenu.AddMenuController;
import RMS.ui.addStaff.AddStaffController;
import RMS.ui.listMenu.ListMenuController;
import RMS.ui.listMenu.MenuListLoader;
import RMS.ui.manageMenu.ManageMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageStaffController implements Initializable {
    ObservableList<Staff> list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Staff> staffTableView;

    @FXML
    private TableColumn<Staff, String> idCol;

    @FXML
    private TableColumn<Staff, String> firstNameCol;

    @FXML
    private TableColumn<Staff, String> lastNameCol;

    @FXML
    private JFXButton addStaff;

    @FXML
    private JFXButton editStaff;

    @FXML
    private JFXButton deleteStaff;

    @FXML
    private JFXButton refreshButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();
    }
    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }

    private void loadData() {
        list.clear();
        DataBaseHandler handler = DataBaseHandler.getInstance();
        String qu = "SELECT * FROM staff";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String password = rs.getString("password");

                list.add(new Staff(id, firstName, lastName, password));
            }

        } catch (SQLException e) {
            Logger.getLogger(ListMenuController.class.getName()).log(Level.SEVERE, null, e);
        }

        staffTableView.getItems().setAll(list);
    }

    public static class Staff {
        private final SimpleStringProperty id;
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty password;

        public Staff(String id, String firstName, String lastName, String password) {
            this.id = new SimpleStringProperty(id);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.password = new SimpleStringProperty(password);
        }

        public String getId() {
            return id.get();
        }

        public String getFirstName() {
            return firstName.get();
        }

        public String getLastName() {
            return lastName.get();
        }

        public String getPassword() {
            return password.get();
        }
    }
    @FXML
    void addNewStaff(ActionEvent event) {
        loadWindow("/RMS/ui/addStaff/add_staff.fxml", "Add New Staff");

    }

    @FXML
    void deleteAnStaff(ActionEvent event) {
        Staff selectedForDelete = staffTableView.getSelectionModel().getSelectedItem();
        if (selectedForDelete == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("No Staff Selected");
            alert.setContentText("Please Select A Staff Member");
            alert.showAndWait();
            return;
        }

        Alert alertx = new Alert(Alert.AlertType.CONFIRMATION);
        alertx.setHeaderText(null);
        alertx.setTitle("Deleting staff");
        alertx.setContentText("Are you sure you want to delete the Menu " + selectedForDelete.getFirstName() + " " + selectedForDelete.getLastName() + " ?");
        Optional<ButtonType> answer = alertx.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DataBaseHandler.getInstance().DeleteStaff(selectedForDelete);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (result) {
                alert.setTitle("Confirmation");
                alert.setContentText("Staff Deleted");
                alert.showAndWait();
            } else {
                alert.setTitle("Failed");
                alert.setContentText("No Staff Selected");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Deletion Cancelled");
            alert.setContentText("Deletion Process Cancelled");

        }
    }

    @FXML
    void editStaff(ActionEvent event) {
        Staff selectedForEdit = staffTableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No Menu Item Selected");
            alert.setContentText("Please Select a Menu Item");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RMS/ui/addStaff/add_staff.fxml"));
            Parent parent = loader.load();
            AddStaffController controller = (AddStaffController) loader.getController();
            controller.inflateUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Staff");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(MenuListLoader.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void refreshData(ActionEvent event) {
        loadData();
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
