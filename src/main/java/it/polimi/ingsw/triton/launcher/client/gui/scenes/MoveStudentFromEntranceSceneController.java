package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MoveStudentOntoDiningRoomMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MoveStudentOntoIslandMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Map;

public class MoveStudentFromEntranceSceneController extends Observable<Message> {

    @FXML
    AnchorPane moveStudentFromEntrancePane;

    @FXML
    Button moveButton;

    @FXML
    ChoiceBox<String> colorChoiceBox;

    @FXML
    ChoiceBox<String> whereChoiceBox;

    @FXML
    ChoiceBox<Integer> islandIdChoiceBox;

    @FXML
    Label islandIdLabel;

    private String username;
    private Map<String, Color> colorMap;
    Stage stage;

    public void setUsername(String username){
        this.username = username;
    }

    public ChoiceBox<String> getColorChoiceBox() {
        return colorChoiceBox;
    }

    public ChoiceBox<String> getWhereChoiceBox() {
        return whereChoiceBox;
    }

    public ChoiceBox<Integer> getIslandIdChoiceBox() {
        return islandIdChoiceBox;
    }

    public void setColorMap(Map<String, Color> colorMap) {
        this.colorMap = colorMap;
    }

    public void move(ActionEvent event){
        stage = (Stage) moveStudentFromEntrancePane.getScene().getWindow();
        if (whereChoiceBox.getValue().equals("dining room")){
            notify(new MoveStudentOntoDiningRoomMessage(username,colorMap.get(colorChoiceBox.getValue())));
            stage.close();
        }

        else {
            notify(new MoveStudentOntoIslandMessage(username,islandIdChoiceBox.getValue(),colorMap.get(colorChoiceBox.getValue())));
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
}
