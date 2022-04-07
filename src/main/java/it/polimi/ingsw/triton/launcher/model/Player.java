package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.model.playeractions.Action;

import java.util.ArrayList;

public class Player {
    private String username;
    private int moveCounter = 0;
    private AssistantDeck assistantDeck;
    //private AssistantCard lastPlayedAssistantCard;
    private ArrayList<AssistantCard> currentPlayedCard = new ArrayList<>();  //Chiedere per passaggio per riferimento nei metodi
    private SchoolBoard schoolBoard;

    public String getUsername() {
        return username;
    }

    public Player(String username){
        this.username = username;
    }

    public void setWizard(Wizard wizard){
        assistantDeck = new AssistantDeck(wizard);
    }

    public SchoolBoard getSchoolBoard(){
        return this.schoolBoard;
    }

    public void setSchoolBoard (TowerColor towerColor){
        this.schoolBoard = new SchoolBoard(towerColor);
    }


    /**
     * @param action to be executed
     */
    public void executeAction(Action action){
        action.execute();
    }

    // Comments for developers: the action should be called from the Game in this way.
    // player.executeAction( new PlayCard(..arguments..) )

}
