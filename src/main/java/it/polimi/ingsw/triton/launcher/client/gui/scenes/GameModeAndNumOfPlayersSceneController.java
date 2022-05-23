package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages.PlayersNumberAndGameModeReply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class GameModeAndNumOfPlayersSceneController extends SceneController {

    @FXML
    AnchorPane gameModePane;

    @FXML
    Button confirmButton;

    @FXML
    RadioButton rdbStandard;

    @FXML
    RadioButton rdbExpert;

    @FXML
    RadioButton rdb2Players;

    @FXML
    RadioButton rdb3Players;

    @FXML
    ToggleGroup modeGroup;

    @FXML
    ToggleGroup numOfPlayersGroup;

    public void confirm(ActionEvent event) {
        int numOfPlayers = 2;
        if (rdb3Players.isSelected())
            numOfPlayers = 3;
        notify(new PlayersNumberAndGameModeReply(username, numOfPlayers,rdbExpert.isSelected()));
        confirmButton.setDisable(true);
    }

}
