package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCard10Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

public class CharCard10SceneController extends SceneController{
    @FXML
    Button stopButton;

    @FXML
    Button confirmButton1;

    @FXML
    Button confirmButton2;

    @FXML
    AnchorPane charCard10Pane;

    @FXML
    ChoiceBox<String> fromDiningRoom1;

    @FXML
    ChoiceBox<String> fromDiningRoom2;

    @FXML
    ChoiceBox<String> fromEntrance1;

    @FXML
    ChoiceBox<String> fromEntrance2;

    private int[] fromEntrance = new int[5];
    private int[] fromDiningRoom = new int[5];
    private ClientModel clientModel;

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        this.clientModel = clientModel;
        setChoiceBoxEntrance(fromEntrance1);
        setChoiceBoxDiningRoom(fromDiningRoom1);
    }

    public void confirm1(ActionEvent event){
        updateSwitchStudents(fromEntrance1, fromDiningRoom1);
        setChoiceBoxDiningRoom(fromDiningRoom2);
        setChoiceBoxEntrance(fromEntrance2);
        disableButtonAndChoiceBox(confirmButton1, fromEntrance1, fromEntrance2, fromDiningRoom1, fromDiningRoom2, confirmButton2);
    }

    public void confirm2(ActionEvent event){
        updateSwitchStudents(fromEntrance2, fromDiningRoom2);
        fromEntrance2.setDisable(true);
        fromDiningRoom2.setDisable(true);
        confirmButton2.setDisable(true);
        stopButton.setDisable(true);
        notify(new CharacterCard10Reply(username,fromEntrance, fromDiningRoom));
    }


    public void stop(ActionEvent event){
        fromEntrance1.setDisable(true);
        fromDiningRoom1.setDisable(true);
        confirmButton1.setDisable(true);
        fromEntrance2.setDisable(true);
        fromDiningRoom2.setDisable(true);
        confirmButton2.setDisable(true);
        notify(new CharacterCard10Reply(username, fromEntrance, fromDiningRoom));
    }

    public void setChoiceBoxEntrance(ChoiceBox<String> choiceBox){
        setUpEntranceChoiceBox(clientModel, fromEntrance);
        choiceBox.getItems().addAll(colorEntrance);
    }

    public void setChoiceBoxDiningRoom(ChoiceBox<String> choiceBox){
        setUpDiningRoomChoiceBox(clientModel, fromDiningRoom);
        choiceBox.getItems().addAll(colorDiningRoom);
    }

    public void updateSwitchStudents(ChoiceBox<String> fromEntranceBox, ChoiceBox<String> fromDiningRoomBox){
        fromEntrance[Color.valueOf(fromEntranceBox.getValue()).ordinal()] ++;
        fromDiningRoom[Color.valueOf(fromDiningRoomBox.getValue()).ordinal()] ++;
    }

    public void disableButtonAndChoiceBox(Button button, ChoiceBox<String> fromEntranceBox, ChoiceBox<String> fromEntranceBox1, ChoiceBox<String> fromCharCardBox, ChoiceBox<String> fromCharCardBox1, Button button1){
        fromEntranceBox.setDisable(true);
        fromCharCardBox.setDisable(true);
        button.setDisable(true);
        fromEntranceBox1.setDisable(false);
        fromCharCardBox1.setDisable(false);
        button1.setDisable(false);
    }

}
