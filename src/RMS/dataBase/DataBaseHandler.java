package RMS.dataBase;

import RMS.ui.manageMenu.ManageMenuController;
import RMS.ui.manageStaff.ManageStaffController;
import javafx.scene.layout.Pane;

import java.lang.invoke.SwitchPoint;
import java.sql.*;
import javax.swing.JOptionPane;

public class DataBaseHandler {

    private static DataBaseHandler handler = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rms";
    private static final String DB_USER_NAME = "root";
    private static final String DB_PASSWORD = "";
    private static Connection conn = null;
    private static Statement stmt = null;

    private DataBaseHandler() {
        createConnection();
        setupMenuTable();
        setupStaffTable();
    }

    // Changed this so other window can get access to the db at the same time
    public static DataBaseHandler getInstance() {
        if (handler == null) {
            handler = new DataBaseHandler();
        }
        return handler;
    }

    // created the database connection
    public void createConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // created the Book table
    public void setupMenuTable() {
        try {
            stmt = conn.createStatement();
            stmt.execute("SET GLOBAL time_zone = '+6:00'");

            stmt.execute(" CREATE TABLE IF NOT EXISTS menu_item ("
                    + " id varchar(255) PRIMARY key, \n"
                    + " name varchar (255), \n"
                    + " price varchar (255), \n"
                    + " menu_type varchar (100) \n"
                    + ")");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
        }
    }

    // created the staff table
    public void setupStaffTable() {
        try {
            stmt = conn.createStatement();
            stmt.execute(" CREATE TABLE IF NOT EXISTS staff ("
                    + " id varchar(255) PRIMARY key, \n"
                    + " firstName varchar (255), \n"
                    + " lastName varchar (255), \n"
                    + " password varchar (255) \n"
                    + ")");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
        }
    }

    // created the execute query function
    public ResultSet execQuery(String query) {
        ResultSet result = null;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
        }
        return result;
    }

    // created the execAction
    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
            return false;
        }
    }

    // Updating the menu
    public boolean updateMenu(ManageMenuController.Menu menu) {
        try {
            String update = "UPDATE menu_item SET name = ?, price = ?, menu_type = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, menu.getName());
            stmt.setString(2, menu.getPrice());
            stmt.setString(3, menu.getType());
            stmt.setString(4, menu.getId());

            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
            return false;
        }
    }

    // Delete the menu
    public boolean DeleteMenu(ManageMenuController.Menu menu) {
        try {
            String update = "DELETE FROM menu_item WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, menu.getId());
            int res = stmt.executeUpdate();
            System.out.println(res);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
            return false;
        }
    }

    // Updating the Staff
    public boolean updateStaff(ManageStaffController.Staff staff) {
        try {
            String update = "UPDATE staff SET firstName= ?, lastName = ?, password = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, staff.getFirstName());
            stmt.setString(2, staff.getLastName());
            stmt.setString(3, staff.getPassword());
            stmt.setString(4, staff.getId());
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
            return false;
        }
    }

    // Delete the Staff
    public boolean DeleteStaff(ManageStaffController.Staff staff) {
        try {
            String update = "DELETE FROM staff WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, staff.getId());
            int res = stmt.executeUpdate();
            System.out.println(res);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
            return false;
        }
    }

}

