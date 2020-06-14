package RMS.ui.addOrder;

import RMS.dataBase.DataBaseHandler;
import RMS.ui.manageOrder.ManageOrderController;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddOrderController implements Initializable {
    ObservableList<Menu> list = FXCollections.observableArrayList();
    ObservableList<AddItemToOrder> selectedItem = FXCollections.observableArrayList();
    double totalAddValue = 0.0;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<AddItemToOrder> orderTableView;

    @FXML
    private TableColumn<AddItemToOrder, String> oderIdCol;

    @FXML
    private TableColumn<AddItemToOrder, String> oderNameCol;

    @FXML
    private TableColumn<AddItemToOrder, String> orderQuantityCol;

    @FXML
    private TableColumn<AddItemToOrder, String> orderPriceCol;

    @FXML
    private Label showTotalCharge;

    @FXML
    private Label staffName;

    @FXML
    private TextField quantityField;

    @FXML
    private JFXButton addMenuItem;

    @FXML
    private JFXButton deleteItem;

    @FXML
    private JFXButton addOrder;

    @FXML
    private TableView<Menu> menuItemTableView;

    @FXML
    private TableColumn<Menu, String> idCol;

    @FXML
    private TableColumn<Menu, String> nameCol;

    @FXML
    private TableColumn<Menu, String> priceCol;

    @FXML
    private TableColumn<Menu, String> typeCol;

    @FXML
    private JFXButton showAllMenu;

    @FXML
    private JFXButton showMainMenu;

    @FXML
    private JFXButton showDrinkMenu;

    @FXML
    private JFXButton showAlcoholMenu;

    @FXML
    private JFXButton showDessertMenu;

    DataBaseHandler dataBaseHandler;
    private Boolean isInEditMode = Boolean.FALSE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData("menu_type = 'Drink' or menu_type ='Main' or menu_type ='Dessert' or menu_type = 'Alcohol'");
        initOrderCol();
        quantityField.setText("1");
        dataBaseHandler = DataBaseHandler.getInstance();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void loadData(String menuType) {
        list.clear();
        DataBaseHandler handler = DataBaseHandler.getInstance();
        String qu = "SELECT * FROM menu_item WHERE " + menuType;
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
            Logger.getLogger(AddOrderController.class.getName()).log(Level.SEVERE, null, e);
        }

        menuItemTableView.getItems().setAll(list);
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

    private void initOrderCol() {
        oderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        oderNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void loadOrderData() {

        orderTableView.refresh();
        String initID = menuItemTableView.getSelectionModel().getSelectedItem().getId();
        String initName = menuItemTableView.getSelectionModel().getSelectedItem().getName();
        String initPrice = menuItemTableView.getSelectionModel().getSelectedItem().getPrice();
        String initQuantity = quantityField.getText();

        selectedItem.add(new AddItemToOrder(initID, initName, initPrice, initQuantity));
        orderTableView.getItems().setAll(selectedItem);
        totalAddValue += Double.parseDouble(quantityField.getText()) * Double.parseDouble(menuItemTableView.getSelectionModel().getSelectedItem().getPrice());
        showTotalCharge.setText("" + totalAddValue);
    }

    @FXML
    void deleteMenuItem(ActionEvent event) {
        double multiPrice = 0.0;
        AddItemToOrder myOrder = new AddItemToOrder();
        myOrder = orderTableView.getSelectionModel().getSelectedItem();
        if (orderTableView.getSelectionModel().getSelectedIndex() != -1) {
            selectedItem.remove(orderTableView.getSelectionModel().getSelectedIndex());
            orderTableView.getSelectionModel().clearSelection();
            orderTableView.getItems().clear();
            orderTableView.getItems().addAll(selectedItem);
            multiPrice = Double.parseDouble(myOrder.getQuantity()) * Double.parseDouble(myOrder.getPrice());
            totalAddValue = totalAddValue - multiPrice;
            if (totalAddValue < 0) {
                totalAddValue = 0.0;
            }
            showTotalCharge.setText("" + totalAddValue);
        }
    }

    public static class AddItemToOrder {
        private SimpleStringProperty id;
        private SimpleStringProperty name;
        private SimpleStringProperty price;
        private SimpleStringProperty quantity;

        public AddItemToOrder() {
        }

        public AddItemToOrder(String id, String name, String price, String quantity) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleStringProperty(price);
            this.quantity = new SimpleStringProperty(quantity);
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

        public String getQuantity() {
            return quantity.get();
        }
    }

    @FXML
    void addNewMenuItem(ActionEvent event) {
        loadOrderData();
    }

    public void addAllOrder() {
        String id_order = "";
        AddItemToOrder order = new AddItemToOrder();
        List<List<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < orderTableView.getItems().size(); i++) {
            order = orderTableView.getItems().get(i);
            arrayList.add(new ArrayList<>());
            arrayList.get(i).add(order.getId());
            arrayList.get(i).add(order.getName());
            arrayList.get(i).add(order.getQuantity());
            arrayList.get(i).add(order.getPrice());
        }
        String id_qu = "SELECT id FROM order_table ORDER BY id DESC LIMIT 1";
        ResultSet rs = dataBaseHandler.execQuery(id_qu);
        try {
            while (rs.next()) {
                id_order = rs.getString("id");
            }
        } catch (SQLException e) {
            Logger.getLogger(AddOrderController.class.getName()).log(Level.SEVERE, null, e);
        }

        for (int i = 0; i < orderTableView.getItems().size(); i++) {
            String qu = "INSERT INTO edit_order_table (id, name, quantity, price, orderId) VALUE (" +
                    "'" + arrayList.get(i).get(0) + "'," +
                    "'" + arrayList.get(i).get(1) + "'," +
                    "'" + arrayList.get(i).get(2) + "'," +
                    "'" + arrayList.get(i).get(3) + "'," +
                    "'" + id_order + "'" +
                    ")";
            dataBaseHandler.execAction(qu);
        }
    }

    @FXML
    void addOrder(ActionEvent event) {
        if (orderTableView.getItems().size() != 0) {
            Alert alertx = new Alert(Alert.AlertType.CONFIRMATION);
            alertx.setHeaderText(null);
            alertx.setTitle("Adding order");
            alertx.setContentText("Are you sure you want to " + "check out" + " ?");
            Optional<ButtonType> answer = alertx.showAndWait();
            if (answer.get() == ButtonType.OK) {
                String qu = "INSERT INTO order_table (staffFirstName, staffLastName, totalPrice) VALUE (" +
                        "'" + "admin" + "'," +
                        "'" + "admin" + "'," +
                        "'" + showTotalCharge.getText() + "'" +
                        ")";
                if (dataBaseHandler.execAction(qu)) {
                    addAllOrder();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Order added");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Failed");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Order Failed");
                alert.setContentText("Order Process Cancelled");

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No item in the order list ");
            alert.showAndWait();
        }
    }

    @FXML
    void showAlcoholType(ActionEvent event) {
        loadData("menu_type = 'Alcohol'");
    }

    @FXML
    void showAllType(ActionEvent event) {
        loadData("menu_type = 'Drink' or menu_type ='Main' or menu_type ='Dessert' or menu_type = 'Alcohol'");
    }

    @FXML
    void showDessertType(ActionEvent event) {
        loadData("menu_type ='Dessert'");
    }

    @FXML
    void showDrinkType(ActionEvent event) {
        loadData("menu_type = 'Drink'");
    }

    @FXML
    void showMainType(ActionEvent event) {
        loadData("menu_type ='Main'");
    }


    public void inflateUI(ManageOrderController.Order order) {
//        menuID.setText(menu.getId());
//        menuName.setText(menu.getName());
//        menuPrice.setText(menu.getPrice());
//        menuType.setValue(menu.getType());
//        menuID.setEditable(false);
//        isInEditMode = Boolean.TRUE;
        System.out.println(orderTableView.getItems());
        System.out.println(order.getId());
    }

}
