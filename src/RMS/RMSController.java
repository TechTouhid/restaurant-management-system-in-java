package RMS;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
/** Controls the main application screen */
public class RMSController {
    @FXML
    private Button logoutButton;
    public void initialize(final LoginManager loginManager) {
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                loginManager.logout();
            }
        });
    }
}