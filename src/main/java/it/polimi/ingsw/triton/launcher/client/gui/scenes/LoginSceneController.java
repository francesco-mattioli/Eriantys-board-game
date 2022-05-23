package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages.LoginRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class LoginSceneController extends SceneController {

    @FXML
    Button loginButton;

    @FXML
    AnchorPane loginPane;

    @FXML
    TextField loginTextField;

    public void login(ActionEvent event) throws IOException {
        String username = loginTextField.getText();
        notify(new LoginRequest(username));
        loginButton.setDisable(true);
    }

}
