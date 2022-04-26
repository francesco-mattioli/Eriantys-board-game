package it.polimi.ingsw.triton.launcher.network.client;

import it.polimi.ingsw.triton.launcher.network.Observable;
import it.polimi.ingsw.triton.launcher.view.ClientView;

import java.util.ArrayList;

public class ClientModel extends Observable<Object> {
    private ArrayList<String> playerUsernames=new ArrayList<>();

    public ClientModel(ClientView view) {
        this.addObserver(view);
    }

    public void addPlayerUsername(String username){
        playerUsernames.add(username);
        notify(playerUsernames);
        // TO THINK IF IS USEFUL TO USE OBSERVER/OBSERVABLE
    }
}
