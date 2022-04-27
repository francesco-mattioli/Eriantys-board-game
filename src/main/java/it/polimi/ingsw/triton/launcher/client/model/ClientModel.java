package it.polimi.ingsw.triton.launcher.client.model;

import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.ArrayList;

public class ClientModel extends Observable<Object> {
    private String username;

        public void setUsername(String username) {
        this.username = username;
    }

    public void addPlayerUsername(String username){
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}
