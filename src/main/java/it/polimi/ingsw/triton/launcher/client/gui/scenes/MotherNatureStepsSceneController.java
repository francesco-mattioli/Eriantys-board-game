package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MotherNatureReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MotherNatureStepsSceneController extends Observable<Message> {

    @FXML
    AnchorPane motherNaturePane;

    @FXML
    ChoiceBox<Integer> stepsChoiceBox;

    @FXML
    Button moveButton;

    private String username;
    Stage stage;

    public void setUsername(String username) {
        this.username = username;
    }

    public ChoiceBox<Integer> getStepsChoiceBox() {
        return stepsChoiceBox;
    }

    public void move(ActionEvent event){
        stage = (Stage) motherNaturePane.getScene().getWindow();
        notify(new MotherNatureReply(username, stepsChoiceBox.getValue()));
        stage.close();
    }
}
