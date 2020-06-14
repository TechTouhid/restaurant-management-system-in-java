package RMS.ui.manageOrder;

import RMS.dataBase.DataBaseHandler;
import RMS.ui.addMenu.AddMenuController;
import RMS.ui.addOrder.AddOrderController;
import RMS.ui.listMenu.ListMenuController;
import RMS.ui.listMenu.MenuListLoader;
import RMS.ui.manageMenu.ManageMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
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

public class ManageOrderController implements Initializable {
    ObservableList<Order> orderList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Order> manegeOrderTable;

    @FXML
    private TableColumn<Order, Integer> orderIDCol;

    @FXML
    private TableColumn<Order, String> staffFirsNameCol;

    @FXML
    private TableColumn<Order, String> staffLastNameCol;

    @FXML
    private TableColumn<Order, String> totalPriceCol;

    @FXML
    private JFXButton addOrderButton;

    @FXML
    private JFXButton editOrderButton;

    @FXML
    private JFXButton deleteOrderButton;

    @FXML
    private JFXButton refreshButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();
    }

    private void initCol() {
        orderIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        staffFirsNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        staffLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }
    private void loadData() {
        orderList.clear();
        DataBaseHandler handler = DataBaseHandler.getInstance();
        String qu = "SELECT * FROM order_table";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String firstName = rs.getString("staffFirstName");
                String lastName = rs.getString("staffLastName");
                String totalPrice = rs.getString("totalPrice");

                orderList.add(new Order(id.toString(), firstName,lastName, totalPrice));
            }

        } catch (SQLException e) {
            Logger.getLogger(ManageOrderController.class.getName()).log(Level.SEVERE, null, e);
        }

        manegeOrderTable.getItems().setAll(orderList);
    }
    public static class Order {
        private SimpleStringProperty id;
        private SimpleStringProperty firstName;
        private SimpleStringProperty lastName;
        private SimpleStringProperty totalPrice;

        public Order() {

        }
        public Order(String id, String firstName, String lastName, String totalPrice) {
            this.id = new SimpleStringProperty(id);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.totalPrice = new SimpleStringProperty(totalPrice);
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

        public String getTotalPrice() {
            return totalPrice.get();
        }
    }

    @FXML
    void addNewOrder(ActionEvent event) {
        loadWindow("/RMS/ui/addOrder/add_order.fxml", "Add New Order");
    }

    @FXML
    void deleteOrder(ActionEvent event) {
        Order selectedForEdit = manegeOrderTable.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No Order Selected");
            alert.setContentText("Please Select An Order");
            alert.showAndWait();
            return;
        }
        Alert alertx = new Alert(Alert.AlertType.CONFIRMATION);
        alertx.setHeaderText(null);
        alertx.setTitle("Deleting Menu Item");
        alertx.setContentText("Are you sure you want to delete the Menu " + selectedForEdit.getId() + " ?");
        Optional<ButtonType> answer = alertx.showAndWait();
        if (answer.get() == ButtonType.OK) {
            String deleteOrderData = "DELETE FROM edit_order_table WHERE orderId =" + selectedForEdit.getId();
            DataBaseHandler.getInstance().execAction(deleteOrderData);

            String deleteOrder = "DELETE FROM order_table WHERE id =" + selectedForEdit.getId();
            DataBaseHandler.getInstance().execAction(deleteOrder);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmation");
            alert.setContentText("Order Deleted");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Deletion Cancelled");
            alert.setContentText("Deletion Process Cancelled");
        }
    }

    @FXML
    void editOrder(ActionEvent event) {
        Order selectedForEdit = manegeOrderTable.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No Order Selected");
            alert.setContentText("Please Select An Order");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RMS/ui/addOrder/add_order.fxml"));
            Parent parent = loader.load();
            AddOrderController controller = (AddOrderController) loader.getController();
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
