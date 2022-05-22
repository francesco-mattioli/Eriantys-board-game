package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;


public abstract class SceneController extends Observable<Message> {
    protected String username;
    protected String[] colorsName = new String[5];

    public void setUsername(String username){
        this.username = username;
    }

    public <T> void setupScene(ClientModel clientModel, T parameters){
    }

    public void setupStudentsChoiceBox(ChoiceBox<String> choiceBox, int [] students){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < students.length; i++) {
            if (students[i] != 0) {
                list.add(Color.values()[i].name());
            }
        }
        choiceBox.getItems().addAll(list);
    }

    public void setUpAllColors(){
        for (int i = 0; i < Color.values().length; i++){
            colorsName[i] = Color.values()[i].name();
        }
    }

}
