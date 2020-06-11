module restaurant_management_system_in_java {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.logging;
    requires com.jfoenix;
    requires java.desktop;
    requires java.sql;
    opens RMS.ui.addMenu;

    // List Menu
    exports RMS.ui.listMenu to javafx.graphics, javafx.fxml;
    opens RMS.ui.listMenu to javafx.fxml, javafx.base;
}