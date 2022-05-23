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


    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        setupStudentsChoiceBox(selectColorChoiceBox, clientModel.getCharacterCardById(1).getStudents());
        selectIslandIdChoiceBox.getItems().addAll(clientModel.getIslands().stream().map(Island::getId).collect(Collectors.toList()));
        selectColorChoiceBox.setOnAction(this::activeButton);
        selectIslandIdChoiceBox.setOnAction(this::activeButton);
    }


    public void confirm(){
        confirmButton.setDisable(true);
        notify(new CharacterCard01Reply(username, Color.valueOf(selectColorChoiceBox.getValue()), selectIslandIdChoiceBox.getValue()));
    }

    private void activeButton(ActionEvent event){
        confirmButton.setDisable(charCard01Pane.getChildren().stream().filter(
                ChoiceBox.class::isInstance).filter(
                Node::isVisible).map(
                x -> ((ChoiceBox<?>) x).getValue()).collect(
                Collectors.toList()).contains(null));
    }
}
