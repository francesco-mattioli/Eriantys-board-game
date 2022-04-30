package it.polimi.ingsw.triton.launcher.server.view;

import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.GameModeRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.PlayersNumberRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.TowerColorRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.WizardRequest;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.utils.message.*;
import it.polimi.ingsw.triton.launcher.utils.View;

import java.util.ArrayList;

public class VirtualView extends Observable<Message> implements View, Observer<Message> {
    private ServeOneClient serveOneClient;
    private String username;
    private Message lastMessage;

    public VirtualView(ServeOneClient serveOneClient, String username){
        this.serveOneClient = serveOneClient;
        this.username = username;
    }

    @Override
    public void update(Message message) {
        message.accept(new MessageVisitor(serveOneClient,this.getUsername()));
    }

    public void askGameMode(){
        serveOneClient.sendMessage(new GameModeRequest());
    }

    public void askNumOfPlayers(){
        serveOneClient.sendMessage(new PlayersNumberRequest(username));
    }

    public void showErrorMessage(ErrorTypeID errorTypeID){
        serveOneClient.sendMessage(new ErrorMessage(errorTypeID));
    }




    @Override
    public void askAssistantCard() {

    }

    @Override
    public void showChangeInfluenceMessage() {

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
    public void askPlayersNumber() {

    }

    @Override
    public void showTieMessage() {

    }

    @Override
    public void askTowerColor(boolean[] towerColorChosen) {
        serveOneClient.sendMessage(new TowerColorRequest(towerColorChosen));
    }

    @Override
    public void askWizard(ArrayList<Wizard> wizards) {
        serveOneClient.sendMessage(new WizardRequest(wizards));
    }


    @Override
    public void showWinMessage() {

    }


    @Override
    public void showLoseMessage() {

    }

    @Override
    public void showYourTurnMessage() {

    }

    @Override
    public void showAvailableCharacterCard() {

    }

    @Override
    public void askStudentsToMoveOntoIslandCharCard01() {

    }

    @Override
    public void askIslandToCalculateInfluenceCharCard03() {

    }

    @Override
    public void askIslandToPutNoEntryTileCharCard05() {

    }

    @Override
    public void askStudentToSwitchFromCardToEntranceCharCard07() {

    }

    @Override
    public void askColorWithNoInfluenceCharCard09() {

    }

    @Override
    public void askStudentsToSwitchCharCard10() {

    }

    @Override
    public void askStudentsToMoveIntoDiningRoomCharCard11() {

    }

    @Override
    public void askColorCharCard12() {

    }

    public void reSendLastMessage(){
        serveOneClient.sendMessage(lastMessage);
    }


    public String getUsername() {
        return username;
    }

}
