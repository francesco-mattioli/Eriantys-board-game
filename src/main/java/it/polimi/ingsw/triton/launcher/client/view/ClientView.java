package it.polimi.ingsw.triton.launcher.client.view;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.View;

import java.util.ArrayList;
import java.util.Map;

public interface ClientView extends View {

    //TO DO AN ABSTRACT CLASS maybe
    public ClientModel getClientModel();

    // Ask methods
    void askUsername();

    // Show methods
    void showGenericMessage(String genericMessage);
    void showLobbyMessage(ArrayList<String> onlineNicknames, int maxNumberPlayers );
    void showGameInfo(ArrayList<CharacterCard> availableCharacterCards, ArrayList<Island> islands, Map<String, SchoolBoard> schoolBoards, ArrayList<CloudTile> cloudTiles, Island motherNaturePosition);

    void showChangeInfluenceMessage();

    void showDisconnectionMessage();

    void showEmptyBagMessage();

    void showErrorMessage();

    void showFillCloudTilesMessage();

    void showFullLobbyMessage();

    void showInfoActionPhase();

    void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard);

    void showLoginReply();

    void showMergeIslandsMessage(int islandWithMotherNatureID, int islandToDeleteID);

    void showMotherNaturePosition(Island motherNaturePosition);

    void showMoveTowerOntoIsland();

    void showMoveTowerOntoSchoolBoard();

    void showTieMessage();

    void showWinMessage();

    void showLoseMessage();

    void showYourTurnMessage();

    void showAvailableCharacterCard();
}
