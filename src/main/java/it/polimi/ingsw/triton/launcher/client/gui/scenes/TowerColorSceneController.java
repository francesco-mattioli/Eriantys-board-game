package it.polimi.ingsw.triton.launcher.client.gui.scenes;

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
import java.util.Map;
import java.util.stream.Collectors;

public class TowerColorSceneController extends Observable<Message> {

    @FXML
    ChoiceBox<String> towerColorChoice;

    @FXML
    Button selectButton;

    @FXML
    AnchorPane towerColorPane;

    private String username;
    private Map<String,TowerColor> towerColorMap;

    public void setTowerColorMap(Map<String, TowerColor> towerColorMap) {
        this.towerColorMap = towerColorMap;
    }


    public void setUsername(String username){
        this.username = username;
    }

    public ChoiceBox<String> getTowerColorChoice() {
        return towerColorChoice;
    }


    public void select(ActionEvent event){
        notify(new TowerColorReply(username, towerColorMap.get(towerColorChoice.getValue())));
        selectButton.setDisable(true);
    }

}
