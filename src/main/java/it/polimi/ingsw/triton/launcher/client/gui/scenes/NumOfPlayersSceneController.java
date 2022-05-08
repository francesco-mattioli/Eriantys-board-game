package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.gui.Gui;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.PlayersNumberReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NumOfPlayersSceneController extends Observable<Message> {
    @FXML
    Button confirm;

    @FXML
    AnchorPane numOfPlayersPane;

    @FXML
    RadioButton rdb2;

    @FXML
    RadioButton rdb3;

    @FXML
    ToggleGroup playersGroup;

    public void confirm(ActionEvent event) throws IOException {
        int numPlayers;
        if(rdb2.isPressed())
            numPlayers =2;
        else
            numPlayers = 3;
        //notify(new PlayersNumberReply("username", numPlayers));
    }

}
