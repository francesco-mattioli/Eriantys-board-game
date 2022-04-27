package it.polimi.ingsw.triton.launcher.client.model;

import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.ArrayList;

public class ClientModel extends Observable<Object> {
    private String username;
    private ClientView clientView;

    public ClientModel(ClientView view) {
        this.clientView = view;
    }

    public void addPlayerUsername(String username){
        this.username=username;
        this.clientView.showGenericMessage("Username accepted");
    }

}
