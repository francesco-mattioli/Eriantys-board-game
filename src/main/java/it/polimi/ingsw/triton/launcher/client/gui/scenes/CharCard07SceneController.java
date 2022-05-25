package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.CharacterCard07Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    /**
     * This method prepares the form to ask character card 7 parameters
     * Choice boxes are populated with available colors and islands
     * This card allows user to exchange up to 3 students between entrance and character card,
     * so at the beginning is only populated the first couple of choice boxes, to exchange first two students
     * @param clientModel clientModel
     * @param parameters a generic parameter which depends, based on specific scene
     * @param <T> generic parameter
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        this.clientModel = clientModel;
        setChoiceBoxEntrance(fromEntrance1);
        setChoiceBoxCharCard(fromCharCard1);
        currentButton = confirmButton1;
        charCard07Pane.getChildren().stream().filter(ChoiceBox.class::isInstance).forEach(x->((ChoiceBox<?>) x).setOnAction(this::activateButton));
    }


    /**
     * When user confirms first 2 students to exchange
     * The first couple of choice boxes is disabled, and second one is populated
     */
    public void confirm1(){
        updateSwitchStudents(fromEntrance1, fromCharCard1);
        setChoiceBoxCharCard(fromCharCard2);
        setChoiceBoxEntrance(fromEntrance2);
        disableButtonAndChoiceBox(confirmButton1, fromEntrance1, fromEntrance2, fromCharCard1, fromCharCard2);
        currentButton = confirmButton2;
    }

    /**
     * When user confirms second 2 students to exchange
     * The second couple of choice boxes is disabled, and third one is populated
     */
    public void confirm2(){
        updateSwitchStudents(fromEntrance2, fromCharCard2);
        setChoiceBoxCharCard(fromCharCard3);
        setChoiceBoxEntrance(fromEntrance3);
        disableButtonAndChoiceBox(confirmButton2, fromEntrance2, fromEntrance3, fromCharCard2, fromCharCard3);
        currentButton = confirmButton3;
    }

    /**
     * When user confirms last 2 students to exchange
     * A message is sent to server, which contains character card 7 parameters
     */
    public void confirm3(){
        updateSwitchStudents(fromEntrance3, fromCharCard3);
        fromEntrance3.setDisable(true);
        fromCharCard3.setDisable(true);
        confirmButton3.setDisable(true);
        stopButton.setDisable(true);
        notify(new CharacterCard07Reply(fromCharCard,fromEntrance, clientModel.getCharacterCardById(7).getStudents()));
    }

    /**
     * User can stop to move students at every time, so when he clicks stop button the message is sent immediately,
     * containing only students he has chosen until that moment
     */
    public void stop(){
        fromEntrance1.setDisable(true);
        fromCharCard1.setDisable(true);
        confirmButton1.setDisable(true);
        fromEntrance2.setDisable(true);
        fromCharCard2.setDisable(true);
        confirmButton2.setDisable(true);
        fromEntrance3.setDisable(true);
        fromCharCard3.setDisable(true);
        confirmButton3.setDisable(true);
        notify(new CharacterCard07Reply(fromCharCard,fromEntrance, clientModel.getCharacterCardById(7).getStudents()));
    }


    /**
     * setting entrance choice box, adding all available students on entrance
     * @param choiceBox the choice box to populate
     */
    public void setChoiceBoxEntrance(ChoiceBox<String> choiceBox){
        int [] array = new int[clientModel.getMySchoolBoard().getEntrance().length];
        for(int i = 0; i<clientModel.getMySchoolBoard().getEntrance().length; i++){
            array[i] = clientModel.getMySchoolBoard().getEntrance()[i] - fromEntrance[i];
        }
        setupStudentsChoiceBox(choiceBox, array);
    }

    /**
     * setting character card choice box, adding all available students on character card.
     * @param choiceBox the choice box to populate
     */
    public void setChoiceBoxCharCard(ChoiceBox<String> choiceBox){
        int [] array = new int[clientModel.getCharacterCardById(7).getStudents().length];
        for(int i = 0; i<clientModel.getCharacterCardById(7).getStudents().length; i++){
            array[i] = clientModel.getCharacterCardById(7).getStudents()[i] - fromCharCard[i];
        }
        setupStudentsChoiceBox(choiceBox,array);
    }

    /**
     * when a student is chosen, that place will have one less student
     * for example, if I move a red student from entrance, entrance will have one less red student
     * @param fromCharCardBox the choice box of character card
     * @param fromEntranceBox the choice box of entrance
     */
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

    /**
     * At the beginning, button is disabled, because user must choose a couple of students
     * When choice boxes contain a value, button is activated
     * @param event on choice box action
     */
    private void activateButton(ActionEvent event){
        currentButton.setDisable(charCard07Pane.getChildren().stream().filter(
                ChoiceBox.class::isInstance).filter(
                Node::isVisible).filter(
                x -> !x.isDisable()).map(
                x -> ((ChoiceBox<?>) x).getValue()).collect(
                Collectors.toList()).contains(null));
    }

}
