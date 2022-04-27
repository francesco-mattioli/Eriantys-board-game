package it.polimi.ingsw.triton.launcher.server.view;

import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.utils.message.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ErrorMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.PlayersNumbersAndModeRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.TowerColorRequest;
import it.polimi.ingsw.triton.launcher.utils.View;

public class VirtualView extends Observable<Message> implements View, Observer<Message> {
    private ServeOneClient serveOneClient;
    private String username;



    public VirtualView(ServeOneClient serveOneClient, String username){
        this.serveOneClient = serveOneClient;
        this.username = username;
    }

    @Override
    public void update(Message message) {

    }

    public void askNumOfPlayersAndMode(){
        serveOneClient.sendMessage(new PlayersNumbersAndModeRequest(username));
    }

    public void showErrorMessage(ErrorTypeID errorTypeID){
        serveOneClient.sendMessage(new ErrorMessage(errorTypeID, username));
    }

    public void askTowerColor(boolean[] availableColors){
        serveOneClient.sendMessage(new TowerColorRequest(availableColors, username));
    }

    @Override
    public void askUsername() {
        // TODO
    }

    @Override
    public void askAssistantCard() {
        //TODO
    }

    @Override
    public void showChangeInfluenceMessage() {
        //TODO
    }

    @Override
    public void askCloudTile() {

    }

    @Override
    public void showDisconnectionMessage() {

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
    public void showFullLobbyMessage() {

    }

    @Override
    public void showGameInfo() {

    }

    @Override
    public void showGenericMessage() {

    }

    @Override
    public void showInfoActionPhase() {

    }

    @Override
    public void showInfoAssistantCardPlayed() {

    }

    @Override
    public void showLobbyMessage() {

    }

    @Override
    public void showLoginReply() {

    }

    @Override
    public void showMergeIslandsMessage() {

    }

    @Override
    public void showMotherNaturePosition() {

    }

    @Override
    public void askMoveStudentFromEntrance() {

    }

    @Override
    public void showMoveTowerOntoIsland() {

    }

    @Override
    public void showMoveTowerOntoSchoolBoard() {

    }

    @Override
    public void askNumberStepsMotherNature() {

    }

    @Override
    public void askPlayersNumberAndMode() {

    }

    @Override
    public void showTieMessage() {

    }

    @Override
    public void askTowerColor() {

    }

    @Override
    public void showWinMessage() {

    }

    @Override
    public void askWizard() {

    }

    @Override
    public void showYourTurnMessage() {

    }

    @Override
    public void showAvailableCharacterCard() {

    }


    public String getUsername() {
        return username;
    }
}
