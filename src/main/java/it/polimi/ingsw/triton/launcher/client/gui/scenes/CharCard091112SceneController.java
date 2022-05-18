package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCard09Reply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCard11Reply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCard12Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;

public class CharCard091112SceneController extends SceneController{

    @FXML
    AnchorPane charCard091112Pane;

    @FXML
    ChoiceBox<String> selectColorChoiceBox;

    @FXML
    Button confirmButton;

    private Map<String, Color> colorMap;
    private ClientModel clientModel;

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        colorMap = new HashMap<>();
        this.clientModel = clientModel;
        colorMap = setUpColorChoiceBox(clientModel);
        selectColorChoiceBox.getItems().addAll(colorMap.keySet());
    }

    public void confirm(ActionEvent event){
        confirmButton.setDisable(true);
        if (clientModel.getLastCharacterCardPlayed().getId() == 9)
            notify(new CharacterCard09Reply(username, colorMap.get(selectColorChoiceBox.getValue())));
        if (clientModel.getLastCharacterCardPlayed().getId() == 11)
            notify(new CharacterCard11Reply(username, colorMap.get(selectColorChoiceBox.getValue())));
        if (clientModel.getLastCharacterCardPlayed().getId() == 12)
            notify(new CharacterCard12Reply(username, colorMap.get(selectColorChoiceBox.getValue())));
    }
}
