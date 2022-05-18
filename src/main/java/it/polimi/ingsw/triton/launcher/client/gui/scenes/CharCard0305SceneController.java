package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCard03Reply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCard05Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

public class CharCard0305SceneController extends SceneController{

    @FXML
    Button confirmButton;

    @FXML
    AnchorPane charCard0305Pane;

    @FXML
    ChoiceBox<Integer> selectIslandIdChoiceBox;

    private ClientModel clientModel;

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        selectIslandIdChoiceBox.getItems().addAll(setUpIslandIdChoiceBox(clientModel));
        this.clientModel = clientModel;
    }

    public void confirm(ActionEvent event){
        confirmButton.setDisable(true);
        if (clientModel.getLastCharacterCardPlayed().getId() == 3)
            notify(new CharacterCard03Reply(username, selectIslandIdChoiceBox.getValue()));
        if (clientModel.getLastCharacterCardPlayed().getId() == 5)
            notify(new CharacterCard05Reply(username, selectIslandIdChoiceBox.getValue()));
    }
}
