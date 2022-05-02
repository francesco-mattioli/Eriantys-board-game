package it.polimi.ingsw.triton.launcher.server.view;

import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.*;
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
    private Message lastCharacterCardMessage;

    public VirtualView(ServeOneClient serveOneClient, String username){
        this.serveOneClient = serveOneClient;
        this.username = username;
    }

    @Override
    public void update(Message message) {
        message.accept(new BroadcastMessageVisitor(serveOneClient,this.getUsername()));
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
        AssistantCardRequest requestMessage = new AssistantCardRequest();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    @Override
    public void showChangeInfluenceMessage() {
        //IT MUST NOT BE HERE!!
    }

    @Override
    public void askCloudTile() {
        CloudTileRequest requestMessage = new CloudTileRequest();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    @Override
    public void showDisconnectionMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showEmptyBagMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showErrorMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showFillCloudTilesMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showFullLobbyMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showGameInfo() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showGenericMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showInfoActionPhase() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showInfoAssistantCardPlayed() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showLobbyMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showLoginReply() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showMergeIslandsMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showMotherNaturePosition() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askMoveStudentFromEntrance() {
        MoveStudentFromEntranceMessage requestMessage = new MoveStudentFromEntranceMessage();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    @Override
    public void showMoveTowerOntoIsland() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showMoveTowerOntoSchoolBoard() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askNumberStepsMotherNature() {
        MotherNatureRequest requestMessage = new MotherNatureRequest();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    @Override
    public void askPlayersNumber() {
        //TODO IMPLEMENT
    }

    @Override
    public void showTieMessage() {
        //IT MUST NOT BE HERE
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
        //IT MUST NOT BE HERE
    }


    @Override
    public void showLoseMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showYourTurnMessage() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void showAvailableCharacterCard() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askStudentsToMoveOntoIslandCharCard01() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askIslandToCalculateInfluenceCharCard03() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askIslandToPutNoEntryTileCharCard05() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askStudentToSwitchFromCardToEntranceCharCard07() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askColorWithNoInfluenceCharCard09() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askStudentsToSwitchCharCard10() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askStudentsToMoveIntoDiningRoomCharCard11() {
        //IT MUST NOT BE HERE
    }

    @Override
    public void askColorCharCard12() {
        //IT MUST NOT BE HERE
    }

    public void askCharacterCardParameters(int id){
        CharacterCardParameterRequest requestMessage = new CharacterCardParameterRequest(id);
        serveOneClient.sendMessage(requestMessage);
        lastCharacterCardMessage = requestMessage;
    }

    public void reSendLastMessage(){
        serveOneClient.sendMessage(lastMessage);
    }

    public void reSendLastCharacterCardMessage(){
        serveOneClient.sendMessage(lastCharacterCardMessage);
    }

    public String getUsername() {
        return username;
    }

}
