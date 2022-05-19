package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.Island;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public Button getPlayCCButton() {
        return playCCButton;
    }

    private Map<String, Color> colorMap;

    public void move(ActionEvent event){
        Stage stage = (Stage) moveStudentFromEntrancePane.getScene().getWindow();
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

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        colorMap = new HashMap<>();
        //ArrayList<Integer> islandsId = new ArrayList<>();
        String[] whereMove = {"dining room", "island"};
        /*for (Island island: clientModel.getIslands()) {
            islandsId.add(island.getId());
        }
        for (int i = 0; i < clientModel.getMySchoolBoard().getEntrance().length; i++) {
            if (clientModel.getMySchoolBoard().getEntrance()[i] != 0) {
                colorMap.put(Color.values()[i].name(), Color.values()[i]);
            }
        }*/
        setUpEntranceChoiceBox(clientModel);
        colorChoiceBox.getItems().addAll(colorMap.keySet());
        islandIdChoiceBox.getItems().addAll(setUpIslandIdChoiceBox(clientModel));
        whereChoiceBox.getItems().addAll(whereMove);
        whereChoiceBox.setOnAction(this::show);
    }
}
