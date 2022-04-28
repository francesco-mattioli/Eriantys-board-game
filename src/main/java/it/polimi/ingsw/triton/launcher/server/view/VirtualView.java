package it.polimi.ingsw.triton.launcher.server.view;

import it.polimi.ingsw.triton.launcher.utils.message.servermessage.*;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.utils.message.*;
import it.polimi.ingsw.triton.launcher.utils.View;

public class VirtualView extends Observable<Message> implements View, Observer<Message> {
    private ServeOneClient serveOneClient;
    private String username;
    private Message message;



    public VirtualView(ServeOneClient serveOneClient, String username){
        this.serveOneClient = serveOneClient;
        this.username = username;
    }

    @Override
    public void update(Message message) {
        this.message = message;
        switch (message.getMessageType()){
            case TOWER_COLOR_REQUEST:{
                if (getUsername().equals(((TowerColorRequest) message).getReceiverUsername()))
                    askTowerColor();
                break;
            }

            case WIN:{
                if (getUsername().equals(((WinMessage) message).getReceiverUsername()))
                    showWinMessage();
                else showLoseMessage(((WinMessage) message).getReceiverUsername());
                break;
            }

            case ASSISTANT_CARD_REQUEST:{
                if (getUsername().equals(((AssistantCardRequest)message).getReceiverUsername()))
                    askAssistantCard();
                break;
            }

            case CHANGE_INFLUENCE_ISLAND:{
                showChangeInfluenceMessage();
                break;
            }

            case CLOUD_TILE_REQUEST:{
                if (getUsername().equals(((CloudTileRequest) message).getReceiverUsername()))
                    askCloudTile();
                break;
            }

            case DISCONNECTION:{
                showDisconnectionMessage();
                break;
            }

            case EMPTY_BAG:{
                showEmptyBagMessage();
                break;
            }

            case FILLED_CLOUD_TILES:{
                showFillCloudTilesMessage();
                break;
            }

            case FULL_LOBBY:{
                if (getUsername().equals(((FullLobbyMessage) message).getReceiverUsername()))
                    showFullLobbyMessage();
                break;
            }

            case GAME_INFO:{
                showGameInfo();
                break;
            }

            case GENERIC:{
                if (getUsername().equals(((GenericMessage) message).getReceiverUsername()))
                    showGenericMessage();
                break;
            }

            case INFO_ACTION_PHASE:{
                if (getUsername().equals(((InfoActionPhase) message).getReceiverUsername()))
                    showInfoActionPhase();
                break;
            }

            case INFO_ASSISTANT_CARD_PLAYED:{
                showInfoAssistantCardPlayed();
                break;
            }

            case LOBBY:{
                showLobbyMessage();
                break;
            }

            case LOGIN_REPLY:{
                if (getUsername().equals(((LoginReply) message).getReceiverUsername()))
                    showLoginReply();
                break;
            }

            case MERGE_ISLANDS:{
                showMergeIslandsMessage();
                break;
            }

            case MOTHER_NATURE_POSITION:{
                showMotherNaturePosition();
                break;
            }

            case MOVE_STUDENT_FROM_ENTRANCE:{
                if (getUsername().equals(((MoveStudentFromEntranceMessage) message).getReceiverUsername()))
                    askMoveStudentFromEntrance();
                break;
            }

            case TOWER_ON_ISLAND:{
                showMoveTowerOntoIsland();
                break;
            }

            case TOWER_ON_SCHOOLBOARD:{
                showMoveTowerOntoSchoolBoard();
                break;
            }

            case NUMBER_STEPS_MOTHER_NATURE:{
                if (getUsername().equals(((NumberStepsMotherNatureMessage) message).getReceiverUsername()))
                    askNumberStepsMotherNature();
                break;
            }

            case TIE:{
                showTieMessage();
                break;
            }

            case WIZARD_REQUEST:{
                if (getUsername().equals(((WizardRequest) message).getReceiverUsername()))
                    askWizard();
                break;
            }

            case YOUR_TURN:{
                showYourTurnMessage();
                break;
            }




        }

    }

    public void askGameMode(){
        serveOneClient.sendMessage(new GameModeRequest(username));
    }

    public void askNumOfPlayers(){
        serveOneClient.sendMessage(new PlayersNumberRequest(username));
    }

    public void showErrorMessage(ErrorTypeID errorTypeID){
        serveOneClient.sendMessage(new ErrorMessage(errorTypeID, username));
    }

    public void askTowerColor(){
        serveOneClient.sendMessage(message);
    }

    @Override
    public void askUsername() {
        // TODO
    }

    @Override
    public void askAssistantCard() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showChangeInfluenceMessage() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void askCloudTile() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showDisconnectionMessage() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showEmptyBagMessage() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showFillCloudTilesMessage() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showFullLobbyMessage() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showGameInfo() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showGenericMessage() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showInfoActionPhase() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showInfoAssistantCardPlayed() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showLobbyMessage() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showLoginReply() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showMergeIslandsMessage() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showMotherNaturePosition() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void askMoveStudentFromEntrance() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showMoveTowerOntoIsland() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showMoveTowerOntoSchoolBoard() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void askNumberStepsMotherNature() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void askPlayersNumber() {

    }

    @Override
    public void showTieMessage() {
        serveOneClient.sendMessage(message);
    }


    @Override
    public void showWinMessage() {
        serveOneClient.sendMessage(message);
    }


    @Override
    public void showLoseMessage(String winnerUsername) {
        serveOneClient.sendMessage(new LoseMessage(winnerUsername));
    }

    @Override
    public void askWizard() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showYourTurnMessage() {
        serveOneClient.sendMessage(message);
    }

    @Override
    public void showAvailableCharacterCard() {
        serveOneClient.sendMessage(message);
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


    public String getUsername() {
        return username;
    }

}
