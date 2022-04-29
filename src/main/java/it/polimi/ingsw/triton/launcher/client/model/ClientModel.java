package it.polimi.ingsw.triton.launcher.client.model;

import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.ArrayList;

/**
 * This class saves the state of the game and relevant information.
 */
public class ClientModel extends Observable<Object> {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
