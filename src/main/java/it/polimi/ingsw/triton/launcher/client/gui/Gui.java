package it.polimi.ingsw.triton.launcher.client.gui;

import it.polimi.ingsw.triton.launcher.client.gui.scenes.LoginSceneController;
import it.polimi.ingsw.triton.launcher.client.gui.scenes.MenuSceneController;
import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.util.ArrayList;
import java.util.Map;

public class Gui implements ClientView {
    private ClientModel clientModel;
    private MenuSceneController menuSceneController;
    private LoginSceneController loginSceneController;



    @Override
    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public void askUsername() {

    }

    @Override
    public void showGenericMessage(String genericMessage) {

    }

    @Override
    public void showLobbyMessage(ArrayList<String> onlineNicknames, int maxNumberPlayers) {

    }

    @Override
    public void showGameInfo(ArrayList<CharacterCard> availableCharacterCards, ArrayList<Island> islands, Map<String, SchoolBoard> schoolBoards, ArrayList<CloudTile> cloudTiles, Island motherNaturePosition) {

    }

    @Override
    public void showDisconnectionMessage(String disconnectedUsername) {

    }

    @Override
    public void showEmptyBagMessage() {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showFillCloudTilesMessage() {

    }

    @Override
    public void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard) {

    }

    @Override
    public void askTowerColor(boolean[] towerColorChosen) {

    }

    @Override
    public void askNumOfPlayers() {

    }

    @Override
    public void askWizard(ArrayList<Wizard> wizards) {

    }

    @Override
    public void askAssistantCard() {

    }

    @Override
    public void askCloudTile() {

    }

    @Override
    public void showLoginReply() {

    }

    @Override
    public void askMoveStudentFromEntrance() {

    }

    @Override
    public void askNumberStepsMotherNature() {

    }

    @Override
    public void askGameMode() {

    }

    @Override
    public void askCharacterCardParameters(int id) {

    }

    @Override
    public void showErrorMessage(ErrorTypeID fullLobby) {

    }

    @Override
    public void showMoveTowerOntoIsland() {

    }

    @Override
    public void showMoveTowerOntoSchoolBoard() {

    }

    @Override
    public void showTieMessage() {

    }

    @Override
    public void showWinMessage() {

    }

    @Override
    public void showLoseMessage(String winnerUsername) {

    }

    @Override
    public void showYourTurnMessage() {

    }

    @Override
    public void showAvailableCharacterCard() {

    }
}
