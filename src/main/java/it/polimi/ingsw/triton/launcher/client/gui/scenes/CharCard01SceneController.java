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

public class CharCard01SceneController extends SceneController {

    @FXML
    AnchorPane charCard01Pane;

    @FXML
    Button confirmButton;

    @FXML
    ChoiceBox<String> selectColorChoiceBox;

    @FXML
    ChoiceBox<Integer> selectIslandIdChoiceBox;

    private Map<String, Color> colorMap = new HashMap<>();

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        setUpCharCardChoiceBox(clientModel, 1);
        selectColorChoiceBox.getItems().addAll(colorMap.keySet());
        selectIslandIdChoiceBox.getItems().addAll(setUpIslandIdChoiceBox(clientModel));
    }

    @Override
    public AnchorPane getAnchorPane() {
        return charCard01Pane;
    }

    public void confirm(ActionEvent event){
        confirmButton.setDisable(true);
        notify(new CharacterCard01Reply(username, colorMap.get(selectColorChoiceBox.getValue()), selectIslandIdChoiceBox.getValue()));
    }
}
