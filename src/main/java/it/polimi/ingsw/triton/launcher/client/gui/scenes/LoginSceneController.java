package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages.LoginRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginSceneController extends SceneController {

    @FXML
    Button loginButton;

    @FXML
    AnchorPane loginPane;

    @FXML
    TextField loginTextField;

    /**
     * User chooses his username
     * When he clicks on login button, a message is sent to server
     */
    public void login() {
        String username = loginTextField.getText();
        notify(new LoginRequest(username));
        loginButton.setDisable(true);
    }

}
