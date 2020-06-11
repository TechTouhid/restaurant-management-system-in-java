package RMS.dataBase;

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
                    +" id varchar(255) PRIMARY key, \n"
                    +" name varchar (255), \n"
                    +" price varchar (255), \n"
                    +" menu_type varchar (100) \n"
                    +  ")");

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
}

