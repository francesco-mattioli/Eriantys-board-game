package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.cardeffects.*;
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
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.Message;

import java.util.*;
import java.util.stream.Collectors;

public class Game extends Observable<Message> {

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
    private ArrayList<Wizard> availableWizards;
    private boolean lastRound = false;
    private GameState gameState;
    private Message lastMessage;


    public Game(int maxNumberOfPlayers) {
        this.islands = new ArrayList<>();
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.bag = new Bag(maxNumberOfPlayers);
        this.players = new ArrayList<>();
        this.cloudTiles = new ArrayList<>();
        this.characterCards = new ArrayList<>();
        this.generalCoinSupply = INITIAL_NUM_COINS;
        this.towerColorChosen = new boolean[TowerColor.values().length];
        this.availableWizards = new ArrayList<>(Arrays.asList(Wizard.values()));
        this.gameState = GameState.LOGIN;
        this.professors = new Player[Color.numOfColors()];
        this.usedAssistantCards = new ArrayList<>();
    }


    /**
     * This method checks if the username entered by the player has already been chosen
     * @param username
     * @return true if the username has already been chosen else false
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
     * @throws IllegalArgumentException if the username is not correct (already used).
     */
    public void addPlayer(String username) throws IllegalArgumentException {
        if(!isUsernameChosen(username) && !username.equals(Game.NAME_SERVER)){
            players.add(new Player(username));
            notify(new LobbyMessage(getAllUsernames(players), maxNumberOfPlayers));
        }
        else
            throw new IllegalArgumentException("Username already chosen");    //caught by lobby method in server
    }

    /**
     * Creates a new message for requesting to the player to choose the tower color.
     * @param username the username of the receiver player.
     */
    public void createTowerColorRequestMessage(String username){
        notify(new TowerColorRequest(towerColorChosen, username));
    }

    /**
     * This method set the player's schoolboard with the chosen tower color
     * @param username the username of the player that has chosen the tower color.
     * @param towerColor the color of the tower.
     */
    public void chooseTowerColor(String username, TowerColor towerColor) {
        Player p = getPlayerByUsername(username);
        p.setSchoolBoard(towerColor, maxNumberOfPlayers);
        towerColorChosen[towerColor.ordinal()] = true;
        try{
            String nextUsername = getNextPlayerName(p);
            createTowerColorRequestMessage(nextUsername);
        }
        catch (NoSuchElementException e){
            createChooseWizardMessage(players.get(0).getUsername());
        }
    }

    /**
     * Creates a new message to send to the player to select the wizard.
     * @param username the receiver player's username.
     */
    public void createChooseWizardMessage(String username){
        notify(new WizardRequest(availableWizards, username));
    }

    /**
     * Sets the wizard to the player and creates a new request to the next player if present.
     * @param username the player's username of who sets the wizard.
     * @param wizard the wizard selected by the player.
     */
    public void chooseWizard(String username, Wizard wizard){
        Player p = getPlayerByUsername(username);
        p.setWizard(wizard);
        availableWizards.remove(wizard);
        try{
            String nextUsername = getNextPlayerName(p);
            createChooseWizardMessage(nextUsername);
        }catch (NoSuchElementException e){
            setup();
        }
    }

    /**
     * @param current the current player of the game.
     * @return the next player that will play his turn.
     * @throws NoSuchElementException if the there's not a next player.
     */
    private String getNextPlayerName(Player current) throws NoSuchElementException{
        int index = players.indexOf(current);
        if(index < players.size()-1){
            currentPlayer = players.get(index+1);
            return players.get(index+1).getUsername();
        }
        throw new NoSuchElementException("There is not a next player");
    }

    /**
     * @param username the username of the player.
     * @return the player with the passed username.
     * @throws NoSuchElementException if there's not a player with the passed username.
     */
    private Player getPlayerByUsername(String username) throws NoSuchElementException {
        for(Player p : players){
            if(p.getUsername().equals(username))
                return p;
        }
        throw new NoSuchElementException("This player does not exist");
    }

    /**
     * This method executes the SETUP phase of the game
     */
    public void setup() {
        gameState = GameState.SETUP;
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
        drawCharacterCards(); //(PHASE 12) creates 3 character cards
        notify(new GameInfoMessage(characterCards, islands, motherNature.getPosition(), getAllSchoolBoards(), cloudTiles));
        for(Player player: players)
            notify(new GiveAssistantDeckMessage(player.getUsername(), player.getAssistantDeck()));
        notify(new YourTurnMessage(currentPlayer.getUsername()));
        planningPhase();
    }

