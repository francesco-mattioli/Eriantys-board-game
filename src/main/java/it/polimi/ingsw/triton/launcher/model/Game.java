package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private final ArrayList<Island> islands;
    private final Bag bag;
    private int numPlayers;
    private ArrayList<Player> players;
    private int generalCoinSupply;
    private ArrayList<CloudTile> cloudTiles;


    private MotherNature motherNature;
    private Player[] professors;
    private AssistantDeck assistantDecks;
    private ArrayList<CharacterCard> characterCards;

    // some useful final variables
    private final int NUM_OF_STUDENTS_COLORS=Color.values().length;
    private final int MAX_NUM_OF_ISLANDS=12;

    public Game() {
        this.islands = new ArrayList<Island>();
        this.bag = new Bag();
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }

    // Preparation phase
    public void startGame() {
        createIslands();
        setupMotherNature();
        setupBag();
        setupIslands();
    }

    public void createIslands() {
        for (int i = 0; i < MAX_NUM_OF_ISLANDS ; i++) {
            islands.add(new Island(i));
        }
    }

    public void setupMotherNature() {
        Random random = new Random();
        int randomIndex = random.nextInt(islands.size());
        motherNature = new MotherNature(islands.get(randomIndex));
    }

    public void setupBag() {
        for (int i = 0; i < NUM_OF_STUDENTS_COLORS ; i++) {
            for (int j = 0; j < 2; j++)
                bag.addStudent(Color.values()[i]);
        }
    }

    public Bag getBag() {
        return bag;
    }

    public void setupIslands(){
       for(Island island: islands){
           if(island.getId()!= motherNature.getIndexOfOppositeIsland() && island.getId()!= motherNature.getPosition().getId()){
               island.addStudent(bag.drawStudent());
           }
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
        if (islands.indexOf(currentIsland) == islands.size() - 1) {
            return islands.get(0);
        } else
            return islands.get(1 + islands.indexOf(currentIsland));
    }


    public Island prevIsland(Island currentIsland) {
        // TODO implement here
        return null;
    }


    public void updateProfessors(Player player, Color color) {
        // TODO implement here
    }

}
