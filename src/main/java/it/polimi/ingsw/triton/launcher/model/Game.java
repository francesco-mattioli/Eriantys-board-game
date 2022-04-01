package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    public Game() {
        this.islands= new ArrayList<Island>();
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
        //FASE 1
        setupIslands();
        setupMotherNature();


    }

    public void setupIslands(){
        for(int i=0;i<12;i++){
            islands.add(new Island(i));
        }
    }

    public void setupMotherNature(){
        Random random = new Random();
        int randomIndex = random.nextInt(islands.size());
        System.out.println(randomIndex);
        MotherNature motherNature= new MotherNature(islands.get(randomIndex));
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
