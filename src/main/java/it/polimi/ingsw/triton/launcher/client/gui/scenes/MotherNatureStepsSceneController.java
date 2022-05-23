package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MotherNatureReply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.ArrayList;

public class MotherNatureStepsSceneController extends ActionPhaseSceneControllers {

    @FXML
    AnchorPane motherNaturePane;

    @FXML
    ChoiceBox<Integer> stepsChoiceBox;

    @FXML
    Button moveButton;

    public void move(ActionEvent event){
        notify(new MotherNatureReply(stepsChoiceBox.getValue()));
        ((Stage) motherNaturePane.getScene().getWindow()).close();
    }

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        ArrayList<Integer> steps = new ArrayList<>();
        for (int i = 0; i <= clientModel.getLastAssistantCardPlayedPerUsername().get(clientModel.getUsername()).getType().getMaxSteps() + (int)parameters; i++) {
            steps.add(i);
        }
        stepsChoiceBox.getItems().addAll(steps);
        stepsChoiceBox.setOnAction(this::activeButton);
    }

    private void activeButton(ActionEvent event){
        moveButton.setDisable(false);
    }

}
