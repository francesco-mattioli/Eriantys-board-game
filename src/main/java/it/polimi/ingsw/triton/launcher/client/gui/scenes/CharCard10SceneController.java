package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.CharacterCard10Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.util.stream.Collectors;

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

    private final int[] fromEntrance = new int[5];
    private final int[] fromDiningRoom = new int[5];
    private ClientModel clientModel;
    private Button currentButton;

    /**
     * This method prepares the form to ask character card 10 parameters
     * Choice boxes are populated with available colors and islands
     * This card allows user to exchange up to 3 students between entrance and dining room,
     * so at the beginning is only populated the first couple of choice boxes, to exchange first two students
     * @param clientModel clientModel
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        this.clientModel = clientModel;
        setChoiceBoxEntrance(fromEntrance1);
        setChoiceBoxDiningRoom(fromDiningRoom1);
        currentButton = confirmButton1;
        charCard10Pane.getChildren().stream().filter(ChoiceBox.class::isInstance).forEach(x->((ChoiceBox<?>) x).setOnAction(this::activateButton));

    }

    /**
     * When user confirms first 2 students to exchange
     * The first couple of choice boxes is disabled, and second one is populated
     */
    public void confirm1(){
        updateSwitchStudents(fromEntrance1, fromDiningRoom1);
        setChoiceBoxDiningRoom(fromDiningRoom2);
        setChoiceBoxEntrance(fromEntrance2);
        disableButtonAndChoiceBox(confirmButton1, fromEntrance1, fromEntrance2, fromDiningRoom1, fromDiningRoom2);
        currentButton = confirmButton2;
    }

    /**
     * When user confirms last 2 students to exchange
     * A message is sent to server, which contains character card 10 parameters
     */
    public void confirm2(){
        updateSwitchStudents(fromEntrance2, fromDiningRoom2);
        fromEntrance2.setDisable(true);
        fromDiningRoom2.setDisable(true);
        confirmButton2.setDisable(true);
        stopButton.setDisable(true);
        notify(new CharacterCard10Reply(username,fromEntrance, fromDiningRoom));
        currentButton = confirmButton2;
    }


    /**
     * User can stop to move students at every time, so when he clicks stop button the message is sent immediately,
     * containing only students he has chosen until that moment
     */
    public void stop(){
        fromEntrance1.setDisable(true);
        fromDiningRoom1.setDisable(true);
        confirmButton1.setDisable(true);
        fromEntrance2.setDisable(true);
        fromDiningRoom2.setDisable(true);
        confirmButton2.setDisable(true);
        notify(new CharacterCard10Reply(username, fromEntrance, fromDiningRoom));
    }

    /**
     * setting entrance choice box, adding all available students on entrance
     */
    public void setChoiceBoxEntrance(ChoiceBox<String> choiceBox){
        int [] array = new int[clientModel.getMySchoolBoard().getEntrance().length];
        for(int i = 0; i<clientModel.getMySchoolBoard().getEntrance().length; i++){
            array[i] = clientModel.getMySchoolBoard().getEntrance()[i] - fromEntrance[i];
        }
        setupStudentsChoiceBox(choiceBox, array);
    }

    /**
     * setting dining room choice box, adding all available students on dining room
     */
    public void setChoiceBoxDiningRoom(ChoiceBox<String> choiceBox){
        int [] array = new int[clientModel.getMySchoolBoard().getDiningRoom().length];
        for(int i = 0; i<clientModel.getMySchoolBoard().getDiningRoom().length; i++){
            array[i] = clientModel.getMySchoolBoard().getDiningRoom()[i] - fromDiningRoom[i];
        }
        setupStudentsChoiceBox(choiceBox, array);
    }

    /**
     * when a student is chosen, that place will have one less student
     * for example, if I move a red student from entrance, entrance will have one less red student
     */
    public void updateSwitchStudents(ChoiceBox<String> fromEntranceBox, ChoiceBox<String> fromDiningRoomBox){
        fromEntrance[Color.valueOf(fromEntranceBox.getValue()).ordinal()] ++;
        fromDiningRoom[Color.valueOf(fromDiningRoomBox.getValue()).ordinal()] ++;
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
     */
    private void activateButton(ActionEvent event){
        currentButton.setDisable(charCard10Pane.getChildren().stream().filter(
                ChoiceBox.class::isInstance).filter(
                Node::isVisible).filter(
                x -> !x.isDisable()).map(
                x -> ((ChoiceBox<?>) x).getValue()).collect(
                Collectors.toList()).contains(null));
    }
}

