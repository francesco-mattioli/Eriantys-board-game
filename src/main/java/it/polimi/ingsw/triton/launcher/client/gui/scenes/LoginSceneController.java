package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.gui.Gui;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.LoginRequest;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    @Override
    public AnchorPane getAnchorPane() {
        return loginPane;
    }

    @Override
    public Button getButton() {
        return loginButton;
    }

    @Override
    public String getPath() {
        return null;
    }
}
