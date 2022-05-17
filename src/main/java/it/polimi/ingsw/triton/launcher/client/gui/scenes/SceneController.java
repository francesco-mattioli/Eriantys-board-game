package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

public abstract class SceneController extends Observable<Message> {
    protected String username;

    public void setUsername(String username){
        this.username = username;
    }
    public <T> void setupScene(ClientModel clientModel, T parameters){

    }
}
