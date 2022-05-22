package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCard01Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;
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


    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        setupChoiceBox(selectColorChoiceBox, clientModel.getCharacterCardById(1).getStudents());
        selectColorChoiceBox.getItems().addAll(colorCharCard);
        selectIslandIdChoiceBox.getItems().addAll(setUpIslandIdChoiceBox(clientModel));
        selectColorChoiceBox.setOnAction(this::activeButton);
        selectIslandIdChoiceBox.setOnAction(this::activeButton);
    }


    public void confirm(ActionEvent event){
        confirmButton.setDisable(true);
        notify(new CharacterCard01Reply(username, Color.valueOf(selectColorChoiceBox.getValue()), selectIslandIdChoiceBox.getValue()));
    }

    private void activeButton(ActionEvent event){
        if(!charCard01Pane.getChildren().stream().filter(
                x->x instanceof ChoiceBox).filter(
                x->x.isVisible()).map(
                x->((ChoiceBox<?>) x).getValue()).collect(
                Collectors.toList()).contains(null)){
            confirmButton.setDisable(false);
        }
        else{
            confirmButton.setDisable(true);
        }
    }
}
