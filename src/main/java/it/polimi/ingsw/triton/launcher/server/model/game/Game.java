package it.polimi.ingsw.triton.launcher.server.model.game;

import it.polimi.ingsw.triton.launcher.server.model.*;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyDefault;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.player.PlayerTurnComparator;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.*;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorStrategyDefault;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.GiveAssistantDeckMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import java.util.*;
import java.util.stream.Collectors;

public class Game extends GameMode{
    protected final ArrayList<Island> islands;
    protected final Bag bag;
    private final int maxNumberOfPlayers;
    protected final ArrayList<Player> players;
    protected final ArrayList<CloudTile> cloudTiles;
    protected ArrayList<CloudTile> availableCloudTiles;
    protected Player currentPlayer;
    protected MotherNature motherNature;
    protected final Player[] professors;
    protected ProfessorsManager professorsManager;
    protected final ArrayList<AssistantCard> usedAssistantCards;
    protected final ArrayList<Wizard> availableWizards;
    protected GameState gameState;
    boolean notFullCloudTiles = false;
    // The following array must be shown to users, so they can choose a towerColor that is not already chosen.
    protected final boolean[] towerColorChosen;

    public Game(int maxNumberOfPlayers) {
        this.islands = new ArrayList<>();
        createIslands();
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.bag = new Bag(maxNumberOfPlayers);
        this.players = new ArrayList<>();
        this.cloudTiles = new ArrayList<>();
        this.towerColorChosen = new boolean[maxNumberOfPlayers];
        this.availableWizards = new ArrayList<>(Arrays.asList(Wizard.values()));
        this.gameState = GameState.LOGIN;
        this.professors = new Player[Color.numOfColors()];
        this.usedAssistantCards = new ArrayList<>();
        this.professorsManager = new ProfessorsManager(); // PHASE 6
    }

    /**
     * This method checks, ignoring CAPSLOCK, if the username entered by the player has already been chosen
     * @param username the username to check.
     * @return true if the username has already been chosen, false otherwise.
     */
    private boolean isUsernameChosen(String username) {
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }

    /**
     * @param players the array of players in the game.
     * @return all the usernames of the players that are playing.
     */
    private ArrayList<String> getAllUsernames(ArrayList<Player> players){
        ArrayList<String> usernames = new ArrayList<>();
        for(Player player: players)
            usernames.add(player.getUsername());
        return usernames;
    }

    /**
     * Checks the username.
     * If the username is correct, it adds it to the players array.
     * @param username the username of the player.
     * @throws IllegalArgumentException if the username is not correct (already used). This exception is caught by lobby() method in Server.
     */
    public void addPlayer(String username) throws IllegalArgumentException {
        if(!isUsernameChosen(username)){
            players.add(new Player(username));
            // Set currentPlayer to the first one who logs in.
            if(players.size() == 1)
                currentPlayer = players.get(0);
            notify(new LobbyMessage(getAllUsernames(players), maxNumberOfPlayers));
        }
        else
            throw new IllegalArgumentException("Username already chosen");
    }


    /**
     * This method set the player's school board with the chosen tower color.
     * @param username the username of the player that has chosen the tower color.
     * @param towerColor the color of the tower.
     * @throws IllegalClientInputException when the user's chosen tower color has already been chosen.
     */
    public void chooseTowerColor(String username, TowerColor towerColor) throws IllegalClientInputException, ChangeTurnException {
        if(towerColorChosen[towerColor.ordinal()]){
            throw new IllegalClientInputException(ErrorTypeID.TOWER_COLOR_ALREADY_CHOSEN);
        }
        else{
            getPlayerByUsername(username).setSchoolBoard(towerColor, maxNumberOfPlayers);
            towerColorChosen[towerColor.ordinal()] = true;
        }
        setNextPlayer(currentPlayer);
    }

    /**
     * Set the wizard to the player and create a new request to the next player if present.
     * @param username the player's username of who sets the wizard.
     * @param wizard the wizard selected by the player.
     * @throws IllegalClientInputException when the user's chosen wizard has already been chosen.
     */
    public void chooseWizard(String username, Wizard wizard) throws IllegalClientInputException, ChangeTurnException {
        if(!availableWizards.contains(wizard)){
            throw new IllegalClientInputException(ErrorTypeID.WIZARD_ALREADY_CHOSEN);
        }
        else{
            getPlayerByUsername(username).setWizard(wizard);
            availableWizards.remove(wizard);
        }
        setNextPlayer(currentPlayer);
    }

