package it.polimi.ingsw.triton.launcher.server.model.player;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.Action;

public class Player {
    private final String username;
    private int moveCounter;
    private Wallet wallet;

    private AssistantDeck assistantDeck;
    private AssistantCard lastPlayedAssistantCard;
    private SchoolBoard schoolBoard;

    public Player(String username) {
        this.username = username;
        this.moveCounter = 0;
        this.wallet = new Wallet();
    }

    /**
     * @param action to be executed
     */
    public void executeAction(Action action) {
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
    // Comments for developers: the action should be called from the Game in this way.
    // player.executeAction( new PlayCard(..arguments..) )

}