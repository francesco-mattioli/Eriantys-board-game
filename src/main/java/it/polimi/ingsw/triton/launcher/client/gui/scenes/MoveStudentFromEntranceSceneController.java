package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MoveStudentOntoDiningRoomMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MoveStudentOntoIslandMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.stream.Collectors;


public class MoveStudentFromEntranceSceneController extends ActionPhaseSceneControllers {
    private static final String ISLAND = "island";
    private static final String DINING_ROOM = "dining room";

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
    Button moveButton;

    /**
     * This method sends a message to server, containing student and position where move it
     */
    public void move() {
        if (whereChoiceBox.getValue().equals(DINING_ROOM)) {
            notify(new MoveStudentOntoDiningRoomMessage(Color.valueOf(colorChoiceBox.getValue())));
        } else {
            notify(new MoveStudentOntoIslandMessage(islandIdChoiceBox.getValue(), Color.valueOf(colorChoiceBox.getValue())));
        }
        ((Stage) moveStudentFromEntrancePane.getScene().getWindow()).close();
    }

    /**
     * When students chooses "island" into second choice box, the island choice box is shown
     */
    public void show() {
        if (whereChoiceBox.getValue().equals(ISLAND)) {
            islandIdLabel.setVisible(true);
            islandIdChoiceBox.setVisible(true);
        } else {
            islandIdLabel.setVisible(false);
            islandIdChoiceBox.setVisible(false);
        }
    }

    /**
     * This method prepares the scene, populating choice boxes and setting everything correctly
     *
     * @param clientModel clientModel
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        String[] whereMove = {DINING_ROOM, ISLAND};
        setupStudentsChoiceBox(colorChoiceBox, clientModel.getMySchoolBoard().getEntrance());
        islandIdChoiceBox.getItems().addAll(clientModel.getIslands().stream().map(Island::getId).collect(Collectors.toList()));
        whereChoiceBox.getItems().addAll(whereMove);
        whereChoiceBox.setOnAction(this::activateButton);
        colorChoiceBox.setOnAction(this::activateButton);
        islandIdChoiceBox.setOnAction(this::activateButton);
    }

    /**
     * At the beginning there are 2 visible choice boxes : we need to enable the button only if all choice boxes don't contain null
     * if users select "Island" in second choice box, the third choice box is set visible, and contains the available island numbers
     *
     * @param event onClick
     */
    private void activateButton(ActionEvent event) {
        if (whereChoiceBox.getValue() != null && whereChoiceBox.getValue().equals(ISLAND)) {
            islandIdLabel.setVisible(true);
            islandIdChoiceBox.setVisible(true);
        } else {
            islandIdLabel.setVisible(false);
            islandIdChoiceBox.setVisible(false);
        }
        moveButton.setDisable(moveStudentFromEntrancePane.getChildren().stream().filter(
                ChoiceBox.class::isInstance).filter(
                Node::isVisible).map(
                x -> ((ChoiceBox<?>) x).getValue()).collect(
                Collectors.toList()).contains(null));
    }

}