    /**
     * When last player has chosen an assistant card, sort players ArrayList based on assistant cards value.
     * Otherwise, set next player that should choose an assistant card.
     * @param username the username of the current player.
     * @param assistantCard the assistant card to play.
     * @throws IllegalClientInputException if the player can't play the assistant card.
     *
     */
    public void chooseAssistantCard(String username, AssistantCard assistantCard) throws IllegalClientInputException, ChangeTurnException {
        getPlayerByUsername(username).executeAction(new PlayAssistantCard(assistantCard, getPlayerByUsername(username), usedAssistantCards));
        notify(new InfoAssistantCardPlayedMessage(currentPlayer.getUsername(),assistantCard));
        if(usedAssistantCards.size() == maxNumberOfPlayers) {
            sortPlayerPerTurn();
            setGameState(GameState.ACTION_PHASE);
        }
        else setNextPlayer(currentPlayer);
    }


    /**
     * This method is used to manage the turns in preparation and planning phases.
     * When last wizard has been chosen, we call setup method and order randomly the player arraylist
     * @param current the current player.
     * @throws ChangeTurnException if there's not another player that has to play in the current phase.
     */
    public void setNextPlayer(Player current) throws ChangeTurnException{
        int indexOfCurrentPlayer = players.indexOf(current);
        if(indexOfCurrentPlayer < players.size()-1){
            currentPlayer = players.get(indexOfCurrentPlayer+1);
        }
        else if(Wizard.values().length - availableWizards.size() == maxNumberOfPlayers) {
            setup();
            availableWizards.clear();
            gameState = GameState.PLANNING_PHASE;
            throw new ChangeTurnException();
        }
        else{
            currentPlayer = players.get(0);
            throw new ChangeTurnException();
        }
    }

    /**
     * @param username the username of the player.
     * @return the player with the passed username.
     * @throws NoSuchElementException if there's not a player with the passed username.
     */
    public Player getPlayerByUsername(String username) throws NoSuchElementException {
        for(Player p : players){
            if(p.getUsername().equals(username))
                return p;
        }
        throw new NoSuchElementException("This player does not exist");
    }

    /**
     * This method executes the SETUP phase of the game.
     */
    public void setup() {
        setupMotherNature(); //PHASE 2
        setupBag(); //PART 1 OF PHASE 3
        setupIslands(); //PART 2 OF PHASE 3
        bag.fillBag(); //PHASE 4
        createCloudTiles(); //PHASE 5
        this.professorsManager = new ProfessorsManager(); //PHASE 6
        //PHASE 7, 8 & 9 are done when the player logs in for the first time
        setupEntrance(); //PHASE 10
        setupPlayers(); //PHASE 11
        for(Player player: players)
            notify(new GiveAssistantDeckMessage(player.getUsername(), player.getAssistantDeck()));
        notify(new GameInfoMessage(islands, motherNature.getPosition(), getAllSchoolBoards(), cloudTiles, new String[professors.length]));
        notify(new ChangeTurnMessage(currentPlayer.getUsername()));
        planningPhase();
    }

