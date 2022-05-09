package it.polimi.ingsw.triton.launcher.server.model.player;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.Action;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

import java.io.Serializable;

public class Player implements Serializable {
    private final String username;
    private int moveCounter;
    private Wallet wallet;
    private AssistantDeck assistantDeck;
    private AssistantCard lastPlayedAssistantCard;
    private SchoolBoard schoolBoard;
    private boolean alreadyPlayedAnAssistantCard;

    public Player(String username) {
        this.username = username;
        this.moveCounter = 0;
        this.wallet = new Wallet();
        this.alreadyPlayedAnAssistantCard=false;
    }

    /**
     * @param action to be executed
     */
    public void executeAction(Action action) throws IllegalClientInputException{
        action.execute();
    }

    /**
     * @param wizard chosen by the player.
     * This method instantiates the deck of the corresponding wizard.
     */
    public void setWizard(Wizard wizard) {
        assistantDeck = new AssistantDeck(wizard);
    }

    public String getUsername() {
        return username;
    }

    public SchoolBoard getSchoolBoard() {
        return this.schoolBoard;
    }


    public void setSchoolBoard(TowerColor towerColor, int numPlayers) {
        this.schoolBoard = new SchoolBoard(towerColor, numPlayers);
    }

    public void setAssistantDeck(AssistantDeck assistantDeck) {
        this.assistantDeck = assistantDeck;
    }

    public void setLastPlayedAssistantCard(AssistantCard lastPlayedAssistantCard) {
        this.lastPlayedAssistantCard = lastPlayedAssistantCard;
    }

    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }

    public AssistantCard getLastPlayedAssistantCard() {
        return lastPlayedAssistantCard;
    }

    public Wallet getWallet(){
        return wallet;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public boolean hasAlreadyPlayedACharacterCard() {
        return alreadyPlayedAnAssistantCard;
    }

    public void setTrueHasAlreadyPlayedACharacterCard(){
        this.alreadyPlayedAnAssistantCard=true;
    }

    public void resetAlreadyPlayedAnCharacterCard(){
        this.alreadyPlayedAnAssistantCard=false;
    }


    // Comments for developers: the action should be called from the Game in this way.
    // player.executeAction( new PlayCard(..arguments..) )

}
