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
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.GiveAssistantDeckMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;

import java.util.ArrayList;
import java.util.Map;

public interface GameMode {
    void setup();
    void setupPlayers();
    void planningPhase();
    void executeActionMoveStudentToDiningRoom(Color student) throws LastMoveException, IllegalClientInputException;
    void useCharacterCard(String username,int idCard) throws IllegalClientInputException, CharacterCardWithParametersException;
    void applyCharacterCardEffect(int characterCardID, CardEffect cardEffect) throws IllegalClientInputException, EndGameException;
    ArrayList<CharacterCard> getCharacterCards();
    CharacterCard getCharacterCardByID(int id) throws IllegalClientInputException;

    void setGameState(GameState setup);
    boolean[] getTowerColorChosen();
    Player getCurrentPlayer();
    void calculateWinner();
    void addPlayer(String username);
    ArrayList<Island> getIslands();

    GameState getGameState();

    void disconnectPlayers();

    ArrayList<Player> getPlayers();

    void chooseTowerColor(String senderUsername, TowerColor playerColor) throws IllegalClientInputException, ChangeTurnException;

    void chooseWizard(String senderUsername, Wizard playerWizard) throws IllegalClientInputException, ChangeTurnException;

    void chooseAssistantCard(String senderUsername, AssistantCard chosenAssistantCard) throws IllegalClientInputException, ChangeTurnException;

    void executeActionMoveStudentToIsland(Color student, int islandID) throws IllegalClientInputException, LastMoveException;

    void moveMotherNature(int numSteps) throws IllegalClientInputException, EndGameException, ChangeTurnException;

    CloudTile getCloudTileById(int selectedCloudTileID) throws IllegalClientInputException;

    void chooseCloudTile(CloudTile cloudTileById) throws IllegalClientInputException, ChangeTurnException, EndGameException;

    Player getPlayerByUsername(String senderUsername);

    MotherNature getMotherNature();

    ProfessorsManager getProfessorsManager();

    Player[] getProfessors();

    Island getIslandByID(int islandID) throws IllegalClientInputException;

    Bag getBag();

    ArrayList<Wizard> getAvailableWizards();

    void addObserver(VirtualView virtualView);

    void setupMotherNature();

    void setupBag();

    void setupIslands();

    void createCloudTiles();

    void setupEntrance();

    void checkNumberMoves() throws LastMoveException;


    void notify(InfoMessage message);

    Map<String, SchoolBoard> getAllSchoolBoards();

    ArrayList<CloudTile> getCloudTiles();

    String[] professorsWithUsernameOwner();

    int getMaxNumberOfPlayers();

    void setCurrentPlayer(Player player);
}
