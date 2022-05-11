package it.polimi.ingsw.triton.launcher.client.view;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;

import java.util.ArrayList;
import java.util.Map;

public interface ClientView extends View {

    //TO DO AN ABSTRACT CLASS maybe
    ClientModel getClientModel();

    void askIpAddress();

    // Ask methods
    void askUsername();

    // Show methods
    void showGenericMessage(String genericMessage);
    void showLobbyMessage(ArrayList<String> onlineNicknames, int maxNumberPlayers );
    void showGameInfo(ArrayList<Island> islands, Map<String, SchoolBoard> schoolBoards, ArrayList<CloudTile> cloudTiles, Island motherNaturePosition, String[] professors);
    // the following gameInfo is for expertMode
    void showGameInfo(ArrayList<CharacterCard> availableCharacterCards, ArrayList<Island> islands, Map<String, SchoolBoard> schoolBoards, ArrayList<CloudTile> cloudTiles, Island motherNaturePosition, String[] professors);
    void showChangePhase(GameState gameState);
    void showDisconnectionMessage();
    void showEmptyBagMessage();
    void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard);
    void showLoginReply();
    void showMoveTowerOntoIsland(int islandId);
    void showMoveTowerOntoSchoolBoard(String username,SchoolBoard schoolBoard);
    void showWinMessage();
    void showLoseMessage(String winnerUsername);
    void showTieMessage();

}
