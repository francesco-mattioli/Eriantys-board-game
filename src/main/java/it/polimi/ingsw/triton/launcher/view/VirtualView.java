package it.polimi.ingsw.triton.launcher.view;

import it.polimi.ingsw.triton.launcher.network.Observable;
import it.polimi.ingsw.triton.launcher.network.Observer;
import it.polimi.ingsw.triton.launcher.network.client.ServeOneClient;
import it.polimi.ingsw.triton.launcher.network.message.*;

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
        serveOneClient.sendMessage(new PlayersNumbersAndModeRequest());
    }

    public void sendErrorMessage(String errorMessage){
        serveOneClient.sendMessage(new ErrorMessage(errorMessage));
    }

    public void askTowerColor(boolean[] availableColors){
        serveOneClient.sendMessage(new TowerColorRequest(availableColors, username));
    }

}
