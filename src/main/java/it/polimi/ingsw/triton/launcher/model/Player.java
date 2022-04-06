package it.polimi.ingsw.triton.launcher.model;

public class Player {
    private String username;
    private int moveCounter = 0;
    private AssistantDeck assistantDeck;
    private AssistantCard lastPlayedAssistantCard;
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

    public void playCard(AssistantCard assistantCard){
        this.lastPlayedAssistantCard=assistantCard;
    }

}
