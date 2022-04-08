package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.model.playeractions.Action;

import java.util.ArrayList;

/**
 * The type Player.
 */
public class Player {
    private final String username;
    private int moveCounter;
    private AssistantDeck assistantDeck;
    //private AssistantCard lastPlayedAssistantCard;
    private ArrayList<AssistantCard> currentPlayedCard = new ArrayList<>();  //Chiedere per passaggio per riferimento nei metodi
    private SchoolBoard schoolBoard;

    public Player(String username) {
        this.username = username;
        this.moveCounter=0;
    }

    /**
     * @param action to be executed
     */
    public void executeAction(Action action) {
        action.execute();
    }

    /**
     * @param wizard chosen by the player.
     *               This method instantiates the deck of the corresponding wizard.
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

    public void setSchoolBoard(TowerColor towerColor) {
        this.schoolBoard = new SchoolBoard(towerColor);
    }


    // Comments for developers: the action should be called from the Game in this way.
    // player.executeAction( new PlayCard(..arguments..) )

}
