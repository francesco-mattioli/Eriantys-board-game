package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.TowerColorReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TowerColorSceneController extends SceneController {

    @FXML
    ChoiceBox<String> towerColorChoice;

    @FXML
    Button selectButton;

    @FXML
    AnchorPane towerColorPane;

    private Map<String,TowerColor> towerColorMap;


    /**
     * This method assigns to the player the chosen tower color, when he clicks the button
     * @param event
     */
    public void select(ActionEvent event){
        notify(new TowerColorReply(username, towerColorMap.get(towerColorChoice.getValue())));
        selectButton.setDisable(true);
    }

    /**
     * In this method we create the map between Strings and TowerColor, to associate the choiceBox value and tower coolor
     * @param clientModel
     * @param parameters in this case contains the arraylist available tower colors
     * @param <T>
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        boolean [] towerColorChosen = (boolean[]) parameters;
        towerColorMap = new HashMap<>();
        for (int i = 0; i < towerColorChosen.length; i++) {
            if (!towerColorChosen[i]) {
                towerColorMap.put(TowerColor.values()[i].name(), TowerColor.values()[i]);
            }
        }
        towerColorChoice.getItems().addAll(towerColorMap.keySet());
        towerColorChoice.setOnAction(this::activeButton);
    }

    private void activeButton(ActionEvent event){
        selectButton.setDisable(false);
    }

}