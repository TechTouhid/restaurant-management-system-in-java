package RMS;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ButtonManager {
    private Scene scene;

    // constructor method
    public ButtonManager(Scene scene) {
        this.scene = scene;
    }

   public void showMenuItem() {
        loadMenuManagement();
    }

    // Load Menu item management function
    public void loadMenuManagement() {
//        try {
//            FXMLLoader loader = new FXMLLoader(
//                    getClass().getResource("menu_item_management.fxml")
//            );
//            scene.setRoot((Parent) loader.load());
////            MenuItemManagementController menuItemManagementController =
////                    loader.<MenuItemManagementController>getController();
////            menuItemManagementController.initMenuItemManagement(this);
//
//            RMSController rmsController =
//                    loader.<RMSController>getController();
//            rmsController.initMenuItemManagement(this);
//            System.out.println("running");
//        } catch (IOException ex) {
//            Logger.getLogger(ButtonManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("menu_item_management.fxml")
            );
            scene.setRoot((Parent) loader.load());

            RMSController controller =
                    loader.<RMSController>getController();
//            controller.initMenuItemManagement(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}


