package it.polimi.ingsw.triton.launcher.server.view;

import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.utils.message.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ErrorMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.PlayersNumbersAndModeRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.TowerColorRequest;
import it.polimi.ingsw.triton.launcher.utils.View;

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
