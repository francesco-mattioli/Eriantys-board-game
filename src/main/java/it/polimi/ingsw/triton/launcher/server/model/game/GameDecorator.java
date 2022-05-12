package it.polimi.ingsw.triton.launcher.server.model.game;

import it.polimi.ingsw.triton.launcher.server.model.*;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;

import java.util.ArrayList;
import java.util.Map;

public class GameDecorator extends GameMode{
    protected GameMode game;

    public GameDecorator(GameMode game) {
        this.game = game;
    }

    @Override
    public void setup() {
        game.setup();
    }

    @Override
    public void setupPlayers() {
        game.setupPlayers();
    }

    @Override
    public void planningPhase() {
        game.planningPhase();
    }

    @Override
    public void executeActionMoveStudentToDiningRoom(Color student) throws LastMoveException, IllegalClientInputException {
        game.executeActionMoveStudentToDiningRoom(student);
    }

    @Override
    public void useCharacterCard(String username, int idCard) throws IllegalClientInputException, CharacterCardWithParametersException {
        game.useCharacterCard(username,idCard);
    }

    @Override
    public void applyCharacterCardEffect(int characterCardID, CardEffect cardEffect) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(characterCardID,cardEffect);
    }

    @Override
    public ArrayList<CharacterCard> getCharacterCards() {
        return game.getCharacterCards();
    }

    @Override
    public CharacterCard getCharacterCardByID(int id) throws IllegalClientInputException {
        return game.getCharacterCardByID(id);
    }

    @Override
    public void setGameState(GameState setup) {
        game.setGameState(setup);
    }

    @Override
    public boolean[] getTowerColorChosen() {
        return game.getTowerColorChosen();
    }

    @Override
    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    @Override
    public void calculateWinner() {
        game.calculateWinner();
    }

    @Override
    public void addPlayer(String username) {
        game.addPlayer(username);
    }

    @Override
    public ArrayList<Island> getIslands() {
        return game.getIslands();
    }

    @Override
    public GameState getGameState() {
        return game.getGameState();
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return game.getPlayers();
    }

    @Override
    public void chooseTowerColor(String senderUsername, TowerColor playerColor) throws IllegalClientInputException, ChangeTurnException {
        game.chooseTowerColor(senderUsername, playerColor);
    }

    @Override
    public void chooseWizard(String senderUsername, Wizard playerWizard) throws IllegalClientInputException, ChangeTurnException {
        game.chooseWizard(senderUsername, playerWizard);
    }

    @Override
    public void chooseAssistantCard(String senderUsername, AssistantCard chosenAssistantCard) throws IllegalClientInputException, ChangeTurnException {
        game.chooseAssistantCard(senderUsername,chosenAssistantCard);
    }

    @Override
    public void executeActionMoveStudentToIsland(Color student, int islandID) throws IllegalClientInputException, LastMoveException {
        game.executeActionMoveStudentToIsland(student,islandID);
    }

    @Override
    public void moveMotherNature(int numSteps) throws IllegalClientInputException, EndGameException, ChangeTurnException {
        game.moveMotherNature(numSteps);
    }

    @Override
    public CloudTile getCloudTileById(int selectedCloudTileID) throws IllegalClientInputException {
        return game.getCloudTileById(selectedCloudTileID);
    }

    @Override
    public void chooseCloudTile(CloudTile cloudTileById) throws IllegalClientInputException, ChangeTurnException, EndGameException {
        game.chooseCloudTile(cloudTileById);
    }

    @Override
    public Player getPlayerByUsername(String senderUsername) {
        return game.getPlayerByUsername(senderUsername);
    }

    @Override
    public MotherNature getMotherNature() {
        return game.getMotherNature();
    }

    @Override
    public ProfessorsManager getProfessorsManager() {
        return game.getProfessorsManager();
    }

    @Override
    public Player[] getProfessors() {
        return game.getProfessors();
    }

    @Override
    public Island getIslandByID(int islandID) throws IllegalClientInputException {
        return game.getIslandByID(islandID);
    }

    @Override
    public Bag getBag() {
        return game.getBag();
    }

    @Override
    public ArrayList<Wizard> getAvailableWizards() {
        return game.getAvailableWizards();
    }


    @Override
    public void setupMotherNature() {
        game.setupMotherNature();
    }

    @Override
    public void setupBag() {
        game.setupBag();
    }

    @Override
    public void setupIslands() {
        game.setupIslands();
    }

    @Override
    public void createCloudTiles() {
        game.createCloudTiles();
    }

    @Override
    public void setupEntrance() {
        game.setupEntrance();
    }

    @Override
    public void checkNumberMoves() throws LastMoveException {
        game.checkNumberMoves();
    }

    @Override
    public Map<String, SchoolBoard> getAllSchoolBoards() {
        return game.getAllSchoolBoards();
    }

    @Override
    public ArrayList<CloudTile> getCloudTiles() {
        return game.getCloudTiles();
    }

    @Override
    public String[] professorsWithUsernameOwner() {
        return game.professorsWithUsernameOwner();
    }

    @Override
    public int getMaxNumberOfPlayers() {
        return game.getMaxNumberOfPlayers();
    }

    @Override
    public void setMaxNumberOfPlayers(int maxNumberOfPlayers){
        game.setMaxNumberOfPlayers(maxNumberOfPlayers);
    }

    @Override
    public void setCurrentPlayer(Player player) {
        game.setCurrentPlayer(player);
    }

    @Override
    public void disconnectPlayers() {
        game.disconnectPlayers();
    }
}
