package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCard07Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;

public class CharCard07SceneController extends SceneController {

    @FXML
    Button stopButton;

    @FXML
    Button confirmButton1;

    @FXML
    Button confirmButton2;

    @FXML
    Button confirmButton3;

    @FXML
    AnchorPane charCard07Pane;

    @FXML
    ChoiceBox<String> fromCharCard1;

    @FXML
    ChoiceBox<String> fromCharCard2;

    @FXML
    ChoiceBox<String> fromCharCard3;

    @FXML
    ChoiceBox<String> fromEntrance1;

    @FXML
    ChoiceBox<String> fromEntrance2;

    @FXML
    ChoiceBox<String> fromEntrance3;

    private int[] fromEntrance = new int[5];
    private int[] fromCharCard = new int[5];
    private Map<String, Color> colorMapForEntrance =  new HashMap<>();
    private Map<String, Color> colorMapForCharCard =  new HashMap<>();
    private ClientModel clientModel;

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        setChoiceBoxEntrance(clientModel,fromEntrance1);
        setChoiceBoxCharCard(clientModel,fromCharCard1);
        this.clientModel = clientModel;
    }

    public void confirm1(ActionEvent event){
        updateSwitchStudents(fromEntrance1, fromCharCard1);
        disableButtonAndChoiceBox(confirmButton1, fromEntrance1, fromEntrance2, fromCharCard1, fromCharCard2);
    }

    public void confirm2(ActionEvent event){
        updateSwitchStudents(fromEntrance2, fromCharCard2);
        disableButtonAndChoiceBox(confirmButton2, fromEntrance2, fromEntrance3, fromCharCard2, fromCharCard3);
    }

    public void confirm3(ActionEvent event){
        updateSwitchStudents(fromEntrance3, fromCharCard3);
        fromEntrance3.setDisable(true);
        fromCharCard3.setDisable(true);
        confirmButton3.setDisable(true);
        stopButton.setDisable(true);
        notify(new CharacterCard07Reply(username,fromCharCard,fromEntrance, clientModel.getCharacterCardById(7).getStudents()));
    }

    public void stop(ActionEvent event){
        fromEntrance1.setDisable(true);
        fromCharCard1.setDisable(true);
        confirmButton1.setDisable(true);
        fromEntrance2.setDisable(true);
        fromCharCard2.setDisable(true);
        confirmButton2.setDisable(true);
        fromEntrance3.setDisable(true);
        fromCharCard3.setDisable(true);
        confirmButton3.setDisable(true);
        notify(new CharacterCard07Reply(username,fromCharCard,fromEntrance, clientModel.getCharacterCardById(7).getStudents()));
    }


    public void setChoiceBoxEntrance(ClientModel clientModel, ChoiceBox<String> choiceBox){
        for (int i = 0; i < clientModel.getMySchoolBoard().getEntrance().length; i++) {
            if (clientModel.getMySchoolBoard().getEntrance()[i] - fromEntrance[i] != 0) {
                colorMapForEntrance.put(Color.values()[i].name(), Color.values()[i]);
            }
        }
        choiceBox.getItems().addAll(colorMapForEntrance.keySet());
    }

    public void setChoiceBoxCharCard(ClientModel clientModel, ChoiceBox<String> choiceBox){
        for (int i = 0; i < clientModel.getCharacterCardById(7).getStudents().length; i++) {
            if (clientModel.getCharacterCardById(7).getStudents()[i] - fromCharCard[i] != 0) {
                colorMapForCharCard.put(Color.values()[i].name(), Color.values()[i]);
            }
        }
        choiceBox.getItems().addAll(colorMapForCharCard.keySet());
    }

    public void updateSwitchStudents(ChoiceBox<String> fromEntranceBox, ChoiceBox<String> fromCharCardBox){
        fromEntrance[colorMapForEntrance.get(fromEntranceBox.getValue()).ordinal()] ++;
        fromCharCard[colorMapForCharCard.get(fromCharCardBox.getValue()).ordinal()] ++;
    }

    public void disableButtonAndChoiceBox(Button button, ChoiceBox<String> fromEntranceBox, ChoiceBox<String> fromEntranceBox1, ChoiceBox<String> fromCharCardBox, ChoiceBox<String> fromCharCardBox1){
        fromEntranceBox.setDisable(true);
        fromCharCardBox.setDisable(true);
        button.setDisable(true);
        fromEntranceBox1.setDisable(false);
        fromCharCardBox1.setDisable(false);
    }
}
