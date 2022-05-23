package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.CharacterCard07Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.util.stream.Collectors;

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

    private final int[] fromEntrance = new int[5];
    private final int[] fromCharCard = new int[5];
    private ClientModel clientModel;
    private Button currentButton;

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        this.clientModel = clientModel;
        setChoiceBoxEntrance(fromEntrance1);
        setChoiceBoxCharCard(fromCharCard1);
        currentButton = confirmButton1;
        charCard07Pane.getChildren().stream().filter(x -> x instanceof ChoiceBox).forEach(x->((ChoiceBox<?>) x).setOnAction(this::activateButton));
    }


    public void confirm1(ActionEvent event){
        updateSwitchStudents(fromEntrance1, fromCharCard1);
        setChoiceBoxCharCard(fromCharCard2);
        setChoiceBoxEntrance(fromEntrance2);
        disableButtonAndChoiceBox(confirmButton1, fromEntrance1, fromEntrance2, fromCharCard1, fromCharCard2);
        currentButton = confirmButton2;
    }

    public void confirm2(ActionEvent event){
        updateSwitchStudents(fromEntrance2, fromCharCard2);
        setChoiceBoxCharCard(fromCharCard3);
        setChoiceBoxEntrance(fromEntrance3);
        disableButtonAndChoiceBox(confirmButton2, fromEntrance2, fromEntrance3, fromCharCard2, fromCharCard3);
        currentButton = confirmButton3;
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


    public void setChoiceBoxEntrance(ChoiceBox<String> choiceBox){
        int [] array = new int[clientModel.getMySchoolBoard().getEntrance().length];
        for(int i = 0; i<clientModel.getMySchoolBoard().getEntrance().length; i++){
            array[i] = clientModel.getMySchoolBoard().getEntrance()[i] - fromEntrance[i];
        }
        setupStudentsChoiceBox(choiceBox, array);
    }

    public void setChoiceBoxCharCard(ChoiceBox<String> choiceBox){
        int [] array = new int[clientModel.getCharacterCardById(7).getStudents().length];
        for(int i = 0; i<clientModel.getCharacterCardById(7).getStudents().length; i++){
            array[i] = clientModel.getCharacterCardById(7).getStudents()[i] - fromCharCard[i];
        }
        setupStudentsChoiceBox(choiceBox,array);
    }

    public void updateSwitchStudents(ChoiceBox<String> fromEntranceBox, ChoiceBox<String> fromCharCardBox){
        fromEntrance[Color.valueOf(fromEntranceBox.getValue()).ordinal()] ++;
        fromCharCard[Color.valueOf(fromCharCardBox.getValue()).ordinal()] ++;
    }

    public void disableButtonAndChoiceBox(Button button, ChoiceBox<String> fromEntranceBox, ChoiceBox<String> fromEntranceBox1, ChoiceBox<String> fromCharCardBox, ChoiceBox<String> fromCharCardBox1){
        fromEntranceBox.setDisable(true);
        fromCharCardBox.setDisable(true);
        button.setDisable(true);
        fromEntranceBox1.setDisable(false);
        fromCharCardBox1.setDisable(false);
    }

    private void activateButton(ActionEvent event){
        if(!charCard07Pane.getChildren().stream().filter(
                x->x instanceof ChoiceBox).filter(
                        x->x.isVisible()).filter(
                                x->!x.isDisable()).map(
                                        x->((ChoiceBox<?>) x).getValue()).collect(
                                                Collectors.toList()).contains(null)){
            currentButton.setDisable(false);
        }
        else{
            currentButton.setDisable(true);
        }
    }

}