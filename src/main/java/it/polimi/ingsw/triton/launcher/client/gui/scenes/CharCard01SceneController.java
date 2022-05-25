package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.CharacterCard01Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.util.stream.Collectors;

public class CharCard01SceneController extends SceneController {

    @FXML
    AnchorPane charCard01Pane;

    @FXML
    Button confirmButton;

    @FXML
    ChoiceBox<String> selectColorChoiceBox;

    @FXML
    ChoiceBox<Integer> selectIslandIdChoiceBox;


    /**
     * This method prepares the form to ask character card 1 parameters
     * Choice boxes are populated with available colors and islands
     * @param clientModel clientModel
     * @param parameters a generic parameter which depends, based on specific scene
     * @param <T> generic parameter
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        setupStudentsChoiceBox(selectColorChoiceBox, clientModel.getCharacterCardById(1).getStudents());
        selectIslandIdChoiceBox.getItems().addAll(clientModel.getIslands().stream().map(Island::getId).collect(Collectors.toList()));
        selectColorChoiceBox.setOnAction(this::activateButton);
        selectIslandIdChoiceBox.setOnAction(this::activateButton);
    }


    /**
     * This method sends the message to server which contains the character card 1 parameters
     */
    public void confirm(){
        confirmButton.setDisable(true);
        notify(new CharacterCard01Reply(Color.valueOf(selectColorChoiceBox.getValue()), selectIslandIdChoiceBox.getValue()));
    }

    /**
     * At the beginning, button is disabled, because user must choose a student and an island
     * When choice boxes contain a value, button is activated
     * @param event on choice box action
     */
    private void activateButton(ActionEvent event){
        confirmButton.setDisable(charCard01Pane.getChildren().stream().filter(
                ChoiceBox.class::isInstance).filter(
                Node::isVisible).map(
                x -> ((ChoiceBox<?>) x).getValue()).collect(
                Collectors.toList()).contains(null));
    }
}
