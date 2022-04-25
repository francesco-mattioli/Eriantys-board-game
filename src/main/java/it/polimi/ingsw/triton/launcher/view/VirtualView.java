package it.polimi.ingsw.triton.launcher.view;

import it.polimi.ingsw.triton.launcher.network.Observable;
import it.polimi.ingsw.triton.launcher.network.Observer;
import it.polimi.ingsw.triton.launcher.network.client.ServeOneClient;
import it.polimi.ingsw.triton.launcher.network.message.*;
import it.polimi.ingsw.triton.launcher.network.message.servermessage.ErrorMessage;
import it.polimi.ingsw.triton.launcher.network.message.servermessage.PlayersNumbersAndModeRequest;
import it.polimi.ingsw.triton.launcher.network.message.servermessage.TowerColorRequest;

public class VirtualView extends Observable<Message> implements View, Observer<Message> {
    private ServeOneClient serveOneClient;
    private String username;

    public String getUsername() {
        return username;
    }

    public VirtualView(ServeOneClient serveOneClient, String username){
        this.serveOneClient = serveOneClient;
        this.username = username;
    }

    @Override
    public void update(Message message) {

    }

    public void askNumOfPlayersAndMode(){
        serveOneClient.sendMessage(new PlayersNumbersAndModeRequest(username));
    }

    public void showErrorMessage(ErrorTypeID errorTypeID){
        serveOneClient.sendMessage(new ErrorMessage(errorTypeID, username));
    }

    public void askTowerColor(boolean[] availableColors){
        serveOneClient.sendMessage(new TowerColorRequest(availableColors, username));
    }

    @Override
    public void askUsername() {
        // TO DO
    }
}
