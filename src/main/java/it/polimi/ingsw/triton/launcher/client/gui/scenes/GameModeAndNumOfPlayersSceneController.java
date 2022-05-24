package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages.PlayersNumberAndGameModeReply;
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

    /**
     * User chooses the number of players and the game mode
     * When he clicks on confirm, a message is sent to server
     */
    public void confirm() {
        int numOfPlayers = 2;
        if (rdb3Players.isSelected())
            numOfPlayers = 3;
        notify(new PlayersNumberAndGameModeReply(numOfPlayers,rdbExpert.isSelected()));
        confirmButton.setDisable(true);
    }

}
