package RMS.ui.managePayment;

import RMS.dataBase.DataBaseHandler;
import RMS.ui.addOrder.AddOrderController;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagePaymentController implements Initializable {
    ObservableList<PayOrder> orderList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<PayOrder> managePaymentTable;

    @FXML
    private TableColumn<PayOrder, Integer> orderIDCol;

    @FXML
    private TableColumn<PayOrder, String> staffFirsNameCol;

    @FXML
    private TableColumn<PayOrder, String> staffLastNameCol;

    @FXML
    private TableColumn<PayOrder, String> totalPriceCol;

    @FXML
    private TableColumn<PayOrder, String> isPaidCol;

    @FXML
    private JFXButton addOrderButton;

    @FXML
    private JFXButton editOrderButton;

    @FXML
    private JFXButton deleteOrderButton;

    @FXML
    private JFXButton refreshButton;

    @FXML
    private JFXButton confirmPayButton;

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
        isPaidCol.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
    }
    private void loadData() {
        orderList.clear();
        String paidValue;
        DataBaseHandler handler = DataBaseHandler.getInstance();
        String qu = "SELECT * FROM order_table";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("staffFirstName");
                String lastName = rs.getString("staffLastName");
                String totalPrice = rs.getString("totalPrice");
                boolean payInfo = rs.getBoolean("isPaid");
                if (payInfo) {
                    paidValue = "Paid";
                } else {
                    paidValue = "Not Paid";
                }
                orderList.add(new PayOrder(Integer.toString(id), firstName, lastName, totalPrice, paidValue));
            }

        } catch (SQLException e) {
            Logger.getLogger(ManagePaymentController.class.getName()).log(Level.SEVERE, null, e);
        }

        managePaymentTable.getItems().setAll(orderList);
    }
    public static class PayOrder {
        private SimpleStringProperty id;
        private SimpleStringProperty firstName;
        private SimpleStringProperty lastName;
        private SimpleStringProperty totalPrice;
        private SimpleStringProperty isPaid;

        public PayOrder() {

        }

        public PayOrder(String id, String firstName, String lastName, String totalPrice, String isPaid) {
            this.id = new SimpleStringProperty(id);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.totalPrice = new SimpleStringProperty(totalPrice);
            this.isPaid = new SimpleStringProperty(isPaid);

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

        public String  getIsPaid() {
            return isPaid.get();
        }
    }

    @FXML
    void confirmPay(ActionEvent event) throws SQLException {
        PayOrder selectedForPay = managePaymentTable.getSelectionModel().getSelectedItem();
        if (selectedForPay == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No Order Selected");
            alert.setContentText("Please Select An Order");
            alert.showAndWait();
            return;
        }

        DataBaseHandler handler = DataBaseHandler.getInstance();
        String qu = "UPDATE `order_table` SET `isPaid` = " + true + " WHERE id =" + selectedForPay.getId();
        if (handler.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmation");
            alert.setContentText("Payment Confirm");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Failed");
            alert.showAndWait();
        }

    }

    @FXML
    void deleteOrder(ActionEvent event) {
        PayOrder selectedForDelete= managePaymentTable.getSelectionModel().getSelectedItem();
        if (selectedForDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No Order Selected");
            alert.setContentText("Please Select An Order");
            alert.showAndWait();
            return;
        }
        Alert alertx = new Alert(Alert.AlertType.CONFIRMATION);
        alertx.setHeaderText(null);
        alertx.setTitle("Deleting Order");
        alertx.setContentText("Are you sure you want to delete Order " + selectedForDelete.getId() + " ?");
        Optional<ButtonType> answer = alertx.showAndWait();
        if (answer.get() == ButtonType.OK) {
            String deleteOrderData = "DELETE FROM edit_order_table WHERE orderId =" + selectedForDelete.getId();
            DataBaseHandler.getInstance().execAction(deleteOrderData);

            String deleteOrder = "DELETE FROM order_table WHERE id =" + selectedForDelete.getId();
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
    void refreshData(ActionEvent event) {
        loadData();
    }

}