     // The following methods execute the PLANNING phase of the game

    /**
     * Adds the students to the cloud tiles and sets the last round flag if the bag is empty.
     * It sends messages to communicate the new filled cloud tiles and if the bag is empty.
     */
    public void addStudentsToCloudTiles(){
        boolean exit = false;
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
                    student = bag.drawStudent();
                }catch(NoSuchElementException e){           //HERE we don't expect to enter
                    lastRound = true;
                    notify(new EmptyBagMessage());
                    exit = true;
                    break;
                }
                cloudTile.setStudents(student);
                if(bag.isEmpty()){
                    lastRound = true;
                    notify(new EmptyBagMessage());
                    exit = true;
                    break;
                }
            }
            if(exit)
                break;
        }
        notify(new FillCloudTilesMessage(cloudTiles));
    }

    /**
     * This method defines the game turn based on the played assistant cards
     */
    private void sortPlayerPerTurn() {
        players.sort(new PlayerTurnComparator());
    }

    /**
     * This method allows one player per time to play his assistant card.
     * When all the players have played their card the game defines the game turn.
     */
    private void nextPlayCardTurn() {
        if (players.indexOf(currentPlayer) < maxNumberOfPlayers - 1) {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            notify(new YourTurnMessage(currentPlayer.getUsername()));
            createAssistantCardRequestMessage();
        }
        else{
            sortPlayerPerTurn();
            currentPlayer = players.get(0);
            notify(new YourTurnMessage(currentPlayer.getUsername()));
            actionPhase();
        }
    }


    // Planning phase
    public void planningPhase() {
        gameState = GameState.PLANNING_PHASE;
        addStudentsToCloudTiles();
        resetPlayedCardInTurn();
        createAssistantCardRequestMessage();
    }

    //Action phase
    public void actionPhase(){
        gameState = GameState.BEGIN_ACTION_PHASE;
        createMoveStudentsMessage();
    }


    /**
     * Creates the message to ask the player which students wants to move and where he wants to move to.
     */
    private void createMoveStudentsMessage(){
        //notify(new InfoActionPhase(currentPlayer.getUsername(), getAllSchoolBoards(), islands, motherNature.getPosition()));
        Message message = new MoveStudentFromEntranceMessage(currentPlayer.getUsername());
        saveMessage(message);
        notify(message);
    }

    /**
     * Executes the action of moving a player from entrance to the dining room.
     * @param student the color of the student to move
     */
    public void executeActionMoveStudentToDiningRoom(Color student){
        if(currentPlayer.getSchoolBoard().getEntrance()[student.ordinal()] == 0)
            notify(new ErrorMessage(ErrorTypeID.NO_STUDENT_WITH_COLOR_ENTRANCE, currentPlayer.getUsername()));
        else{
            currentPlayer.executeAction(new MoveStudentIntoDiningRoom(student, currentPlayer.getWallet(), currentPlayer.getSchoolBoard()));
            notify(new InfoStudentIntoDiningRoomMessage(currentPlayer.getUsername()));
            currentPlayer.setMoveCounter(currentPlayer.getMoveCounter() + 1);
            checkNumberMoves();
        }
    }

    public void executeActionMoveStudentToIsland(Color student, int idIsland){
        if(currentPlayer.getSchoolBoard().getEntrance()[student.ordinal()] == 0) {
            notify(new ErrorMessage(ErrorTypeID.NO_STUDENT_WITH_COLOR_ENTRANCE, currentPlayer.getUsername()));
        }else if(currentPlayer.getSchoolBoard().getEntrance()[student.ordinal()] != 0 && !existingIsland(idIsland)){
            notify(new ErrorMessage(ErrorTypeID.NO_ISLAND_WITH_THAT_ID, currentPlayer.getUsername()));
        }else{
            currentPlayer.executeAction(new MoveStudentOntoIsland(currentPlayer.getSchoolBoard(), student, findIsland(idIsland)));
            currentPlayer.setMoveCounter(currentPlayer.getMoveCounter() + 1);
            checkNumberMoves();
        }
    }

    /**
     * Checks if the player has to move again a student.
     */
    public void checkNumberMoves(){
        int numberMoves;
        if(maxNumberOfPlayers == 2)
            numberMoves = 3;
        else
            numberMoves = 4;
        if(currentPlayer.getMoveCounter() != numberMoves)
            createMoveStudentsMessage();
        else{
            currentPlayer.setMoveCounter(0);
            Message message = new NumberStepsMotherNatureMessage(currentPlayer.getUsername());
            saveMessage(message);
            notify(message);
        }
    }

    /**
     * Moves mother nature to another island.
     * @param numSteps the number of steps that mother nature has to do.
     */
    public void moveMotherNature(int numSteps){
        gameState = GameState.MIDDLE_ACTION_PHASE;
        try{
            Island newPosition = motherNature.move(currentPlayer.getLastPlayedAssistantCard(), numSteps, islands);
            motherNature.setIslandOn(newPosition);
            notify(new MotherNaturePositionMessage(newPosition));
        }catch (IllegalArgumentException e){
            notify(new ErrorMessage(ErrorTypeID.TOO_MANY_MOTHERNATURE_STEPS, currentPlayer.getUsername()));
        }
        mergeNearIslands(motherNature.getPosition());
    }

    /**
     * Asks all the players to play assistant card.
     */
    private void createAssistantCardRequestMessage(){
        Message message = new AssistantCardRequest(currentPlayer.getUsername());
        saveMessage(message);
        notify(message);
    }

    /**
     * Sets the action to the player to play the assistant card and communicates to the others which card he played.
     * @param username the username of the player that plays the assistant card.
     * @param assistantCard the assistant card played.
     */
    public void chooseAssistantCard(String username, AssistantCard assistantCard){
        try{
            currentPlayer.executeAction(new PlayAssistantCard(assistantCard, getPlayerByUsername(username),usedAssistantCards));
            notify(new InfoAssistantCardPlayedMessage(currentPlayer.getUsername(), assistantCard));
            nextPlayCardTurn();
        }
        catch (NoSuchElementException e){  //case player name not found in players

        }
        catch (RuntimeException e) {  //case AssistantCard already chosen in the same turn
            notify(new ErrorMessage(ErrorTypeID.ASSISTANTCARD_ALREADY_CHOSEN, currentPlayer.getUsername()));
        }
    }


    //--- methods for the PIANIFICATION PHASE

    //--- end of methods for the PLANNING PHASE


    // methods for the SETUP PHASE
    private void createIslands() {
        for (int i = 0; i < MAX_NUM_OF_ISLANDS; i++) {
            islands.add(new Island(i));
        }
    }

    /**
     * This method places mother nature on a random island.
     */
    private void setupMotherNature() {
        Random random = new Random();
        int randomIndex = random.nextInt(islands.size());
        motherNature = new MotherNature(islands.get(randomIndex));
    }


    /**
     * This method places two students for each color into the bag.
     */
    private void setupBag() {
        for (int i = 0; i < NUM_OF_STUDENTS_COLORS; i++) {
            for (int j = 0; j < 2; j++)
                bag.addStudent(Color.values()[i]);
        }
    }

    /**
     * This method places two students on every island except the one with mother nature and the one in front of it.
     */
    private void setupIslands() {
        for (Island island : islands) {
            if (island.getId() != motherNature.getIndexOfOppositeIsland(islands) && island.getId() != motherNature.getPosition().getId()) {
                island.addStudent(bag.drawStudent());
            }
        }
    }

    /**
     * This method sorts the players ArrayList random, and sets correctly the current player
     */
    private void setupPlayers(){
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
    private void createCloudTiles() {
        for (int i = 0; i < maxNumberOfPlayers; i++) {
            cloudTiles.add(new CloudTile(i));
        }
    }

    /**
     * This method add seven students into the entrance when there are two players.
     * This method add nine students into the entrance when there are three players.
     */
    private void setupEntrance() {
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
     * @param motherNaturePosition the island where mother nature is located.
     * @throws RuntimeException
     */
    public void mergeNearIslands(Island motherNaturePosition) throws RuntimeException {
        motherNaturePosition.updateInfluence(players, professors);
        if (motherNaturePosition.getDominator() != null) {
            if (motherNaturePosition.getDominator() == prevIsland(motherNaturePosition).getDominator()) {
                motherNaturePosition.merge(prevIsland(motherNaturePosition));
                notify(new MergeIslandsMessage(motherNaturePosition.getDominator().getUsername(), motherNaturePosition, prevIsland(motherNaturePosition)));
                islands.remove(prevIsland(motherNaturePosition));
                checkNumberIslands();
            }
            if (motherNaturePosition.getDominator() == nextIsland(motherNaturePosition).getDominator()) {
                motherNaturePosition.merge(nextIsland(motherNaturePosition));
                notify(new MergeIslandsMessage(motherNaturePosition.getDominator().getUsername(), motherNaturePosition, nextIsland(motherNaturePosition)));
                islands.remove(nextIsland(motherNaturePosition));
                checkNumberIslands();
            }
        }
        ArrayList<CloudTile> availableCloudTiles = new ArrayList<>();
        for(CloudTile cloudTile: cloudTiles){
            if(!cloudTile.isAlreadyUsed())
                availableCloudTiles.add(cloudTile);
        }
        gameState = GameState.END_ACTION_PHASE;
        Message message = new CloudTileRequest(availableCloudTiles, currentPlayer.getUsername());
        saveMessage(message);
        notify(message);
    }

    /**
     * Manages the action of the player to choose the cloud tile.
     * @param cloudTile the cloud tile selected from the player.
     */
    public void chooseCloudTile(CloudTile cloudTile){
        try{
            currentPlayer.executeAction(new ChooseCloudTile(cloudTile, currentPlayer.getSchoolBoard()));
        }catch (RuntimeException e){
            notify(new ErrorMessage(ErrorTypeID.CLOUD_TILE_ALREADY_CHOSEN, currentPlayer.getUsername()));
        }
        cloudTile.setAlreadyUsed(true);
        nextGameTurn();
    }

    /**
     * Checks if the number of remaining groups of islands is three.
     * @throws RuntimeException if the number of groups islands is three because the game must finish.
     */
    private void checkNumberIslands() throws RuntimeException{
        if(islands.size() == 3)   //TO END
            throw new RuntimeException("Only three groups of islands: game ended.");
    }

    /**
     * This methods calculates the winner when one player has the max number of towers onto islands.
     * If two or more players have the same number of towers on islands, is called a new method for the calculation of the winner because of professors
     * If there is a winner, virtualViews are notified using a WinMessage
     */
    public void calculateWinner(){
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
     * This methods checks occurrences of players in list into the professors array.
     * If one of the players has more professors than the others, he's the winner, else we have a tie
     * In case of tie we notify virtualViews using a TieMessage, which specifies the list of the peer players
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
    public void nextGameTurn() {
        if (players.indexOf(currentPlayer) < maxNumberOfPlayers - 1){
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            professorsManager.setProfessorStrategy(new ProfessorStrategyDefault());
            for(Island island: islands)
                island.setInfluenceStrategy(new InfluenceStrategyDefault());
            notify(new YourTurnMessage(currentPlayer.getUsername()));
        }
        else if(!lastRound && players.indexOf(currentPlayer) == maxNumberOfPlayers - 1){
            currentPlayer = players.get(0);
            notify(new YourTurnMessage(currentPlayer.getUsername()));
            planningPhase();
        }else{
            calculateWinner();
        }
    }

    /**
     * This method creates a new message to choose the character card.
     */
    public void createCharacterCardsMessage(){
        notify(new AvailableCharacterCardReply(characterCards, currentPlayer.getUsername()));
    }

    /**
     * Sends messages to the player to ask the parameters for some effects.
     * @param idCard the id of the selected character card
     */
    public void manageEffectCharacterCards(int idCard){
        int indexCard = 0;
        boolean foundCard = false;
        for(int i = 0; i < characterCards.size(); i++){
            if(characterCards.get(i).getId() == idCard){
                indexCard = i;
                foundCard = true;
                break;
            }
        }
        if(!foundCard)
            notify(new ErrorMessage(ErrorTypeID.CHARACTER_CARD_NOT_AVAILABLE, currentPlayer.getUsername()));
        else if(foundCard && !canBuyCharacterCard(currentPlayer, characterCards.get(indexCard))){
            notify(new ErrorMessage(ErrorTypeID.NOT_ENOUGH_COINS, currentPlayer.getUsername()));
        }else{
            switch (idCard){
                case 1:
                    notify(new CharacterCard01Request(currentPlayer.getUsername()));
                break;
                case 3:
                    notify(new CharacterCard03Request(currentPlayer.getUsername()));
                case 5:
                    notify(new CharacterCard05Request(currentPlayer.getUsername()));
                break;
                case 7:
                    notify(new CharacterCard07Request(currentPlayer.getUsername()));
                break;
                case 9:
                    notify(new CharacterCard09Request(currentPlayer.getUsername()));
                break;
                case 10:
                    notify(new CharacterCard10Request(currentPlayer.getUsername()));
                break;
                case 11:
                    notify(new CharacterCard11Request(currentPlayer.getUsername()));
                break;
                case 12:
                    notify(new CharacterCard12Request(currentPlayer.getUsername()));
                break;
                default:
                    useCharacterCardWithoutPreparation(characterCards.get(indexCard));
                break;
            }
        }
    }

    /**
     * @param player the player who wants to play the character card.
     * @param characterCard the character card to play.
     * @return true if the card can be purchased, false otherwise.
     */
    private boolean canBuyCharacterCard(Player player, CharacterCard characterCard){
        return player.getWallet().getValue() >= characterCard.getCost();
    }

    /**
     * Applies the effect to the cards that don't require a preparation.
     * @param characterCard the character card on which to apply the effect.
     */
    public void useCharacterCardWithoutPreparation(CharacterCard characterCard){
        switch(characterCard.getId()){
            case 2:
                currentPlayer.executeAction(new UseCharacterCard(characterCard, new CardEffect02(currentPlayer, professorsManager), currentPlayer.getWallet()));
            break;
            case 4:
                currentPlayer.executeAction(new UseCharacterCard(characterCard, new CardEffect04(motherNature), currentPlayer.getWallet()));
            break;
            case 6:
                currentPlayer.executeAction(new UseCharacterCard(characterCard, new CardEffect06(islands), currentPlayer.getWallet()));
            break;
            case 8:
                currentPlayer.executeAction(new UseCharacterCard(characterCard, new CardEffect08(islands), currentPlayer.getWallet()));
            break;
        }
        notify(new InfoCharacterCardPlayedMessage(currentPlayer.getUsername(), characterCard));
    }

    /**
     * @param characterCard the character card to play
     * @param cardEffect the effect to apply.
     */
    public void useCharacterCardsWithPreparation(CharacterCard characterCard, CardEffect cardEffect){
        currentPlayer.executeAction(new UseCharacterCard(characterCard, cardEffect, currentPlayer.getWallet()));
        notify(new InfoCharacterCardPlayedMessage(currentPlayer.getUsername(), characterCard));
    }

    /**
     * Creates three character cards.
     * Each one costs 1 coin.
     */
    private void drawCharacterCards() {
        Random randomNumber;
        ArrayList<Integer> idAlreadyChosen = new ArrayList<>();
        int id;
        while(characterCards.size() < 3){
            randomNumber = new Random();
            id = randomNumber.nextInt(12);
            if(!idAlreadyChosen.contains(id)){
                characterCards.add(new CharacterCard(id, 1, 0, this.bag));
                idAlreadyChosen.add(id);
            }
        }
    }

    /**
     * Decreases by 1 the general coin supply.
     * @throws RuntimeException if there aren't coins in the supply.
     */
    public void withdrawCoin() {
        if(generalCoinSupply > 0)
            generalCoinSupply--;
        else
            notify(new ErrorMessage(ErrorTypeID.EMPTY_GENERAL_COIN_SUPPLY, currentPlayer.getUsername()));
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
    private Island nextIsland(Island currentIsland) {
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
        for (AssistantCard assistantCard : usedAssistantCards)
            usedAssistantCards.remove(assistantCard);
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

    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public boolean[] getTowerColorChosen() {
        return towerColorChosen;
    }

    public Player[] getProfessors() {
        return professors;
    }

    public Message getLastMessage(){
        return lastMessage;
    }

    public void saveMessage(Message messageToSave){
        this.lastMessage = messageToSave;
    }

    private boolean existingIsland(int idIsland){
        for(Island island: islands){
            if(island.getId() == idIsland)
                return true;
        }
        return false;
    }

    private Island findIsland(int idIsland){
        for(Island island: islands){
            if(island.getId() == idIsland)
                return island;
        }
        return null;      //We don't expect to reach this statement because we check in the caller method if there's not an island with that id
    }
}
