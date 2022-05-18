package it.polimi.ingsw.triton.launcher.client.view;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This interface contains all the methods of cli/gui that are useful to show requests from server and to allow
 * the player to reply them.
 */
public interface ClientView extends View {

    /**
     * @return the client model of the player.
     */
    //TO DO AN ABSTRACT CLASS maybe
    ClientModel getClientModel();

    /**
     * Asks the player to insert the IP address of the server.
     */
    void askIpAddress();

    // Ask methods
    /**
     * Asks the player to insert his username.
     */
    void askUsername();

    /**
     * Asks the player if he wants to play again. In that case, it calls askUsername().
     */
    void askPlayAgain();

    // Show methods
    /**
     * Shows to the player a generic message.
     * @param genericMessage the string to print.
     */
    void showGenericMessage(String genericMessage);

    /**
     * Shows which players are online.
     * @param onlineNicknames the list with the online players.
     */
    void showLobbyMessage(List<String> onlineNicknames);

    /**
     * Shows the game information with all school boards, islands and character cards.
     */
    void showGameInfo();

    /**
     * Shows to the player that a new phase is starting.
     * @param gameState the new phase of the game.
     */
    void showChangePhase(GameState gameState);

    /**
     * Shows to the player that another player has disconnected and so the game is ended.
     */
    void showDisconnectionMessage();

    /**
     * Shows to the player that the bag with students is empty and the game will finish at the end of the last player's round.
     */
    void showEmptyBagMessage();

    /**
     * Shows to other players which card is played by current player.
     * @param username the current player's username.
     * @param assistantCard the assistant card just played.
     */
    void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard);

    /**
     * Shows to the player his correct login.
     */
    void showLoginReply();

    /**
     * Shows to other players which student is moved to the dining room by current player.
     * @param username the current player's username.
     * @param moveDescription the description of the move.
     */
    void showInfoStudentIntoDiningRoom(String username, String moveDescription);

    /**
     * Shows to other players which student is moved to an island by current player.
     * @param username the current player's username.
     * @param moveDescription the description of the move.
     */
    void showInfoStudentOntoIsland(String username, String moveDescription);

    /**
     * Shows to the players the new position of mother nature.
     * @param islandId the id of the island with mother nature.
     */
    void showMotherNaturePosition(int islandId);

    /**
     * Shows to the players that an island has a new dominator.
     * @param username the new island dominator's username.
     * @param islandId the id of the island.
     */
    void showChangeInfluenceMessage(String username, int islandId);

    /**
     * Shows to the players that an island is now merged with another one.
     * @param island1Id the id of the island merged with mother nature.
     * @param island2Id the id of the island merged to delete.
     */
    void showMergeIslandsMessage(int island1Id, int island2Id);

    /**
     * Shows to the players that a new tower is built onto an island.
     * @param islandId the id of the island where a new tower is built.
     */
    void showMoveTowerOntoIsland(int islandId);

    /**
     * Shows to the players that a tower came back onto a school board.
     * @param username the school board owner's username.
     * @param schoolBoard the school board with new towers.
     */
    void showMoveTowerOntoSchoolBoard(String username,SchoolBoard schoolBoard);

    /**
     * Shows to the players which cloud tile the current player chose.
     * @param username the current player's username.
     * @param choiceDescription the description of the chosen cloud tile.
     */
    void showInfoChosenCloudTile(String username, String choiceDescription);

    /**
     * Shows to the wallet's owner the amount of coins.
     */
    void showUpdateWallet();

    /**
     * Shows to the player that he's the winner.
     */
    void showWinMessage();

    /**
     * Shows to the losers who won the game.
     * @param winnerUsername the winner's username.
     */
    void showLoseMessage(String winnerUsername);

    /**
     * Shows to the players who have tied.
     */
    void showTieMessage();

    /**
     * Shows to the current player which assistant card has just played.
     * @param assistantCard the assistant card played.
     */
    void showMyInfoAssistantCardPlayed(AssistantCard assistantCard);

    /**
     * Shows to the players that a very important error occurs during the match.
     * The game is ended instantly.
     */
    void showAbortMessage();
}
