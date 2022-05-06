package it.polimi.ingsw.triton.launcher.client;

import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.GiveAssistantDeckMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.LoginReply;

public class ServerMessageVisitor {
    private final ClientView clientView;

    public ServerMessageVisitor(ClientView clientView) {
        this.clientView = clientView;
    }


    public void visit(ServerMessage message) {
        // DEFAULT METHOD, otherwise it does not work
        clientView.showGenericMessage("This string should not be printed");
    }

    public void visit(LoginReply message) {
        clientView.showGenericMessage("Username accepted");
    }

    public void visit(GameModeRequest message) {
        clientView.askGameMode();
    }

    public void visit(PlayersNumberRequest message) {
        clientView.askNumOfPlayers();
    }

    public void visit(TowerColorRequest message) {
        clientView.askTowerColor(message.getChosenTowerColors());
    }

    public void visit(LobbyMessage message) {
        clientView.showLobbyMessage(message.getOnlineNicknames(), message.getMaxNumberPlayers());
    }

    public void visit(WizardRequest message) {
        clientView.askWizard(message.getAvailableWizards());
    }

    public void visit(ChangeTurnMessage message){
        if(message.getCurrentUsername().equals(clientView.getClientModel().getUsername()))
            clientView.showGenericMessage("It's your turn!");
        else
            clientView.showGenericMessage("It's "+message.getCurrentUsername()+"'s turn!");
    }

    public void visit(AssistantCardRequest message) {
        clientView.askAssistantCard();
    }

    /**
     * If this player is the one who played the card, remove from his/her deck.
     * Otherwise, show the played card and who played it.
     */
    public void visit(InfoAssistantCardPlayedMessage message) {
        if (message.getCurrentPlayerUsername().equals(clientView.getClientModel().getUsername())) {
            clientView.getClientModel().setLastAssistantCardPlayed(message.getAssistantCardPlayed());
            clientView.getClientModel().getAssistantDeck().getAssistantDeck().remove(message.getAssistantCardPlayed());
        }
        else
            clientView.showInfoAssistantCardPlayed(message.getCurrentPlayerUsername(), message.getAssistantCardPlayed());
    }

    public void visit(GiveAssistantDeckMessage message) {
        clientView.getClientModel().setAssistantDeck(message.getAssistantDeck());
    }

    public void visit(MoveStudentFromEntranceMessage message) {
        clientView.askMoveStudentFromEntrance();
    }

    public void visit(InfoStudentIntoDiningRoomMessage message) {
        clientView.getClientModel().setSchoolBoard(message.getPlayerUsername(), message.getSchoolBoard());
    }

    public void visit(InfoStudentOntoIslandMessage message) {
        clientView.getClientModel().setIsland(message.getIsland());
    }

    public void visit(MotherNaturePositionMessage message) {
        clientView.getClientModel().setMotherNaturePosition(message.getMotherNaturePosition());
        clientView.showMotherNaturePosition(message.getMotherNaturePosition());
    }

    public void visit(ChangeInfluenceMessage message){
        clientView.getClientModel().setIsland(message.getIslandWithNewInfluence());
        clientView.showChangeInfluenceMessage(message.getIslandWithNewInfluence(), message.getUsernameDominator());
    }

    public void visit(MergeIslandsMessage message){
        clientView.getClientModel().setIsland(message.getIslandWithMotherNature());
        clientView.getClientModel().setMotherNaturePosition(message.getIslandWithMotherNature());
        clientView.getClientModel().removeIsland(message.getIslandToDelete());
        clientView.showMergeIslandsMessage(message.getIslandWithMotherNature().getId(), message.getIslandToDelete().getId());
    }

    public void visit(InfoChosenCloudTileMessage message) {
        clientView.getClientModel().setSchoolBoard(message.getPlayerUsername(), message.getPlayerSchoolBoard());
        clientView.getClientModel().setCloudTile(message.getCloudTile());
    }

    public void visit(CloudTileRequest message) {
        clientView.askCloudTile();
    }

    public void visit(CloudTilesInfoMessage message) {
        clientView.getClientModel().setCloudTiles(message.getCloudTiles());
    }

    public void visit(GenericMessage message) {
        clientView.showGenericMessage(message.getStringMessage());
    }

    public void visit(GameInfoMessage message) {
        clientView.showGameInfo(message.getAvailableCharacterCards(), message.getIslands(), message.getSchoolBoards(), message.getCloudTiles(), message.getMotherNaturePosition());
    }



    public void visit(MotherNatureRequest message) {
        clientView.askNumberStepsMotherNature();
    }

    public void visit(EmptyBagMessage message){
        clientView.showEmptyBagMessage();
    }




}
