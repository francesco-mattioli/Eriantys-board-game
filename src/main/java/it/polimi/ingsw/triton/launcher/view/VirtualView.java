package it.polimi.ingsw.triton.launcher.view;

import it.polimi.ingsw.triton.launcher.network.Observable;
import it.polimi.ingsw.triton.launcher.network.Observer;
import it.polimi.ingsw.triton.launcher.network.ServeOneClient;
import it.polimi.ingsw.triton.launcher.network.message.LoginReply;
import it.polimi.ingsw.triton.launcher.network.message.LoginRequest;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.PlayersNumbersAndModeRequest;

public class VirtualView extends Observable<Message> implements View, Observer<Message> {
    private ServeOneClient serveOneClient;

    public VirtualView(ServeOneClient serveOneClient){
        this.serveOneClient = serveOneClient;
    }

    @Override
    public void update(Message message) {

    }

    public void askNumOfPlayersAndMode(){
        serveOneClient.sendMessage(new PlayersNumbersAndModeRequest());
    }

}
