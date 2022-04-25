package it.polimi.ingsw.triton.launcher.controller;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.network.Observer;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;
import it.polimi.ingsw.triton.launcher.network.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.network.message.clientmessage.TowerColorReply;
import it.polimi.ingsw.triton.launcher.view.VirtualView;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Server has a reference to Controller. This reference is passed to
 * ServeOneClient, so the latter can send new data to the Controller.
 *
  */

public class Controller implements Observer<Message> {
    private Game game;
    private ArrayList<VirtualView> virtualViews= new ArrayList<>();

    public ArrayList<VirtualView> getVirtualViews() {
        return virtualViews;
    }

    public Controller(Game game){
        this.game = game;
    }

    public void addPlayer(String username) throws IllegalArgumentException{
        game.addPlayer(username);
    }

    @Override
    public void update(Message message) {
        if(message.getMessageType() == MessageType.TOWER_COLOR_REPLY){
            game.chooseTowerColor(((ClientMessage)message).getSenderUsername(), ((TowerColorReply) message).getPlayerColor());
        }
        if(message.getMessageType() == MessageType.FULL_LOBBY){
            game.createTowerColorRequestMessage(((ClientMessage)message).getSenderUsername());
        }
        if(message.getMessageType() == MessageType.ASSISTANT_CARD_REPLY){
            game.createTowerColorRequestMessage((((ClientMessage)message).getSenderUsername()));
        }

    }

    public void addGameObserver(VirtualView virtualView){
        game.addObserver(virtualView);
    }

    public VirtualView getVirtualViewByUsername(String username) throws NoSuchElementException{
        for(VirtualView vw : virtualViews){
            if (vw.getUsername().equals(username))
                return vw;
        }
        throw new NoSuchElementException("The virtualview does not exist");
    }
}





    /*private Game game;


    public void addPlayer(String username){
        if(game.isUsernameChosen(username)){
            //return an error message
        }
        else
            game.addPlayer(username);
    }
     */


    // ---------------------------------------------------------
    // TO DELETE: some examples to check that the server works
    // example, just to test if the server works
    /*private String username;


    // this is called by ServeOneClient
    public void setUsername(String username) {
        this.username = username;
    }

    public void print() {
        System.out.println("bro:" + username);
    }*/
