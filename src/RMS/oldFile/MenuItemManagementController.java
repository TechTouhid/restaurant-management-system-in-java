package RMS.oldFile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuItemManagementController {

    @FXML
    public Button manageMenuItem;

    // starting the Menu item window
    public void initMenuItem(final LoginManager loginManager) {
        manageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginManager.showMenuItem();
            }
        });
    }
}
