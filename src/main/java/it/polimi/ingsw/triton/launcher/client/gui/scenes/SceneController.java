package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SceneController extends Observable<Message> {
    protected String username;
    private Map<String,Color> colorMap = new HashMap<>();
    protected ArrayList<String> colorEntrance = new ArrayList<>();
    protected ArrayList<String> colorCharCard = new ArrayList<>();
    protected ArrayList<String> colorDiningRoom = new ArrayList<>();
    protected String[] colorsName = new String[5];
    public void setUsername(String username){
        this.username = username;
    }
    public <T> void setupScene(ClientModel clientModel, T parameters){
    }

    public ArrayList<Integer> setUpIslandIdChoiceBox(ClientModel clientModel){
        ArrayList<Integer> islandsId = new ArrayList<>();
        for (Island island: clientModel.getIslands()) {
            islandsId.add(island.getId());
        }
        return islandsId;
    }

    public void setUpEntranceChoiceBox(ClientModel clientModel) {
        colorEntrance.clear();
        for (int i = 0; i < clientModel.getMySchoolBoard().getEntrance().length; i++) {
            if (clientModel.getMySchoolBoard().getEntrance()[i] != 0) {
                colorEntrance.add(Color.values()[i].name());
            }
        }
    }

    public void setUpCharCardChoiceBox(ClientModel clientModel, int id) {
        colorCharCard.clear();
        for (int i = 0; i < clientModel.getCharacterCardById(id).getStudents().length; i++) {
            if (clientModel.getCharacterCardById(id).getStudents()[i] != 0) {
                colorCharCard.add(Color.values()[i].name());
            }
        }
    }

    public void setUpEntranceChoiceBox(ClientModel clientModel, int[] array) {
        colorEntrance.clear();
        for (int i = 0; i < clientModel.getMySchoolBoard().getEntrance().length; i++) {
            if (clientModel.getMySchoolBoard().getEntrance()[i] - array[i] != 0) {
                colorEntrance.add(Color.values()[i].name());
            }
        }
    }

    public void setUpDiningRoomChoiceBox(ClientModel clientModel, int[] array) {
        colorDiningRoom.clear();
        for (int i = 0; i < clientModel.getMySchoolBoard().getDiningRoom().length; i++) {
            if (clientModel.getMySchoolBoard().getDiningRoom()[i] - array[i] != 0 ) {
                colorDiningRoom.add(Color.values()[i].name());
            }
        }
    }

    public void setUpCharCardChoiceBox(ClientModel clientModel, int id, int[] array) {
        colorCharCard.clear();
        for (int i = 0; i < clientModel.getCharacterCardById(id).getStudents().length; i++) {
            if (clientModel.getCharacterCardById(id).getStudents()[i] - array[i] != 0) {
                colorCharCard.add(Color.values()[i].name());
            }
        }
    }

    public void setUpAllColors(){
        for (int i = 0; i < Color.values().length; i++){
            colorsName[i] = Color.values()[i].name();
        }
    }


    public abstract AnchorPane getAnchorPane();
    public abstract Button getButton();
    public abstract String getPath();


}
