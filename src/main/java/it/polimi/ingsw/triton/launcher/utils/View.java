package it.polimi.ingsw.triton.launcher.utils;

import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;

import java.util.ArrayList;

public interface View {

    public void askUsername();
    public void askAssistantCard(AssistantDeck assistantDeck);
    public void showChangeInfluenceMessage(Island islandWithNewInfluence, String usernameDominator);
    public void askCloudTile(ArrayList<CloudTile> availableCloudTiles);
    public void showDisconnectionMessage();
    public void showEmptyBagMessage();
    public void showErrorMessage();
    public void showFillCloudTilesMessage();
    public void showFullLobbyMessage();
    public void showGameInfo();
    public void showGenericMessage();
    public void showInfoActionPhase();
    public void showInfoAssistantCardPlayed();
    public void showLobbyMessage();
    public void showLoginReply();
    public void showMergeIslandsMessage();
    public void showMotherNaturePosition();
    public void askMoveStudentFromEntrance();
    public void showMoveTowerOntoIsland();
    public void showMoveTowerOntoSchoolBoard();
    public void askNumberStepsMotherNature();
    public void askGameMode();
    public void askPlayersNumber();
    public void showTieMessage();
    public void askTowerColor(boolean[] availableColors);
    public void showWinMessage();
    public void askWizard();
    public void showYourTurnMessage();
    public void showAvailableCharacterCard();
}
