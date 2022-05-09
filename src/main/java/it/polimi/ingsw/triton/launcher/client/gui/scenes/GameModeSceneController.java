package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.GameModeReply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.PlayersNumberReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GameModeSceneController extends Observable<Message> {
    @FXML
    Button confirmButton;

    @FXML
    AnchorPane gameModePane;

    @FXML
    RadioButton rdbStandard;

    @FXML
    RadioButton rdbExpert;

    @FXML
    ToggleGroup modeGroup;

    private String username;

    public void setUsername(String username){
        this.username = username;
    }

    public void confirm(ActionEvent event) throws IOException {
        notify(new GameModeReply(username, rdbExpert.isSelected()));
    }
}
