package it.polimi.ingsw.triton.launcher.server.model.game;

import it.polimi.ingsw.triton.launcher.client.cli.Cli;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.islands.IslandManager;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.player.PlayerTurnComparator;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.ChooseCloudTile;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.MoveStudentIntoDiningRoom;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.MoveStudentOntoIsland;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.PlayAssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.GiveAssistantDeckMessage;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Game extends GameMode {
    private static Game instance;
    private final Bag bag;
    private int maxNumberOfPlayers;
    private final ArrayList<Player> players;
    private final ArrayList<CloudTile> cloudTiles;
    private Player currentPlayer;
    private final Player[] professors;
    private ProfessorsManager professorsManager;
    private final IslandManager islandManager;
    private final ArrayList<AssistantCard> usedAssistantCards;
    private final ArrayList<Wizard> availableWizards;
    private GameState gameState;
    private boolean notFullCloudTiles;
    // The following array must be shown to users, so they can choose a towerColor that is not already chosen.
    private boolean[] towerColorChosen;

    /**
     * Realize the Singleton Pattern in order to instantiate the Game Class only once.
     *
     * @param maxNumberOfPlayers chosen by the first player
     * @return the instance of Game
     */
    public static Game instance(int maxNumberOfPlayers) {
        if (instance == null)
            instance = new Game(maxNumberOfPlayers);
        return instance;
    }


    private Game(int maxNumberOfPlayers) {
        this.islandManager = new IslandManager();
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.bag = new Bag();
        this.players = new ArrayList<>();
        this.cloudTiles = new ArrayList<>();
        this.towerColorChosen = new boolean[maxNumberOfPlayers];
        this.availableWizards = new ArrayList<>(Arrays.asList(Wizard.values()));
        this.gameState = GameState.LOGIN;
        this.professors = new Player[Color.numOfColors()];
        this.usedAssistantCards = new ArrayList<>();
        this.professorsManager = new ProfessorsManager(); // PHASE 6
    }


    //----------------------------------------- LOGIN PHASE METHODS ----------------------------------------------------

    /**
     * Checks the username.
     * If the username is correct, it adds it to the players arrayList.
     * If the player is the first, it sets it as currentPlayer.
     *
     * @param username the username of the player.
     * @throws IllegalArgumentException if the username is not correct (already used).
     *                                  This exception is caught by lobby() method in Server.
     */
    public void addPlayer(@NotNull String username) throws IllegalArgumentException {
        if (username.length() == 0 || username.equals(" ") || username.equals(Cli.commandForCharacterCard))
            throw new IllegalArgumentException("Illegal username");
        if (!isUsernameChosen(username)) {
            players.add(new Player(username));
            if (players.size() == 1)
                currentPlayer = players.get(0);
            notify(new LobbyMessage(getAllUsernames(players), maxNumberOfPlayers));
        } else
            throw new IllegalArgumentException("Username already chosen");
    }


    /**
     * This method set the player's school board with the chosen tower color.
     *
     * @param player     the player that has chosen the tower color.
     * @param towerColor the color of the tower.
     * @throws IllegalClientInputException when the user's chosen tower color has already been chosen.
     */
    public void chooseTowerColor(Player player, @NotNull TowerColor towerColor) throws IllegalClientInputException, ChangeTurnException {
        try {
            if (towerColorChosen[towerColor.ordinal()]) {
                throw new IllegalClientInputException(ErrorTypeID.TOWER_COLOR_ALREADY_CHOSEN);
            } else {
                player.setSchoolBoard(towerColor, maxNumberOfPlayers);
                towerColorChosen[towerColor.ordinal()] = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalClientInputException(ErrorTypeID.GENERIC_CLIENT_INPUT_ERROR);
        }
        setNextPlayer();
    }

    /**
     * Set the wizard to the player and removes it from the available wizards list;
     * Create a new request to the next player.
     *
     * @param player the player who set the wizard.
     * @param wizard the wizard selected by the player.
     * @throws IllegalClientInputException when the user's chosen wizard has already been chosen.
     */
    public void chooseWizard(Player player, Wizard wizard) throws IllegalClientInputException, ChangeTurnException {
        if (!availableWizards.contains(wizard)) {
            throw new IllegalClientInputException(ErrorTypeID.WIZARD_ALREADY_CHOSEN);
        } else {
            player.setWizard(wizard);
            availableWizards.remove(wizard);
        }
        setNextPlayer();
    }

    //------------------------------------------------------------------------------------------------------------------


    //----------------------------------------- SETUP PHASE METHODS ----------------------------------------------------

    /**
     * When last player chooses an assistant card, sort players ArrayList based on assistant cards value,
     * thus the ACTION PHASE starts.
     * Otherwise, set next player that should choose an assistant card.
     *
     * @param player the current player.
     * @param assistantCard the assistant card to play.
     * @throws IllegalClientInputException if the player can't play the given assistant card.
     */
    public void chooseAssistantCard(Player player, AssistantCard assistantCard) throws IllegalClientInputException, ChangeTurnException {
        player.executeAction(new PlayAssistantCard(assistantCard, player, usedAssistantCards));
        notify(new InfoAssistantCardPlayedMessage(currentPlayer.getUsername(), assistantCard));
        if (usedAssistantCards.size() == maxNumberOfPlayers) {
            sortPlayerPerTurn();
            setGameState(GameState.ACTION_PHASE);
        } else setNextPlayer();
    }


    /**
     * This method executes the SETUP phase of the game.
     * Then it starts the PLANNING PHASE.
     */
    public void setup() {
        setupBag(); //PART 1 OF PHASE 3
        setupIslands(); //PART 2 OF PHASE 3
        bag.fillBag(); //PHASE 4
        createCloudTiles(); //PHASE 5
        this.professorsManager = new ProfessorsManager(); //PHASE 6
        //PHASE 7, 8 & 9 are done when the player logs in for the first time
        setupEntrance(); //PHASE 10
        setupPlayers(); //PHASE 11
        for (Player player : players)
            notify(new GiveAssistantDeckMessage(player.getUsername(), player.getAssistantDeck()));
        notify(new GameInfoMessage(islandManager.getIslands(), islandManager.getMotherNature().getPosition(), getAllSchoolBoards(), cloudTiles, new String[professors.length], getAllChosenWizards()));
        notify(new ChangeTurnMessage(currentPlayer.getUsername()));
        planningPhase();
    }




    /**
     * This method places two students for each color into the bag.
     */
    public void setupBag() {
        for (int i = 0; i < Color.values().length; i++) {
            for (int j = 0; j < 2; j++)
                bag.addStudent(Color.values()[i]);
        }
    }

    /**
     * This method places two students on every island except the one with mother nature on and the one in front of.
     */
    public void setupIslands() {
        for (Island island : islandManager.getIslands()) {
            if (island.getId() != islandManager.getMotherNature().getIndexOfOppositeIsland(islandManager.getIslands()) && island.getId() != islandManager.getMotherNature().getPosition().getId()) {
                island.addStudent(bag.drawStudent());
            }
        }
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
     * This method add 7 students into the entrance when is 2-player Game or 9 students when is a 3-player Game.
     */
    public void setupEntrance() {
        int studentsToMove = maxNumberOfPlayers == 3 ? 9 : 7;
        for (Player player : players) {
            for (int i = 0; i < studentsToMove; i++) {
                player.getSchoolBoard().addStudentIntoEntrance(bag.drawStudent());
            }
        }
    }

    /**
     * This method sorts the players ArrayList randomly and sets the current player.
     */
    public void setupPlayers() {
        for (int i = 0; i < players.size(); i++) {
            int customRandom = random.nextInt(players.size() - i) + i;
            Player p = players.get(customRandom);
            players.set(customRandom, players.get(i));
            players.set(i, p);
        }
        currentPlayer = players.get(0);
    }

    //------------------------------------------------------------------------------------------------------------------


    //-------------------------------------- PLANNING PHASE METHODS ----------------------------------------------------
    // TODO continuare metodi sotto; i metodi sopra sono revisionati. Alcuni da migliorare, vedi notes (PAP)
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Adds the students to the cloud tiles and sets the last round flag if the bag is empty.
     * It sends messages to communicate the new filled cloud tiles and if the bag is empty.
     */
    public void addStudentsToCloudTiles() {
        Color student;
        int numStudents;
        if (maxNumberOfPlayers == 2)
            numStudents = 3;
        else
            numStudents = 4;
        for (CloudTile cloudTile : cloudTiles) {
            cloudTile.setAlreadyUsed(false);
            for (int i = 0; i < numStudents; i++) {
                try {
                    if (!bag.isEmpty())
                        student = bag.drawStudent();
                    else {
                        notFullCloudTiles = true;
                        break;
                    }
                } catch (NoSuchElementException e) {           //HERE we don't expect to enter
                    notFullCloudTiles = true;
                    break;
                }
                cloudTile.setStudents(student);
                if (bag.isEmpty()) {
                    notFullCloudTiles = true;
                    break;
                }
            }
            if (notFullCloudTiles)
                break;
        }
        notify(new CloudTilesInfoMessage(cloudTiles));
    }

    /**
     * This method defines the game turn based on the played assistant cards.
     */
    public void sortPlayerPerTurn() throws ChangeTurnException {
        players.sort(new PlayerTurnComparator());
        currentPlayer = players.get(0);
        notify(new ChangeTurnMessage(currentPlayer.getUsername()));
        throw new ChangeTurnException();
    }


    /**
     * Executes a part of the planning phase, filling the cloud tiles and resetting the assistant
     * cards played in last round.
     * If the Game mode is Expert, it also sets the "already played character chard boolean attribute" to false.
     */
    public void planningPhase() {
        setGameState(GameState.PLANNING_PHASE);
        for (Player player : players)
            player.resetAlreadyPlayedAnCharacterCard();
        addStudentsToCloudTiles();

        resetPlayedCardInTurn();
    }


    /**
     * Executes the action of moving a player from entrance to the dining room.
     *
     * @param student the color of the student to move.
     */
    public void executeActionMoveStudentToDiningRoom(Color student) throws LastMoveException, IllegalClientInputException {
        currentPlayer.executeAction(new MoveStudentIntoDiningRoom(student, currentPlayer));
        professorsManager.updateProfessors(currentPlayer, student, professors);
        String moveDescription = currentPlayer.getUsername() + " has moved a " + student.name().toLowerCase() + " student in his dining room.";
        notify(new InfoStudentIntoDiningRoomMessage(currentPlayer.getUsername(), currentPlayer.getSchoolBoard(), professorsWithUsernameOwner(), moveDescription));
        currentPlayer.setMoveCounter(currentPlayer.getMoveCounter() + 1);
        checkNumberMoves();   //checks if the move was the last one throwing lastMoveException
    }


    /**
     * @param student  the color of the student to move.
     * @param idIsland the id of the island where the player wants to move the student to.
     * @throws IllegalClientInputException if the island doesn't exist or if there aren't students of the color specified by the player in the entrance.
     * @throws LastMoveException           if the player has moved the students three times.
     */
    public void executeActionMoveStudentToIsland(Color student, int idIsland) throws IllegalClientInputException, LastMoveException {
        if (!islandManager.existsIsland(idIsland)) {
            throw new IllegalClientInputException();
        } else {
            currentPlayer.executeAction(new MoveStudentOntoIsland(currentPlayer.getSchoolBoard(), student, islandManager.getIslandByID(idIsland)));
            currentPlayer.setMoveCounter(currentPlayer.getMoveCounter() + 1);
            String moveDescription = currentPlayer.getUsername() + " has moved a " + student.name().toLowerCase() + " student on the island " + idIsland + ".";
            notify(new InfoStudentOntoIslandMessage(currentPlayer.getUsername(), currentPlayer.getSchoolBoard(), islandManager.getIslandByID(idIsland), professorsWithUsernameOwner(), moveDescription));
            checkNumberMoves();
        }
    }

    /**
     * Checks if the player has to move again a student.
     */
    public void checkNumberMoves() throws LastMoveException {
        int numberMoves;
        if (maxNumberOfPlayers == 2)
            numberMoves = 3;
        else
            numberMoves = 4;
        if (currentPlayer.getMoveCounter() == numberMoves) {
            currentPlayer.setMoveCounter(0);
            throw new LastMoveException();
        }
    }

    /**
     * Moves mother nature to another island.
     *
     * @param numSteps the number of steps that mother nature has to do.
     */
    public void moveMotherNature(int numSteps) throws IllegalClientInputException, EndGameException, ChangeTurnException {
        Island newPosition = islandManager.getMotherNature().move(currentPlayer.getLastPlayedAssistantCard(), numSteps, islandManager.getIslands());
        islandManager.getMotherNature().setIslandOn(newPosition);
        islandManager.mergeNearIslands(islandManager.getMotherNature().getPosition(), players, professors);
        if (notFullCloudTiles)
            nextGameTurn();
    }



    /**
     * Manages the action of the player to choose the cloud tile.
     *
     * @param cloudTile the cloud tile selected from the player.
     */
    public void chooseCloudTile(CloudTile cloudTile) throws IllegalClientInputException, ChangeTurnException, EndGameException {
        currentPlayer.executeAction(new ChooseCloudTile(cloudTile, currentPlayer.getSchoolBoard()));
        cloudTile.setAlreadyUsed(true);
        String choiceDescription = currentPlayer.getUsername() + " has chosen the cloud tile " + cloudTile.getId() + ".";
        notify(new InfoChosenCloudTileMessage(currentPlayer.getUsername(), currentPlayer.getSchoolBoard(), cloudTile, professorsWithUsernameOwner(), choiceDescription));
        nextGameTurn();
    }


    /**
     * This method calculates the winner when one player has the max number of towers onto islands.
     * If two or more players have the same number of towers on islands, is called a new method for the calculation of the winner because of professors.
     * If there is a winner, virtualViews are notified using a WinMessage.
     */
    public void calculateWinner() {
        Optional<Player> p;
        int min = Collections.min(players.stream().map(Player::getSchoolBoard).map(SchoolBoard::getNumTowers).collect(Collectors.toList()));
        int frequency = Collections.frequency(players.stream().map(Player::getSchoolBoard).map(SchoolBoard::getNumTowers).collect(Collectors.toList()), min);
        if (frequency == 1) {
            p = players.stream().filter(player -> player.getSchoolBoard().getNumTowers() == min).findFirst();
            p.ifPresent(player -> notify(new WinMessage(player.getUsername())));
        } else
            checkForTie(players.stream().filter(player -> player.getSchoolBoard().getNumTowers() == min).collect(Collectors.toList()));
    }


    /**
     * This method checks occurrences of players in list into the professors array.
     * If one of the players has more professors than the others, he's the winner, else we have a tie.
     * In case of tie we notify virtualViews using a TieMessage, which specifies the list of the peer players.
     *
     * @param list gains the list of the players that have the same number of towers, so they are potentially peer.
     */
    private void checkForTie(List<Player> list) {
        Optional<Player> player;
        int max = Collections.max(list.stream().map(p -> Collections.frequency(Arrays.stream(professors).collect(Collectors.toList()), p)).collect(Collectors.toList()));
        int frequency = Collections.frequency(list.stream().map(p -> Collections.frequency(Arrays.stream(professors).collect(Collectors.toList()), p)).collect(Collectors.toList()), max);
        if (frequency == 1) {
            player = list.stream().filter(pl -> (Collections.frequency(Arrays.stream(professors).collect(Collectors.toList()), pl) == max)).findFirst();
            player.ifPresent(value -> notify(new WinMessage(value.getUsername())));
        }else
            notify(new TieMessage(list.stream().filter(pl -> (Collections.frequency(Arrays.stream(professors).collect(Collectors.toList()), pl) == max)).collect(Collectors.toList()).stream().map(Player::getUsername).collect(Collectors.toList())));
    }


    /**
     * Changes current player at the end of every action phase.
     * At the end of the last player's action phase, it starts a new planning phase.
     */
    private void nextGameTurn() throws EndGameException, ChangeTurnException {
        professorsManager.resetProfessorStrategy();
        islandManager.getMotherNature().resetAdditionalSteps();
        islandManager.resetIslandsInfluenceStrategy();
        if (players.indexOf(currentPlayer) < maxNumberOfPlayers - 1) {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            notify(new ChangeTurnMessage(currentPlayer.getUsername()));
        } else if (!currentPlayer.getAssistantDeck().getAssistantDeck().isEmpty() && !bag.isEmpty() && players.indexOf(currentPlayer) == maxNumberOfPlayers - 1) {
            currentPlayer = players.get(0);
            notify(new ChangeTurnMessage(currentPlayer.getUsername()));
            planningPhase();
            throw new ChangeTurnException();
        } else {
            throw new EndGameException();
        }
    }

    public void useCharacterCard(Player player, int idCard) throws IllegalClientInputException, CharacterCardWithParametersException {
        // This method is implemented by ExpertGame
    }

    public void applyCharacterCardEffect(int characterCardID, CardEffect cardEffect) throws IllegalClientInputException, EndGameException {
        // This method is implemented by ExpertGame
    }

    public CharacterCard getCharacterCardByID(int id) throws IllegalClientInputException {
        // This method is implemented by ExpertGame
        return null;
    }

    public ArrayList<CharacterCard> getCharacterCards() {
        // This method is implemented by ExpertGame
        return new ArrayList<>();
    }



    /**
     * This method removes the played assistant card from the player's deck.
     */
    private void resetPlayedCardInTurn() {
        usedAssistantCards.clear();
    }

    /**
     * @return all the school boards associate to their owner.
     */
    public Map<String, SchoolBoard> getAllSchoolBoards() {
        Map<String, SchoolBoard> mapSchoolBoards = new HashMap<>();
        for (Player player : players) {
            mapSchoolBoards.put(player.getUsername(), player.getSchoolBoard());
        }
        return mapSchoolBoards;
    }

    /**
     * @return all the wizards already chosen by the players.
     */
    public Map<String, Wizard> getAllChosenWizards() {
        Map<String, Wizard> chosenWizardsPerUsername = new HashMap<>();
        for (Player player : players) {
            chosenWizardsPerUsername.put(player.getUsername(), player.getAssistantDeck().getWizard());
        }
        return chosenWizardsPerUsername;
    }



    /**
     * @return an array of String about professors with their respective owner.
     * If a professor is not on a player's school board, it has a '_' on his position of the array.
     */
    public String[] professorsWithUsernameOwner() {
        return Arrays.stream(professors).map(p -> {
            if (p == null) return "_";
            else return p.getUsername();
        }).toArray(String[]::new);
    }

    @Override
    public void removePlayer(String username) {
        players.removeIf(player -> (player.getUsername().equals(username)));
        notify(new LobbyMessage(getAllUsernames(players), maxNumberOfPlayers));
    }

    /**
     * Ends the game resetting the instance of the game.
     */
    public void endGame(boolean correctEnd) {
        setGameState(GameState.END);
        if (!correctEnd)
            notify(new DisconnectionMessage());
        resetInstance();
    }


    /**
     * Sets the instance of the game to null.
     */
    private static void resetInstance() {
        instance = null;
    }
    //------------------------------------------------------------------------------------------------------------------

    //--------------------------------------------- HELPER METHODS -----------------------------------------------------

    /**
     * This method checks, ignoring CAPSLOCK, if the username entered by the player has already been chosen
     *
     * @param username the username to check.
     * @return true if the username has already been chosen, false otherwise.
     */
    private boolean isUsernameChosen(String username) {
        return players.stream().anyMatch(player -> player.getUsername().equalsIgnoreCase(username));
    }

    /**
     * @param players the array of players in the game.
     * @return all the usernames of the players that are playing.
     */
    private List<String> getAllUsernames(@NotNull ArrayList<Player> players) {
        return players.stream().map(Player::getUsername).collect(Collectors.toList());
    }

    /**
     * @param username the username of the player.
     * @return the player with the given username.
     * @throws NoSuchElementException if there's not a player with the given username.
     */
    public Player getPlayerByUsername(String username) throws NoSuchElementException {
        for (Player p : players) {
            if (p.getUsername().equals(username))
                return p;
        }
        throw new NoSuchElementException("This player does not exist");
    }

    /**
     * This method is used to manage the turns in preparation and planning phases.
     * When the currentPlayer is not the last in turn, set the currentPlayer to the next one.
     * When the last wizard has been chosen, we call setup method and order randomly the players' arraylist.
     * Otherwise, set current player to the first player in the sorted players' arraylist.
     *
     * @throws ChangeTurnException if there's not another player that has to play in the current phase.
     */
    public void setNextPlayer() throws ChangeTurnException {
        if (players.indexOf(currentPlayer) < players.size() - 1) {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        } else if (Wizard.values().length - availableWizards.size() == maxNumberOfPlayers) {
            setup();
            availableWizards.clear();
            throw new ChangeTurnException();
        } else {
            currentPlayer = players.get(0);
            throw new ChangeTurnException();
        }
    }
    //------------------------------------------------------------------------------------------------------------------


    //--------------------------------------------- GETTER METHODS -----------------------------------------------------


    public Bag getBag() {
        return bag;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean[] getTowerColorChosen() {
        return towerColorChosen;
    }

    public Player[] getProfessors() {
        return professors;
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    public ProfessorsManager getProfessorsManager() {
        return professorsManager;
    }

    public GameState getGameState() {
        return gameState;
    }


    /**
     * @param cloudTileId the id of the cloud tile to return.
     * @return the cloud tile with the id passed by parameter.
     * @throws IllegalClientInputException if the cloud tile with that id doesn't exist.
     */
    public CloudTile getCloudTileById(int cloudTileId) throws IllegalClientInputException {
        for (CloudTile cloudTile : cloudTiles) {
            if (cloudTile.getId() == cloudTileId)
                return cloudTile;
        }
        throw new IllegalClientInputException();
    }


    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    public IslandManager getIslandManager() {
        return islandManager;
    }
    //------------------------------------------------------------------------------------------------------------------


    //--------------------------------------------- SETTER METHODS -----------------------------------------------------

    public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.towerColorChosen = new boolean[maxNumberOfPlayers];
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        if (gameState == GameState.PLANNING_PHASE || gameState == GameState.ACTION_PHASE) {
            notify(new ChangePhaseMessage(gameState));
        }
    }
    //------------------------------------------------------------------------------------------------------------------
}
