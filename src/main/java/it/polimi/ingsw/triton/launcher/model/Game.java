package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;

public class Game {

    public Game() {
    }

    private int numPlayers;
    private ArrayList<Player> players;
    private int generalCoinSupply;
    private ArrayList<CloudTile> cloudTiles;
    private MotherNature motherNature;
    private ArrayList<Island> islands;
    private Player[] professors;
    private Bag bag;
    private AssistantDeck assistantDecks;
    private ArrayList<CharacterCard> characterCards;

    public void startGame() {
        for(int i=0;i<12;i++){
            islands.add(new Island());
        }
    }

    public void endGame() {

    }

    public void createPlayer() {
    }

    public void removePlayer() {
    }


    public void mergeIslands(Island i1, Island i2) {
        // TODO implement here
    }

    public void sortPlayerPerTurn() {
        // TODO implement here
    }

    public void nextPlayCardTurn() {
        // TODO implement here
    }


    public void nextGameTurn() {
        // TODO implement here
    }

    public void drawCharacterCards() {
        // TODO implement here
    }

    public void withdrawCoin() {
        // TODO implement here
    }

    public void depositCoin(int x) {
        // TODO implement here
    }

    public Island nextIsland(Island currentIsland) {
        // TODO implement here
        return null;
    }


    public Island prevIsland(Island currentIsland) {
        // TODO implement here
        return null;
    }



    public void updateProfessors(Player player,Color color ) {
        // TODO implement here
    }

}
