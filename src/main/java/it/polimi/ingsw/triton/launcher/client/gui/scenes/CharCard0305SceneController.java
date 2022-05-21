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

    private int id;

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        id = (int) parameters;
        selectIslandIdChoiceBox.getItems().addAll(setUpIslandIdChoiceBox(clientModel));
        selectIslandIdChoiceBox.setOnAction(this::activeButton);
    }

    @Override
    public AnchorPane getAnchorPane() {
        return charCard0305Pane;
    }

    public void confirm(ActionEvent event){
        confirmButton.setDisable(true);
        if (id == 3)
            notify(new CharacterCard03Reply(username, selectIslandIdChoiceBox.getValue()));
        if (id == 5)
            notify(new CharacterCard05Reply(username, selectIslandIdChoiceBox.getValue()));
    }

    private void activeButton(ActionEvent event){
        confirmButton.setDisable(false);
    }
}
