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
import java.util.Map;

public interface ClientView extends View {

    //TO DO AN ABSTRACT CLASS maybe
    ClientModel getClientModel();

    void askIpAddress();

    // Ask methods
    void askUsername();

    // Show methods
    void showGenericMessage(String genericMessage);
    void showLobbyMessage(ArrayList<String> onlineNicknames);
    void showGameInfo();
    void showChangePhase(GameState gameState);
    void showDisconnectionMessage();
    void showEmptyBagMessage();
    void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard);
    void showLoginReply();
    void showInfoStudentIntoDiningRoom(String username, String moveDescription);
    void showInfoStudentOntoIsland(String username, String moveDescription);
    void showMotherNaturePosition(int islandId);
    void showChangeInfluenceMessage(String username, int islandId);
    void showMergeIslandsMessage(int island1Id, int island2Id);
    void showMoveTowerOntoIsland(int islandId);
    void showMoveTowerOntoSchoolBoard(String username,SchoolBoard schoolBoard);
    void showWinMessage();
    void showLoseMessage(String winnerUsername);
    void showTieMessage();
    void showMyInfoAssistantCardPlayed(AssistantCard assistantCard);
    void showAbortMessage();

}
