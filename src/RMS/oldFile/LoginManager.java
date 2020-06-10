package RMS.oldFile;

import java.io.IOException;
import java.util.logging.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;

/**
 * Manages control flow for logins
 */
public class LoginManager {
    private Scene scene;

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */

    public void authorizeLogin() {
        showMainView();
    }

    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    public void showMenuItem() {
        loadMenuManagement();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("login.fxml")
            );
            scene.setRoot((Parent) loader.load());

            LoginController controller =
                    loader.<LoginController>getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("restaurant_management_system.fxml"));
            scene.setRoot((Parent) loader.load());

            RMSController controller =
                    loader.<RMSController>getController();
            controller.initRMSController(this);


        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadMenuManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("menu_item_management.fxml")
            );
            scene.setRoot((Parent) loader.load());

            RMSController controller =
                    loader.<RMSController>getController();
            controller.initMenuItem(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
