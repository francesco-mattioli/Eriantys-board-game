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

    /**
     * When user clicks on button, a message is sent to server containing the number of steps that mother nature has to do
     */
    public void move(){
        notify(new MotherNatureReply(stepsChoiceBox.getValue()));
        ((Stage) motherNaturePane.getScene().getWindow()).close();
    }

    /**
     * This method prepares the scene, populating choice box and setting everything correctly
     * @param clientModel clientModel
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        ArrayList<Integer> steps = new ArrayList<>();
        for (int i = 0; i <= clientModel.getLastAssistantCardPlayedPerUsername().get(clientModel.getUsername()).getType().getMaxSteps() + (int)parameters; i++) {
            steps.add(i);
        }
        stepsChoiceBox.getItems().addAll(steps);
        stepsChoiceBox.setOnAction(this::activateButton);
    }

    private void activateButton(ActionEvent event){
        moveButton.setDisable(false);
    }

}
