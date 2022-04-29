package it.polimi.ingsw.triton.launcher.server.controller;

import it.polimi.ingsw.triton.launcher.client.cli.Cli;
import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect01;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect03;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect05;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;

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

    /*@Override
    public void update(Message message) {
        if(message.getMessageType() == MessageType.TOWER_COLOR_REPLY){
            game.chooseTowerColor(((ClientMessage)message).getSenderUsername(), ((TowerColorReply) message).getPlayerColor());
        }
        if(message.getMessageType() == MessageType.ASSISTANT_CARD_REPLY){
            game.createTowerColorRequestMessage((((ClientMessage)message).getSenderUsername()));
        }

    }*/

    @Override
    public void update(Message message) {
        switch (game.getGameState()){
            case LOGIN:
                loginPhaseSwitch(message);
            break;
            case PLANNING_PHASE:
                planningPhaseSwitch(message);
            break;
            case BEGIN_ACTION_PHASE:
            break;
            case MIDDLE_ACTION_PHASE:
            break;
            case END_ACTION_PHASE:
            break;
            default:
            break;
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

    /**
     * Calls a method in game to send the request to a player of selecting a tower color.
     * @param username the receiver username of the message
     */
    public void createTowerColorRequestMessage(String username){
        game.createTowerColorRequestMessage(username);
    }

    /**
     * In the login phase, it selects the methods in game to setting some properties to the players.
     * @param message the message received.
     */
    private void loginPhaseSwitch(Message message){
        switch(message.getMessageType()){
            case TOWER_COLOR_REPLY:
                game.chooseTowerColor(((ClientMessage)message).getSenderUsername(), ((TowerColorReply)message).getPlayerColor());
            break;
            case WIZARD_REPLY:
                game.chooseWizard(((ClientMessage)message).getSenderUsername(), ((WizardReply)message).getPlayerWizard());
            break;
            default:
                characterCardsParametersSwitch(message);
            break;
        }
    }

    /**
     * In the planning phase, it selects the methods in game to do some actions.
     * @param message the message received.
     */
    private void planningPhaseSwitch(Message message){
        switch(message.getMessageType()){
            case ASSISTANT_CARD_REPLY:
                game.chooseAssistantCard(((ClientMessage)message).getSenderUsername(), ((AssistantCardReply)message).getChosenAssistantCard());
            break;
            case CHARACTER_CARD_REQUEST:
                game.createCharacterCardsMessage();  //To modify, we need to check if the senderUsername is the current player.
            break;
            case CHARACTER_CARD_CHOICE:
                game.manageEffectCharacterCards(((CharacterCardChoice)message).getSelectedCharacterCard().getId());
            break;
            default:
                characterCardsParametersSwitch(message);
            break;
        }
    }

    /**
     * It manages the creation of the effects and calls a method in game to execute them.
     * @param message the message received
     */
    private void characterCardsParametersSwitch(Message message){
        CardEffect cardEffect;
        switch (message.getMessageType()){
            case CHARACTER_CARD_01_PARAMETER:
                cardEffect = new CardEffect01(game.getCharacterCardById(1), ((CharacterCard01Reply)message).getStudent(),((CharacterCard01Reply)message).getIsland(), game.getBag());
                game.useCharacterCardsWithPreparation(game.getCharacterCardById(1), cardEffect);
            break;
            case CHARACTER_CARD_03_PARAMETER:
                cardEffect = new CardEffect03(((CharacterCard03Reply)message).getIsland(), game.getPlayers(), game.getProfessors());
                game.useCharacterCardsWithPreparation(game.getCharacterCardById(3), cardEffect);
            break;
            case CHARACTER_CARD_05_PARAMETER:
                cardEffect = new CardEffect05(((CharacterCard05Reply)message).getIsland(), game.getCharacterCardById(5));
                game.useCharacterCardsWithPreparation(game.getCharacterCardById(5), cardEffect);
            break;
            case CHARACTER_CARD_09_PARAMETER:
            break;
            case CHARACTER_CARD_10_PARAMETER:
            break;
            case CHARACTER_CARD_11_PARAMETER:
            break;
            case CHARACTER_CARD_12_PARAMETER:
            break;
        }
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
