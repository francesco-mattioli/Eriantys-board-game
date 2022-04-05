package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Game {

    private final ArrayList<Island> islands;

    private final Bag bag;
    private ArrayList<Player> players;
    private int generalCoinSupply;
    private ArrayList<CloudTile> cloudTiles;
    private Player currentPlayer;

    private MotherNature motherNature;
    private Player[] professors;
    private ProfessorsManager professorsManager;


    // some useful final variables
    private final int NUM_OF_STUDENTS_COLORS=Color.values().length;
    private final int MAX_NUM_OF_ISLANDS =12;
    private final int INITIAL_NUM_COINS = 20;

    public Game() {
        this.islands = new ArrayList<Island>();
        this.bag = Bag.instance(players.size());
        this.players = new ArrayList<Player>();
        this.cloudTiles = new ArrayList<CloudTile>();
        this.generalCoinSupply = INITIAL_NUM_COINS;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }

    public Bag getBag() {
        return bag;
    }

    // Preparation phase
    public void startGame(Map<Player,TowerColor> playerTowerColorMap, Map<Player,Wizard> playerWizardMap) {
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

    // Planning phase
    public void planningPhase(){
        setupCloudTiles();
    }


    //--- methods for the PIANIFICATION PHASE
    public void setupCloudTiles(){
        int numOfStudentsOnCloudTile=3;
        if(players.size()>2){
           numOfStudentsOnCloudTile=4;
        }
        // if there are 2 players the numOfStudentsOnCloudTile should be 3
        for(CloudTile cloudTile: cloudTiles){
            for(int i=0;i<numOfStudentsOnCloudTile;i++){
                cloudTile.addStudent(bag.drawStudent());
            }
        }
    }

    //--- end of methods for the PLANNING PHASE





    public void createIslands() {
        for (int i = 0; i < MAX_NUM_OF_ISLANDS ; i++) {
            islands.add(new Island(i));
        }
    }

    public void setupMotherNature() {
        Random random = new Random();
        int randomIndex = random.nextInt(islands.size());
        motherNature = MotherNature.instance(islands.get(randomIndex));
    }

    public void setupBag() {
        for (int i = 0; i < NUM_OF_STUDENTS_COLORS ; i++) {
            for (int j = 0; j < 2; j++)
                bag.addStudent(Color.values()[i]);
        }
    }

    public void setupIslands(){
       for(Island island: islands){
           if(island.getId()!= motherNature.getIndexOfOppositeIsland() && island.getId()!= motherNature.getPosition().getId()){
               island.addStudent(bag.drawStudent());
           }
       }
    }

    public void createCloudTiles(){
        for(int i=0;i<players.size();i++){
            cloudTiles.add(new CloudTile(i));
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    //control if name is unique
    public void addPlayer(String username){
        Player player = new Player(username);
        players.add(player);
    }

    public void setupSchoolboard(Map<Player,TowerColor> playerTowerColorMap){
        for (Player player: playerTowerColorMap.keySet()) {
            player.setSchoolBoard(playerTowerColorMap.get(player));
        }
    }

    public void setupWizard(Map<Player,Wizard> playerWizardMap){
        for (Player player: playerWizardMap.keySet()) {
            player.setWizard(playerWizardMap.get(player));
        }
    }

    public void setupEntrance(){
        for (Player player: players) {
            for (int i=0; i<7; i++){
                player.getSchoolBoard().addStudentIntoEntrance(bag.drawStudent());
            }
        }
    }

    public void setupFirstPlayer(){
        Random random = new Random();
        currentPlayer = players.get(random.nextInt(players.size()));
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

}
