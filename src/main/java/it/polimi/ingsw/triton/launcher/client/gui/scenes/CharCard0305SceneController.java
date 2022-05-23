package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.CharacterCard03Reply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.CharacterCard05Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.util.stream.Collectors;

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
        selectIslandIdChoiceBox.getItems().addAll(clientModel.getIslands().stream().map(Island::getId).collect(Collectors.toList()));
        selectIslandIdChoiceBox.setOnAction(this::activeButton);
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
