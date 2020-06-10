package RMS.oldFile;

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
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("menu_item_management.fxml")
            );
            scene.setRoot((Parent) loader.load());

            RMSController controller =
                    loader.<RMSController>getController();
//            controller.initMenuItem(this);
        } catch (IOException ex) {
            Logger.getLogger(ButtonManager.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}


