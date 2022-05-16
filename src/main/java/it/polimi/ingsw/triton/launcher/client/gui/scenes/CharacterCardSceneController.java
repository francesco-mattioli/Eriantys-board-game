package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.server.model.playeractions.Action;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.UseCharacterCardRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.AssistantCardRequest;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderPane;

public class CharacterCardSceneController extends Observable<Message> {
    @FXML
    AnchorPane characterCardPane;

    @FXML
    BorderPane card1Pane;

    @FXML
    BorderPane card2Pane;

    @FXML
    BorderPane card3Pane;

    @FXML
    Button buttonPlay;

    private String username;

    private int cardID;

    public AnchorPane getCharacterCardPane() {
        return characterCardPane;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void playCharacterCard(ActionEvent event){
        notify(new UseCharacterCardRequest(username, cardID));
    }
}
