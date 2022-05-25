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

    /**
     * This method prepares the form to ask character card 3 and 5 parameters
     * Choice boxes are populated with available colors and islands
     * @param clientModel clientModel
     * @param parameters a generic parameter which depends, based on specific scene
     * @param <T> generic parameter
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        id = (int) parameters;
        selectIslandIdChoiceBox.getItems().addAll(clientModel.getIslands().stream().map(Island::getId).collect(Collectors.toList()));
        selectIslandIdChoiceBox.setOnAction(this::activeButton);
    }

    /**
     * User has to choose an island
     * On confirm button click, is sent a message to server containing the card id, 3 or 5
     */
    public void confirm(){
        confirmButton.setDisable(true);
        if (id == 3)
            notify(new CharacterCard03Reply(selectIslandIdChoiceBox.getValue()));
        if (id == 5)
            notify(new CharacterCard05Reply(selectIslandIdChoiceBox.getValue()));
    }

    /**
     * At the beginning, button is disabled, because user must choose an island
     * When choice box contains a value, button is activated
     * @param event on choice box action
     */
    private void activeButton(ActionEvent event){
        confirmButton.setDisable(false);
    }
}
