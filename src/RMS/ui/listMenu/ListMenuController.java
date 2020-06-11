package RMS.ui.listMenu;

import RMS.dataBase.DataBaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListMenuController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    public static class Menu{
        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty price;
        private final SimpleStringProperty type;

        public Menu(String  id, String  name, String  price, String  type) {
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
}
