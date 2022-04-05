package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Game {

    private final ArrayList<Island> islands;
    private int numPlayers;
    private ArrayList<Player> players;
    private int generalCoinSupply;
    private ArrayList<CloudTile> cloudTiles;
    private Player currentPlayer;
    private MotherNature motherNature;
    private Player[] professors;
    private AssistantDeck assistantDecks;
    private ArrayList<CharacterCard> characterCards;
    private ProfessorsManager professorsManager;

    private final Bag bag;
    // some useful final variables
    private final int NUM_OF_STUDENTS_COLORS = Color.values().length;
    private final int MAX_NUM_OF_ISLANDS = 12;
    private final int MAX_NUM_OF_STUDENTS_ON_CLOUDTILE = 3;


    public Game() {
        this.islands = new ArrayList<Island>();
        this.bag = new Bag(); // to pass num of stud
        this.cloudTiles= new ArrayList<>();

    }


    // Preparation phase
    public void startGame(Map<Player, TowerColor> playerTowerColorMap, Map<Player, Wizard> playerWizardMap) {
        createIslands();
        setupMotherNature();
        setupBag();
        setupIslands();
        bag.fillBag();
        this.professorsManager = new ProfessorsManager();
        createCloudTiles();
        setupSchoolboard(playerTowerColorMap);
        setupWizard(playerWizardMap);
        setupEntrance();
        setupFirstPlayer();
    }

    // Planning Phase
    public void planningPhase(){
        setupCloudTiles();
    }


    //--- methods for the PIANIFICATION PHASE
    public void setupCloudTiles(){
        for(CloudTile cloudTile: cloudTiles){
            for(int i=0;i<MAX_NUM_OF_STUDENTS_ON_CLOUDTILE;i++){
                cloudTile.addStudents(bag.drawStudent());
            }
        }
    }

    //--- end of methods for the PIANIFICATION PHASE




    //--- methods for the PEPARATION PHASE
    public void createIslands() {
        for (int i = 0; i < MAX_NUM_OF_ISLANDS; i++) {
            islands.add(new Island(i));
        }
    }

    public void setupMotherNature() {
        Random random = new Random();
        int randomIndex = random.nextInt(islands.size());
        motherNature = new MotherNature(islands.get(randomIndex));
    }

    public void setupBag() {
        for (int i = 0; i < NUM_OF_STUDENTS_COLORS; i++) {
            for (int j = 0; j < 2; j++)
                bag.addStudent(Color.values()[i]);
        }
    }

    public void setupIslands() {
        for (Island island : islands) {
            if (island.getId() != motherNature.getIndexOfOppositeIsland() && island.getId() != motherNature.getPosition().getId()) {
                island.addStudent(bag.drawStudent());
            }
        }
    }

    public void createCloudTiles() {
        for (int i = 0; i < 2; i++) {
            cloudTiles.add(new CloudTile(i));
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void setupSchoolboard(Map<Player, TowerColor> playerTowerColorMap) {

        for (Player player : playerTowerColorMap.keySet()) {
            player.setSchoolBoard(playerTowerColorMap.get(player));
        }
    }

    public void setupWizard(Map<Player, Wizard> playerWizardMap) {
        for (Player player : playerWizardMap.keySet()) {
            player.setWizard(playerWizardMap.get(player));
        }
    }

    public void setupEntrance() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.getSchoolBoard().addStudentIntoEntrance(bag.drawStudent());
            }
        }
    }

    public void setupFirstPlayer() {
        Random random = new Random();
        currentPlayer = players.get(random.nextInt(players.size()));
    }

    //--- end of methods for PEPARATION PHASE

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


    // getters

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }

    public Bag getBag() {
        return bag;
    }

    public ArrayList<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    public int getMAX_NUM_OF_STUDENTS_ON_CLOUDTILE() {
        return MAX_NUM_OF_STUDENTS_ON_CLOUDTILE;
    }


}
