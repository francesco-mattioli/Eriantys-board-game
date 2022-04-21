package it.polimi.ingsw.triton.launcher.view;

import it.polimi.ingsw.triton.launcher.controller.Controller;
import it.polimi.ingsw.triton.launcher.network.Observable;
import it.polimi.ingsw.triton.launcher.network.Observer;
import it.polimi.ingsw.triton.launcher.network.ServeOneClient;
import it.polimi.ingsw.triton.launcher.network.message.*;

public class VirtualView extends Observable implements View, Observer<Message> {
    private ServeOneClient serveOneClient;
    private String username;

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

}