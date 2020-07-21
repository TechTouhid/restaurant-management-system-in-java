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
    // manage Menu
    exports RMS.ui.manageMenu to javafx.graphics, javafx.fxml;
    opens RMS.ui.manageMenu to javafx.fxml, javafx.base;
    // add Order
    exports RMS.ui.addOrder to javafx.graphics, javafx.fxml;
    opens RMS.ui.addOrder to javafx.fxml, javafx.base;
    // add Order
    exports RMS.ui.addStaff to javafx.graphics, javafx.fxml;
    opens RMS.ui.addStaff to javafx.fxml, javafx.base;
    // manage Stuff
    exports RMS.ui.manageStaff to javafx.graphics, javafx.fxml;
    opens RMS.ui.manageStaff to javafx.fxml, javafx.base;
    // manage Order
    exports RMS.ui.manageOrder to javafx.graphics, javafx.fxml;
    opens RMS.ui.manageOrder to javafx.fxml, javafx.base;
    // main
    exports RMS.ui.main to javafx.graphics, javafx.fxml;
    opens RMS.ui.main to javafx.fxml, javafx.base;
    // login
    exports RMS.ui.login to javafx.graphics, javafx.fxml;
    opens RMS.ui.login to javafx.fxml, javafx.base;
    // payment
    exports RMS.ui.managePayment to javafx.graphics, javafx.fxml;
    opens RMS.ui.managePayment to javafx.fxml, javafx.base;

     exports RMS to javafx.graphics;
     exports RMS.ui.sumon to javafx.graphics, javafx.fxml;
     opens RMS.ui.sumon to javafx.fxml, javafx.base;
}