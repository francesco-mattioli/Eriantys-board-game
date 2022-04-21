package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.model.player.PlayerTurnComparator;
import it.polimi.ingsw.triton.launcher.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.network.Observable;
import it.polimi.ingsw.triton.launcher.network.Observer;
import it.polimi.ingsw.triton.launcher.network.message.AssistantCardRequest;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.view.View;
import it.polimi.ingsw.triton.launcher.view.VirtualView;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Observable {

    public static final String NAME_SERVER = "eriantys";
    private final ArrayList<Island> islands;
    private final Bag bag;
    private final int maxNumberOfPlayers;
    // some useful final variables
    private final int NUM_OF_STUDENTS_COLORS = Color.values().length;
    private final int MAX_NUM_OF_ISLANDS = 12;
    private final int INITIAL_NUM_COINS = 20;
    private final ArrayList<Player> players;
    private int generalCoinSupply;
    private final ArrayList<CloudTile> cloudTiles;
    private final ArrayList<CharacterCard> characterCards;
    private Player currentPlayer;


    private MotherNature motherNature;
    private Player[] professors;
    private ProfessorsManager professorsManager;
    private ArrayList<AssistantCard> usedAssistantCards;
    // This array must be shown to users, so they can choose a towerColor that is not already chosen.
    private final boolean[] towerColorChosen;


    public Game(int maxNumberOfPlayers) {
        this.islands = new ArrayList<>();
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.bag = new Bag(maxNumberOfPlayers);
        this.players = new ArrayList<>();
        this.cloudTiles = new ArrayList<>();
        this.characterCards = new ArrayList<>();
        this.generalCoinSupply = INITIAL_NUM_COINS;
        this.towerColorChosen = new boolean[TowerColor.values().length];

    }


    /**
     * This method checks if the username entered by the player has already been chosen
     * @param username
     * @return true if the username has already been chosen else false
     */
    public boolean isUsernameChosen(String username) {
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public void addPlayer(String username) {
        players.add(new Player(username));
    }

    /**
     * This method set the player's schoolboard with the chosen tower color
     * @param player
     * @param towerColor
     */
    public void chooseTowerColor(Player player, TowerColor towerColor) {
        player.setSchoolBoard(towerColor, maxNumberOfPlayers);
        towerColorChosen[towerColor.ordinal()] = true;
    }

    /**
     * This method set the player's deck with the chosen wizard
     * @param player
     * @param wizard
     */
    public void chooseWizard(Player player, Wizard wizard) {
        player.setWizard(wizard);
    }

    /**
     * This method executes the SETUP phase of the game
     */
    public void setup() {
        createIslands(); //PHASE 1
        setupMotherNature(); //PHASE 2
        setupBag(); //PART 1 OF PHASE 3
        setupIslands(); //PART 2 OF PHASE 3
        bag.fillBag(); //PHASE 4
        createCloudTiles(); //PHASE 5
        this.professorsManager = new ProfessorsManager(); //PHASE 6
        //PHASE 7, 8 & 9 are done when the player logs in for the first time
        setupEntrance(); //PHASE 10
        setupPlayers(); //PHASE 11
    }


     // The following methods execute the PLANNING phase of the game

    /**
     * PART 1 of PLANNING
     */
    public void addStudentsToCloudTiles(){
        for(CloudTile cloudTile: cloudTiles){
            if(maxNumberOfPlayers==2)
                cloudTile.setStudents(bag.drawStudent(),bag.drawStudent(),bag.drawStudent());
            if(maxNumberOfPlayers==3)
                cloudTile.setStudents(bag.drawStudent(),bag.drawStudent(),bag.drawStudent(),bag.drawStudent());
        }
    }





    /**
     * This method defines the game turn based on the played assistant cards
     */
    public void sortPlayerPerTurn() {
        players.sort(new PlayerTurnComparator());
    }

    /**
     * This method allows one player per time to play his assistant card.
     * When all the players have played their card the game defines the game turn.
     */
    public void nextPlayCardTurn() {
        if (players.indexOf(currentPlayer) < maxNumberOfPlayers - 1)
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        else sortPlayerPerTurn();
    }


    // Planning phase
    public void planningPhase() {
        setupCloudTiles();


    }


    //--- methods for the PIANIFICATION PHASE

    /**
     * This method add three students on the cloud tiles when there are two players.
     * This method add four students on the cloud tiles when there are three players.
     */
    public void setupCloudTiles() {
        int numOfStudentsOnCloudTile = 3;
        if (players.size() > 2) {
            numOfStudentsOnCloudTile = 4;
        }
        // if there are 2 players the numOfStudentsOnCloudTile should be 3
        for (CloudTile cloudTile : cloudTiles) {
            for (int i = 0; i < numOfStudentsOnCloudTile; i++) {
                cloudTile.addStudent(bag.drawStudent());
            }
        }
    }

    //--- end of methods for the PLANNING PHASE


    // methods for the SETUP PHASE
    public void createIslands() {
        for (int i = 0; i < MAX_NUM_OF_ISLANDS; i++) {
            islands.add(new Island(i));
        }
    }

    /**
     * This method places mother nature on a random island.
     */
    public void setupMotherNature() {
        Random random = new Random();
        int randomIndex = random.nextInt(islands.size());
        motherNature = new MotherNature(islands.get(randomIndex));
    }


    /**
     * This method places two students for each color into the bag.
     */
    public void setupBag() {
        for (int i = 0; i < NUM_OF_STUDENTS_COLORS; i++) {
            for (int j = 0; j < 2; j++)
                bag.addStudent(Color.values()[i]);
        }
    }

    /**
     * This method places two students on every island except the one with mother nature and the one in front of it.
     */
    public void setupIslands() {
        for (Island island : islands) {
            if (island.getId() != motherNature.getIndexOfOppositeIsland(islands) && island.getId() != motherNature.getPosition().getId()) {
                island.addStudent(bag.drawStudent());
            }
        }
    }

    /**
     * This method sorts the players ArrayList random, and sets correctly the current player
     */
    public void setupPlayers(){
        Random rnd = new Random();
        Player p;
        for(int i = 0; i<players.size(); i++){
            int random = rnd.nextInt(players.size()-i)+i;
            p = players.get(random);
            players.set(random, players.get(i));
            players.set(i,p);
        }
        currentPlayer = players.get(0);
    }

    /**
     * This method creates the cloud tiles, one per player.
     */
    public void createCloudTiles() {
        for (int i = 0; i < maxNumberOfPlayers; i++) {
            cloudTiles.add(new CloudTile(i));
        }
    }

    /**
     * This method add seven students into the entrance when there are two players.
     * This method add nine students into the entrance when there are three players.
     */
    public void setupEntrance() {
        int studentsToMove = 7;
        if (this.maxNumberOfPlayers == 3)
            studentsToMove = 9;
        for (Player player : players) {
            for (int i = 0; i < studentsToMove; i++) {
                player.getSchoolBoard().addStudentIntoEntrance(bag.drawStudent());
            }
        }
    }


    // end of methods for the PREPARATION PHASE

    public void endGame() {
        //close connection
    }

    /**
     * This method remove a player and then end the game.
     * @param player to remove
     */
    public void removePlayer(Player player) {
        players.remove(player);
        endGame();
    }

    /**
     * This method merge two or more adjacent islands with the same dominator.
     * @param motherNaturePosition
     */
    public void mergeNearIslands(Island motherNaturePosition) {
        motherNaturePosition.updateInfluence(players, professors);
        if (motherNaturePosition.getDominator() != null) {
            if (motherNaturePosition.getDominator() == prevIsland(motherNaturePosition).getDominator()) {
                motherNaturePosition.merge(prevIsland(motherNaturePosition));
            }
            if (motherNaturePosition.getDominator() == nextIsland(motherNaturePosition).getDominator()) {
                motherNaturePosition.merge(nextIsland(motherNaturePosition));
            }
        }

    }

    public void nextGameTurn() {
        // TODO implement here
    }

    public void drawCharacterCards() {
        // TODO implement here
    }

    /**
     * Decreases by 1 the general coin supply.
     * @throws RuntimeException if there aren't coins in the supply.
     */
    public void withdrawCoin() {
        if(generalCoinSupply > 0)
            generalCoinSupply--;
        else
            throw new RuntimeException("There are no coins in the supply");
    }

    /**
     * Increases the general coin supply.
     * @param coinsToDeposit the number of coins to deposit.
     * @throws IllegalArgumentException if the number of coins to deposit is less than 0.
     */
    public void depositCoin(int coinsToDeposit) {
        if(coinsToDeposit >= 0)
            generalCoinSupply += coinsToDeposit;
        else{
            throw new IllegalArgumentException("Number of coins to deposit must be greater or equal than 0");
        }
    }

    /**
     *
     * @param currentIsland
     * @return next island on the left
     */
    public Island nextIsland(Island currentIsland) {
        if (islands.indexOf(currentIsland) == islands.size() - 1) {
            return islands.get(0);
        } else
            return islands.get(1 + islands.indexOf(currentIsland));
    }

    /**
     *
     * @param currentIsland
     * @return previous island on the right.
     */
    public Island prevIsland(Island currentIsland) {
        if (islands.indexOf(currentIsland) == 0) {
            return islands.get(islands.size() - 1);
        } else
            return islands.get(islands.indexOf(currentIsland) - 1);
    }

    /**
     * This method removes the played assistant card from the player's deck.
     */
    public void resetPlayedCardInTurn() {
        for (AssistantCard assistantCard : usedAssistantCards)
            usedAssistantCards.remove(assistantCard);
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getGeneralCoinSupply() {
        return generalCoinSupply;
    }
}
