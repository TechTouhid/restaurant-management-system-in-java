package RMS.ui.manageMenu;

import RMS.dataBase.DataBaseHandler;
import RMS.ui.addMenu.AddMenuController;
import RMS.ui.listMenu.ListMenuController;
import RMS.ui.listMenu.MenuListLoader;
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
import javafx.scene.control.cell.TextFieldTableCell;
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

public class ManageMenuController implements Initializable {
    ObservableList<Menu> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Menu> tableView;

    @FXML
    private TableColumn<Menu, String> idCol;

    @FXML
    private TableColumn<Menu, String> nameCol;

    @FXML
    private TableColumn<Menu, String> priceCol;

    @FXML
    private TableColumn<Menu, String> typeCol;

    @FXML
    private JFXButton addMenu;

    @FXML
    private JFXButton editMenu;

    @FXML
    private JFXButton deleteMenu;

    @FXML
    private JFXButton refreshButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // to make the table editable
//        tableView.setEditable(true);
//        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());

        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void loadData() {
        list.clear();
        DataBaseHandler handler = DataBaseHandler.getInstance();
        String qu = "SELECT * FROM menu_item";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String type = rs.getString("menu_type");

                list.add(new Menu(id, name, price, type));
            }

        } catch (SQLException e) {
            Logger.getLogger(ListMenuController.class.getName()).log(Level.SEVERE, null, e);
        }

        tableView.getItems().setAll(list);
    }

    public static class Menu {
        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty price;
        private final SimpleStringProperty type;

        public Menu(String id, String name, String price, String type) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleStringProperty(price);
            this.type = new SimpleStringProperty(type);
        }

        public String getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getPrice() {
            return price.get();
        }

        public String getType() {
            return type.get();
        }
    }

    @FXML
    void addNewMenuItem(ActionEvent event) {
        loadWindow("/RMS/ui/addMenu/add_menu.fxml", "Add New Menu Item");
    }

    @FXML
    void deleteMenuItem(ActionEvent event) {
        Menu selectedForDelete = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDelete == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("No Menu Item Selected");
            alert.setContentText("Please Select a Menu Item");
            alert.showAndWait();
            return;
        }

        Alert alertx = new Alert(Alert.AlertType.CONFIRMATION);
        alertx.setHeaderText(null);
        alertx.setTitle("Deleting Menu Item");
        alertx.setContentText("Are you sure you want to delete the Menu " + selectedForDelete.getName() + " ?");
        Optional<ButtonType> answer = alertx.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DataBaseHandler.getInstance().DeleteMenu(selectedForDelete);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (result) {
                alert.setTitle("Confirmation");
                alert.setContentText("Menu Item Deleted");
                alert.showAndWait();
            } else {
                alert.setTitle("Failed");
                alert.setContentText("No Menu Item Selected");
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
    void editMenuItem(ActionEvent event) {
        Menu selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No Menu Item Selected");
            alert.setContentText("Please Select a Menu Item");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RMS/ui/addMenu/add_menu.fxml"));
            Parent parent = loader.load();
            AddMenuController controller = (AddMenuController) loader.getController();
            controller.inflateUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Menu Item");
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