    /**
     * Adds the students to the cloud tiles and sets the last round flag if the bag is empty.
     * It sends messages to communicate the new filled cloud tiles and if the bag is empty.
     */
    public void addStudentsToCloudTiles(){
        Color student;
        int numStudents;
        if(maxNumberOfPlayers == 2)
            numStudents = 3;
        else
            numStudents = 4;
        for(CloudTile cloudTile: cloudTiles){
            cloudTile.setAlreadyUsed(false);
            for(int i = 0; i < numStudents; i++){
                try{
                    if(!bag.isEmpty())
                        student = bag.drawStudent();
                    else{
                        notFullCloudTiles = true;
                        break;
                    }
                }catch(NoSuchElementException e){           //HERE we don't expect to enter
                    notFullCloudTiles = true;
                    break;
                }
                cloudTile.setStudents(student);
                if(bag.isEmpty()){
                    notFullCloudTiles = true;
                    break;
                }
            }
            if(notFullCloudTiles)
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


    public void planningPhase() {
        setGameState(GameState.PLANNING_PHASE);
        addStudentsToCloudTiles();
        resetPlayedCardInTurn();
    }


    /**
     * Executes the action of moving a player from entrance to the dining room.
     * @param student the color of the student to move.
     */
    public void executeActionMoveStudentToDiningRoom(Color student) throws LastMoveException, IllegalClientInputException {
        currentPlayer.executeAction(new MoveStudentIntoDiningRoom(student, currentPlayer));
        professorsManager.updateProfessors(currentPlayer, student, professors);
        String moveDescription = currentPlayer.getUsername() + " has moved a " + student.name().toLowerCase() + " student in his dining room";
        notify(new InfoStudentIntoDiningRoomMessage(currentPlayer.getUsername(), currentPlayer.getSchoolBoard(),professorsWithUsernameOwner(), moveDescription));
        currentPlayer.setMoveCounter(currentPlayer.getMoveCounter() + 1);
        checkNumberMoves();   //checks if the move was the last one throwing lastMoveException
    }



    /**
     * @param student the color of the student to move.
     * @param idIsland the id of the island where the player wants to move the student to.
     * @throws IllegalClientInputException if the island doesn't exist or if there aren't students of the color specified by the player in the entrance.
     * @throws LastMoveException if the player has moved the students three times.
     */
    public void executeActionMoveStudentToIsland(Color student, int idIsland) throws IllegalClientInputException, LastMoveException {
        if(!existsIsland(idIsland)){
            throw new IllegalClientInputException();
        }else{
            currentPlayer.executeAction(new MoveStudentOntoIsland(currentPlayer.getSchoolBoard(), student, getIslandByID(idIsland)));
            currentPlayer.setMoveCounter(currentPlayer.getMoveCounter() + 1);
            String moveDescription = currentPlayer.getUsername() + " has moved a " + student.name().toLowerCase() + " student on the island " + idIsland;
            notify(new InfoStudentOntoIslandMessage(currentPlayer.getUsername(), currentPlayer.getSchoolBoard(), getIslandByID(idIsland), professorsWithUsernameOwner(), moveDescription));
            checkNumberMoves();
        }
    }

    /**
     * Checks if the player has to move again a student.
     */
    public void checkNumberMoves() throws LastMoveException {
        int numberMoves;
        if(maxNumberOfPlayers == 2)
            numberMoves = 3;
        else
            numberMoves = 4;
        if(currentPlayer.getMoveCounter() == numberMoves) {
            currentPlayer.setMoveCounter(0);
            throw new LastMoveException();
        }
    }

    /**
     * Moves mother nature to another island.
     * @param numSteps the number of steps that mother nature has to do.
     */
    public void moveMotherNature(int numSteps) throws IllegalClientInputException, EndGameException, ChangeTurnException {
        Island newPosition = motherNature.move(currentPlayer.getLastPlayedAssistantCard(), numSteps, islands);
        motherNature.setIslandOn(newPosition);
        notify(new MotherNaturePositionMessage(newPosition));
        mergeNearIslands(motherNature.getPosition());
        if(notFullCloudTiles)
            nextGameTurn();
    }


    // Methods for the SETUP PHASE
    private void createIslands() {
        for (int i = 0; i < 12; i++) {
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
        for (int i = 0; i < Color.values().length; i++) {
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
     * This method sorts the players ArrayList random, and sets correctly the current player.
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


    /**
     * This method merge two or more adjacent islands with the same dominator.
     * @param motherNaturePosition the island where mother nature is located.
     * @throws EndGameException when there are only three groups of islands.
     */
    public void mergeNearIslands(Island motherNaturePosition) throws EndGameException {
        motherNaturePosition.updateInfluence(players, professors);
        if (motherNaturePosition.getDominator() != null) {
            if (motherNaturePosition.getDominator() == prevIsland(motherNaturePosition).getDominator()) {
                motherNaturePosition.merge(prevIsland(motherNaturePosition));
                notify(new MergeIslandsMessage(motherNaturePosition, prevIsland(motherNaturePosition)));
                islands.remove(prevIsland(motherNaturePosition));
                checkNumberIslands();
            }
            if (motherNaturePosition.getDominator() == nextIsland(motherNaturePosition).getDominator()) {
                motherNaturePosition.merge(nextIsland(motherNaturePosition));
                notify(new MergeIslandsMessage(motherNaturePosition, nextIsland(motherNaturePosition)));
                islands.remove(nextIsland(motherNaturePosition));
                checkNumberIslands();
            }
        }
        availableCloudTiles = new ArrayList<>();
        for(CloudTile cloudTile: cloudTiles){
            if(!cloudTile.isAlreadyUsed())
                availableCloudTiles.add(cloudTile);
        }
    }

    /**
     * Manages the action of the player to choose the cloud tile.
     * @param cloudTile the cloud tile selected from the player.
     */
    public void chooseCloudTile(CloudTile cloudTile) throws IllegalClientInputException, ChangeTurnException, EndGameException {
        currentPlayer.executeAction(new ChooseCloudTile(cloudTile, currentPlayer.getSchoolBoard()));
        cloudTile.setAlreadyUsed(true);
        String choiceDescription = currentPlayer.getUsername() + " has chosen the cloud tile " + cloudTile.getId();
        notify(new InfoChosenCloudTileMessage(currentPlayer.getUsername(), currentPlayer.getSchoolBoard(), cloudTile, professorsWithUsernameOwner(), choiceDescription));
        nextGameTurn();
    }

    /**
     * Checks if the number of remaining groups of islands is three.
     * @throws RuntimeException if the number of groups islands is three because the game must finish.
     */
    private void checkNumberIslands() throws EndGameException {
        if(islands.size() == 3)   //TO END
            throw new EndGameException();
    }

    /**
     * This method calculates the winner when one player has the max number of towers onto islands.
     * If two or more players have the same number of towers on islands, is called a new method for the calculation of the winner because of professors.
     * If there is a winner, virtualViews are notified using a WinMessage.
     */
    public void calculateWinner(){
        setGameState(GameState.END);
        Optional<Player> p;
        int min = Collections.min(players.stream().map(Player::getSchoolBoard).map(SchoolBoard::getNumTowers).collect(Collectors.toList()));
        int frequency = Collections.frequency(players.stream().map(Player::getSchoolBoard).map(SchoolBoard::getNumTowers).collect(Collectors.toList()), min);
        if(frequency == 1) {
            p = players.stream().filter(player -> player.getSchoolBoard().getNumTowers() == min).findFirst();
            notify(new WinMessage(p.get().getUsername()));
        }
        else checkForTie(players.stream().filter(player -> player.getSchoolBoard().getNumTowers() == min).collect(Collectors.toList()));
    }


    /**
     * This method checks occurrences of players in list into the professors array.
     * If one of the players has more professors than the others, he's the winner, else we have a tie.
     * In case of tie we notify virtualViews using a TieMessage, which specifies the list of the peer players.
     * @param list gains the list of the players that have the same number of towers, so they are potentially peer.
     */
    private void checkForTie(List<Player> list){
        Optional<Player> player;
        int max = Collections.max(list.stream().map(p -> Collections.frequency(Arrays.stream(professors).collect(Collectors.toList()), p)).collect(Collectors.toList()));
        int frequency = Collections.frequency(list.stream().map(p -> Collections.frequency(Arrays.stream(professors).collect(Collectors.toList()), p)).collect(Collectors.toList()), max);
        if (frequency == 1) {
            player = list.stream().filter(pl -> (Collections.frequency(Arrays.stream(professors).collect(Collectors.toList()), pl) == max)).findFirst();
            notify(new WinMessage(player.get().getUsername()));
        }
        notify(new TieMessage(list.stream().filter(pl -> (Collections.frequency(Arrays.stream(professors).collect(Collectors.toList()), pl) == max)).collect(Collectors.toList()).stream().map(Player::getUsername).collect(Collectors.toList())));

    }


    /**
     * Changes current player at the end of every action phase.
     * At the end of the last player's action phase, it starts a new planning phase.
     */
    public void nextGameTurn() throws EndGameException, ChangeTurnException {
        professorsManager.setProfessorStrategy(new ProfessorStrategyDefault());
        motherNature.resetAdditionalSteps();
        for(Island island: islands)
            island.setInfluenceStrategy(new InfluenceStrategyDefault());
        if (players.indexOf(currentPlayer) < maxNumberOfPlayers - 1){
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            notify(new ChangeTurnMessage(currentPlayer.getUsername()));
        }
        else if(!currentPlayer.getAssistantDeck().getAssistantDeck().isEmpty() && !bag.isEmpty() && players.indexOf(currentPlayer) == maxNumberOfPlayers - 1){
            currentPlayer = players.get(0);
            notify(new ChangeTurnMessage(currentPlayer.getUsername()));
            planningPhase();
            throw new ChangeTurnException();
        }else{
            calculateWinner();
            throw new EndGameException();
        }
    }

    public void useCharacterCard(String username,int idCard) throws IllegalClientInputException, CharacterCardWithParametersException {
        // This method is implemented by ExpertGame
    }

    public void applyCharacterCardEffect(int characterCardID, CardEffect cardEffect) throws IllegalClientInputException, EndGameException {
        // This method is implemented by ExpertGame
    }

    public CharacterCard getCharacterCardByID(int id) throws IllegalClientInputException{
        // This method is implemented by ExpertGame
        return null;
    }

    public ArrayList<CharacterCard> getCharacterCards() {
        // This method is implemented by ExpertGame
        return new ArrayList<>();
    }


        /**
         *
         * @param currentIsland the current island.
         * @return next island on the left.
         */
    private Island nextIsland(Island currentIsland) {
        if (islands.indexOf(currentIsland) == islands.size() - 1) {
            return islands.get(0);
        } else
            return islands.get(1 + islands.indexOf(currentIsland));
    }

    /**
     *
     * @param currentIsland the current island.
     * @return previous island on the right.
     */
    private Island prevIsland(Island currentIsland) {
        if (islands.indexOf(currentIsland) == 0) {
            return islands.get(islands.size() - 1);
        } else
            return islands.get(islands.indexOf(currentIsland) - 1);
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
    public Map<String, SchoolBoard> getAllSchoolBoards(){
        Map<String, SchoolBoard> mapSchoolBoards = new HashMap<>();
        for(Player player: players){
            mapSchoolBoards.put(player.getUsername(), player.getSchoolBoard());
        }
        return mapSchoolBoards;
    }

    /**
     * @param idIsland the id of the island to find.
     * @return true if the island with that id exists, false otherwise.
     */
    private boolean existsIsland(int idIsland){
        for(Island island: islands){
            if(island.getId() == idIsland)
                return true;
        }
        return false;
    }

    public String[] professorsWithUsernameOwner(){
        return Arrays.stream(professors).map(p->{if(p == null) return "_"; else return p.getUsername();}).toArray(String[]::new);
    }

    /**
     * This method remove a player and then end the game.
     */
    public void disconnectPlayers() {
        setGameState(GameState.END);
        notify(new DisconnectionMessage());
        //endGame();
    }

    //public void endGame() {
    //    instance=null;
    //}

    //GETTERS ----------------------------------------------
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

    public boolean[] getTowerColorChosen() {
        return towerColorChosen;
    }

    public Player[] getProfessors() {
        return professors;
    }

    public ArrayList<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    public ProfessorsManager getProfessorsManager() {
        return professorsManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public ArrayList<CloudTile> getAvailableCloudTiles(){
        return availableCloudTiles;
    }

    /**
     * @param cloudTileId the id of the cloud tile to return.
     * @return the cloud tile with the id passed by parameter.
     * @throws IllegalClientInputException if the cloud tile with that id doesn't exist.
     */
    public CloudTile getCloudTileById(int cloudTileId) throws IllegalClientInputException{
        for(CloudTile cloudTile: cloudTiles){
            if(cloudTile.getId() == cloudTileId)
                return cloudTile;
        }
        throw new IllegalClientInputException();
    }

    /**
     * @param id the id of the island to return.
     * @return the island with the id passed by parameter.
     * @throws IllegalClientInputException if the island with that id doesn't exist.
     */
    public Island getIslandByID(int id) throws IllegalClientInputException{
        for(Island island: islands){
            if(island.getId() == id)
                return island;
        }
        throw new IllegalClientInputException(ErrorTypeID.NO_ISLAND_WITH_THAT_ID);
    }

    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }



    // SETTERS ----------------------------------------------


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        if(gameState == GameState.PLANNING_PHASE || gameState == GameState.ACTION_PHASE || gameState == GameState.END){
            notify(new ChangePhaseMessage(gameState));
        }
    }
}
