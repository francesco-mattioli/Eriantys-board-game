package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.gui.Gui;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuSceneController extends Observable<Message> {

    @FXML
    Button joinButton;

    @FXML
    AnchorPane menuPane;

    public void join(ActionEvent event) throws IOException {
        Gui gui = new Gui();
        gui.setActiveStage((Stage)((Node)event.getSource()).getScene().getWindow());
        gui.askUsername();
    }
}
