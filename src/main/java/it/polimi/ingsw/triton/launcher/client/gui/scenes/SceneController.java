package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class SceneController extends Observable<Message> {
    protected String username;
    private Map<String,Color> colorMap = new HashMap<>();

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

    public Map<String,Color> setUpColorChoiceBox(ClientModel clientModel) {
        colorMap.clear();
        for (int i = 0; i < clientModel.getMySchoolBoard().getEntrance().length; i++) {
            if (clientModel.getMySchoolBoard().getEntrance()[i] != 0) {
                colorMap.put(Color.values()[i].name(), Color.values()[i]);
            }
        }
        return colorMap;
    }

    public Map<String,Color> setUpCharCardChoiceBox(ClientModel clientModel, int id) {
        colorMap.clear();
        for (int i = 0; i < clientModel.getCharacterCardById(id).getStudents().length; i++) {
            if (clientModel.getCharacterCardById(id).getStudents()[i] != 0) {
                colorMap.put(Color.values()[i].name(), Color.values()[i]);
            }
        }
        return colorMap;
    }



}
