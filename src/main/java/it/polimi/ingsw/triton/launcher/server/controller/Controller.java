package it.polimi.ingsw.triton.launcher.server.controller;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageExceptionalVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Controller is used to manage the game flow, using visitor pattern to modify the model and to send the correct following message, basing on CLientMessage type
 * Controller is notified by virtual view, when a new message is received
 * Login, number of players and game mode are not managed here, but in server
 * Controller catches all exceptions that model throws
 */

public class Controller implements Observer<ClientMessage> {
    private final Game game;
    private final ArrayList<VirtualView> virtualViews = new ArrayList<>();

    public Controller(Game game) {
        this.game = game;
    }

    public void createLoginReplyMessage(String username){
        getVirtualViewByUsername(username).showLoginReply();
    }

    /**
     * Calls a method in virtualView to send the request to a player for selecting a tower color.
     *
     * @param username the receiver username of the message
     */
    public void createTowerColorRequestMessage(String username) {
        game.setGameState(GameState.SETUP);
        getVirtualViewByUsername(username).askTowerColor(game.getTowerColorChosen());
    }

    /**
     * This method is called by a virtual view notify
     * @param message the message that was received from the client
     * Received message is used to modify the model, using overriding to establish what changes I have to do
     * modifyModel throws some exceptions, to manage the game flow correctly also in exceptional situations:
     * 4 situations can be verified:
     * Standard situation, the game is following the regular flow
     * Error situation, the user gave a wrong input, so the message must be re-sent
     * Exceptional situation, this game micro-phase is ended, so we need to start the next one. For example turn is changed.
     * End game situations, the game is over and winner must be calculated
     */
    @Override
    public void update(ClientMessage message) {
        //if(message.getSenderUsername().equals(game.getCurrentPlayer().getUsername())) {
            try {
                message.modifyModel(new ClientMessageModifierVisitor(game));
                message.createStandardNextMessage(new ClientMessageStandardVisitor(game, getVirtualViewByUsername(game.getCurrentPlayer().getUsername())));
            } catch (IllegalClientInputException e) {
                getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).showErrorMessage(e.getTypeError());
                message.createInputErrorMessage(new ClientMessageErrorVisitor(game, getVirtualViewByUsername(game.getCurrentPlayer().getUsername())));
            } catch (LastMoveException | CharacterCardWithParametersException | ChangeTurnException e) {
                message.createExceptionalNextMessage(new ClientMessageExceptionalVisitor(game, getVirtualViewByUsername(game.getCurrentPlayer().getUsername())));
            } catch (EndGameException e) {
                game.calculateWinner();
            }
        /*}
        else
            getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).showErrorMessage(ErrorTypeID.NOT_YOUR_TURN);
         */

    }

    public void addGameObserver(VirtualView virtualView) {
        game.addObserver(virtualView);
        for(Island island: game.getIslands())
            island.addObserver(virtualView);
    }

    public void addPlayer(String username) throws IllegalArgumentException {
        game.addPlayer(username);
    }

    public VirtualView getVirtualViewByUsername(String username) throws NoSuchElementException {
        for (VirtualView vw : virtualViews) {
            if (vw.getUsername().equals(username))
                return vw;
        }
        throw new NoSuchElementException("The virtualview does not exist");
    }

    public ArrayList<VirtualView> getVirtualViews() {
        return virtualViews;
    }

    public void disconnectPlayers(){
        if(game.getGameState() != GameState.LOGIN) {
            game.disconnectPlayers();
            virtualViews.clear();
        }
    }
}