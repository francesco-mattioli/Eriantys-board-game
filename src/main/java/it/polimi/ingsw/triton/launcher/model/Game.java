package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.model.player.PlayerTurnComparator;
import it.polimi.ingsw.triton.launcher.model.professor.ProfessorsManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class Game {

    private final ArrayList<Island> islands;
    private final Bag bag;
    private final int maxNumberOfPlayers;
    // some useful final variables
    private final int NUM_OF_STUDENTS_COLORS = Color.values().length;
    private final int MAX_NUM_OF_ISLANDS = 12;
    private final int INITIAL_NUM_COINS = 20;
    private final ArrayList<Player> players;
    private final int generalCoinSupply;
    private final ArrayList<CloudTile> cloudTiles;
    private final ArrayList<CharacterCard> characterCards;
    private Player currentPlayer;
    private MotherNature motherNature;
    private Player[] professors;
    private ProfessorsManager professorsManager;
    private ArrayList<AssistantCard> usedAssistantCards;

    public Game(int maxNumberOfPlayers) {
        this.islands = new ArrayList<>();
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.bag = new Bag(maxNumberOfPlayers);
        this.players = new ArrayList<>();
        this.cloudTiles = new ArrayList<>();
        this.characterCards = new ArrayList<>();
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


    public void sortPlayerPerTurn() {
        players.sort(new PlayerTurnComparator());
    }

    public void nextPlayCardTurn() {
        if (players.indexOf(currentPlayer) < maxNumberOfPlayers - 1)
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        else sortPlayerPerTurn();
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
        setupSchoolboard(playerTowerColorMap, this.maxNumberOfPlayers);
        setupWizard(playerWizardMap);
        setupEntrance();
        setupFirstPlayer();

    }

    // Planning phase
    public void planningPhase(AssistantCard assistantCard) {
        setupCloudTiles();

    }


    //--- methods for the PIANIFICATION PHASE
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


    // methods for the PREPARATION PHASE
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
            if (island.getId() != motherNature.getIndexOfOppositeIsland(islands) && island.getId() != motherNature.getPosition().getId()) {
                island.addStudent(bag.drawStudent());
            }
        }
    }

    public void createCloudTiles() {
        for (int i = 0; i < players.size(); i++) {
            cloudTiles.add(new CloudTile(i));
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<CloudTile> getCloudTiles() {
        return cloudTiles;
    }


    public void setupSchoolboard(Map<Player, TowerColor> playerTowerColorMap, int numPlayers) {
        for (Player player : playerTowerColorMap.keySet()) {
            player.setSchoolBoard(playerTowerColorMap.get(player), numPlayers);
        }
    }

    public void setupWizard(Map<Player, Wizard> playerWizardMap) {
        for (Player player : playerWizardMap.keySet()) {
            player.setWizard(playerWizardMap.get(player));
        }
    }

    public void setupEntrance() {
        int studentsToMove = 7;
        if(this.maxNumberOfPlayers == 3)
            studentsToMove = 9;
        for (Player player : players) {
            for (int i = 0; i < studentsToMove; i++) {
                player.getSchoolBoard().addStudentIntoEntrance(bag.drawStudent());
            }
        }
    }

    public void setupFirstPlayer() {
        Random random = new Random();
        currentPlayer = players.get(random.nextInt(players.size()));
    }

    // end of methods for the PREPARATION PHASE

    public void endGame() {
        //close connection
    }


    public void removePlayer(Player player) {
        players.remove(player);
        endGame();
    }


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
        if (islands.indexOf(currentIsland) == 0) {
            return islands.get(islands.size() - 1);
        } else
            return islands.get(islands.indexOf(currentIsland) - 1);
    }

    public void resetPlayedCardInTurn() {
        for (AssistantCard assistantCard : usedAssistantCards)
            usedAssistantCards.remove(assistantCard);
    }

}
