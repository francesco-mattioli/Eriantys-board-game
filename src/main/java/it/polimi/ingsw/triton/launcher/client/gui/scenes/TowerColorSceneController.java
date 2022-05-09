package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.TowerColorReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class TowerColorSceneController extends Observable<Message> {

    @FXML
    ComboBox<TowerColor> cmbTowerColor;

    @FXML
    Button selectButton;

    @FXML
    AnchorPane towerColorPane;

    private String username;

    public void setUsername(String username){
        this.username = username;
    }

    public ComboBox<TowerColor> getCmbTowerColor() {
        return cmbTowerColor;
    }

    public void select(ActionEvent event){
        notify(new TowerColorReply(username, cmbTowerColor.getValue()));
    }
}
