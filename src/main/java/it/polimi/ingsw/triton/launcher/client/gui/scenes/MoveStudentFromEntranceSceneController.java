package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MoveStudentOntoDiningRoomMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MoveStudentOntoIslandMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.stream.Collectors;


public class MoveStudentFromEntranceSceneController extends SceneController {

    @FXML
    AnchorPane moveStudentFromEntrancePane;

    @FXML
    ChoiceBox<String> colorChoiceBox;

    @FXML
    ChoiceBox<String> whereChoiceBox;

    @FXML
    ChoiceBox<Integer> islandIdChoiceBox;

    @FXML
    Label islandIdLabel;

    @FXML
    Button playCCButton;

    @FXML
    Button moveButton;

    public Button getPlayCCButton() {
        return playCCButton;
    }


    public void move(ActionEvent event){
        Stage stage = (Stage) moveStudentFromEntrancePane.getScene().getWindow();
        if (whereChoiceBox.getValue().equals("dining room")){
            notify(new MoveStudentOntoDiningRoomMessage(username,Color.valueOf(colorChoiceBox.getValue())));
            stage.close();
        }
        else {
            notify(new MoveStudentOntoIslandMessage(username,islandIdChoiceBox.getValue(),Color.valueOf(colorChoiceBox.getValue())));
            stage.close();
        }
    }


    public void show(ActionEvent event){
        if(whereChoiceBox.getValue().equals("island")){
            islandIdLabel.setVisible(true);
            islandIdChoiceBox.setVisible(true);
        }
        else {
            islandIdLabel.setVisible(false);
            islandIdChoiceBox.setVisible(false);
        }
    }

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        String[] whereMove = {"dining room", "island"};
        setUpEntranceChoiceBox(clientModel);
        colorChoiceBox.getItems().addAll(colorEntrance);
        islandIdChoiceBox.getItems().addAll(setUpIslandIdChoiceBox(clientModel));
        whereChoiceBox.getItems().addAll(whereMove);
        whereChoiceBox.setOnAction(this::activeButton);
        colorChoiceBox.setOnAction(this::activeButton);
        islandIdChoiceBox.setOnAction(this::activeButton);
    }

    private void activeButton(ActionEvent event){
        if(whereChoiceBox.getValue() != null && whereChoiceBox.getValue().equals("island")){
            islandIdLabel.setVisible(true);
            islandIdChoiceBox.setVisible(true);
        }
        else {
            islandIdLabel.setVisible(false);
            islandIdChoiceBox.setVisible(false);
        }
        if(!moveStudentFromEntrancePane.getChildren().stream().filter(
                x->x instanceof ChoiceBox).filter(
                x->x.isVisible()).map(
                x->((ChoiceBox<?>) x).getValue()).collect(
                Collectors.toList()).contains(null)){
            moveButton.setDisable(false);
        }
        else{
            moveButton.setDisable(true);
        }
    }


    @Override
    public AnchorPane getAnchorPane() {
        return moveStudentFromEntrancePane;
    }

    @Override
    public Button getButton() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }
}
