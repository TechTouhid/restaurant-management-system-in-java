package RMS.oldFile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controls the main application screen
 */
public class RMSController {
    @FXML
    private Button logoutButton;
    @FXML
    public Button manageMenuItem;

    public void initialize() {
    }
    public void initRMSController(final LoginManager loginManager) {
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginManager.logout();
            }
        });
    }

    public void initMenuItem(final LoginManager loginManager) {
        manageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginManager.showMenuItem();
            }
        });
    }

    public void handleButtonAction(ActionEvent event) {
        
    }
}


