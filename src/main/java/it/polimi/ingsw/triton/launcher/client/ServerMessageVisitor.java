package it.polimi.ingsw.triton.launcher.client;

import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ErrorMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.EmptyGeneralCoinSupplyMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.GiveAssistantDeckMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.LoginReply;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.UpdateWalletMessage;


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
        clientView.getClientModel().setUsername(message.getReceiverUsername());
        clientView.showGenericMessage("Username accepted! Welcome to Eriantys " + message.getReceiverUsername() + "!");
    }

    public void visit(PlayersNumberAndGameModeRequest message) {
        clientView.askNumPlayersAndGameMode();
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
            clientView.getClientModel().removeCard(message.getAssistantCardPlayed());
        }
        else
            clientView.showInfoAssistantCardPlayed(message.getCurrentPlayerUsername(), message.getAssistantCardPlayed());
    }

    public void visit(GiveAssistantDeckMessage message) {
        clientView.getClientModel().setAssistantDeck(message.getAssistantDeck());
    }

    public void visit(UpdateWalletMessage message){
        if(clientView.getClientModel().getUsername().equals(message.getReceiverUsername())) {
            clientView.getClientModel().setWallet(message.getWallet());
            clientView.showGenericMessage(clientView.getClientModel().printWallet());
        }
    }

    public void visit(ChangePhaseMessage message){
        clientView.showChangePhase(message.getGameState());
    }

    public void visit(MoveStudentFromEntranceMessage message) {
        clientView.askMoveStudentFromEntrance();
    }

    public void visit(InfoStudentIntoDiningRoomMessage message) {
        clientView.getClientModel().setSchoolBoard(message.getPlayerUsername(), message.getSchoolBoard());
        clientView.getClientModel().setProfessors(message.getProfessors());
        if(!clientView.getClientModel().getUsername().equals(message.getPlayerUsername()))
            clientView.showGenericMessage(message.getMoveDescription());
    }

    public void visit(InfoStudentOntoIslandMessage message) {
        clientView.getClientModel().setIsland(message.getIsland());
        clientView.getClientModel().setSchoolBoard(message.getPlayerUsername(), message.getSchoolBoard());
        if(!clientView.getClientModel().getUsername().equals(message.getPlayerUsername()))
            clientView.showGenericMessage(message.getMoveDescription());
    }

    public void visit(MotherNaturePositionMessage message) {
        clientView.getClientModel().setMotherNaturePosition(message.getMotherNaturePosition());
        clientView.showGenericMessage("Mother nature has been moved.\nMother nature is on the island: " + message.getMotherNaturePosition().getId());
    }

    public void visit(UpdateIslandWithNoEntryTilesMessage message){
        clientView.getClientModel().setIsland(message.getIslandToUpdate());
    }


    public void visit(ChangeInfluenceMessage message){
        clientView.getClientModel().setIsland(message.getIslandWithNewInfluence());
        clientView.getClientModel().setMotherNaturePosition(message.getIslandWithNewInfluence());
        if(message.getUsernameDominator().equals(clientView.getClientModel().getUsername()))
            clientView.showGenericMessage("You are the new dominator of the island " + message.getIslandWithNewInfluence().getId());
        else
            clientView.showGenericMessage("The island " + message.getIslandWithNewInfluence().getId() + " has a new dominator. " + "The new dominator is: " + message.getUsernameDominator());
    }

    public void visit(MergeIslandsMessage message){
        clientView.getClientModel().setIsland(message.getIslandWithMotherNature());
        clientView.getClientModel().setMotherNaturePosition(message.getIslandWithMotherNature());
        clientView.getClientModel().removeIsland(message.getIslandToDelete());
        clientView.showGenericMessage("The island " + message.getIslandWithMotherNature().getId() + " is now merged with the island " + message.getIslandToDelete().getId());
        clientView.showGenericMessage("These are the remaining islands: "+clientView.getClientModel().printIslands());
    }

    public void visit(InfoChosenCloudTileMessage message) {
        clientView.getClientModel().setSchoolBoard(message.getPlayerUsername(), message.getPlayerSchoolBoard());
        clientView.getClientModel().removeCloudTile(message.getCloudTile());
        if(!clientView.getClientModel().getUsername().equals(message.getPlayerUsername()))
            clientView.showGenericMessage(message.getChoiceDescription());
    }

    public void visit(CloudTileRequest message) {
        clientView.askCloudTile();
    }

    public void visit(CloudTilesInfoMessage message) {
        clientView.getClientModel().setCloudTiles(message.getCloudTiles());
    }

    public void visit(MotherNatureRequest message) {
        clientView.askNumberStepsMotherNature();
    }

    public void visit(MoveTowerOntoIslandMessage message){
        clientView.getClientModel().setIsland(message.getIsland());
        clientView.getClientModel().setSchoolBoard(message.getDominatorUsername(), message.getSchoolBoardDominator());
        clientView.showMoveTowerOntoIsland(message.getIsland().getId());
    }

    public void visit(MoveTowerOntoSchoolBoardMessage message){
        clientView.getClientModel().setSchoolBoard(message.getUsernameDominated(), message.getSchoolBoard());
        clientView.showMoveTowerOntoSchoolBoard(message.getUsernameDominated(), message.getSchoolBoard());
    }

    public void visit(EmptyBagMessage message){
        clientView.showEmptyBagMessage();
    }

    public void visit(InfoCharacterCardPlayedMessage message){
        clientView.getClientModel().setAvailableCharacterCard(message.getCharacterCard());
        clientView.getClientModel().setIslands(message.getUpdatedIslands());
        clientView.getClientModel().setSchoolBoards(message.getUpdatedSchoolBoards());
        if(!clientView.getClientModel().getUsername().equals(message.getPlayerUsername()))
            clientView.showGenericMessage(message.getChoiceDescription());
        System.out.println(clientView.getClientModel().toString());
    }

    public void visit(CharacterCardParameterRequest message){
        clientView.askCharacterCardParameters(message.getCharacterCardID());
    }

    public void visit(WinMessage message){
        if(clientView.getClientModel().getUsername().equals(message.getReceiverUsername()))
            clientView.showWinMessage();
        else clientView.showLoseMessage(message.getReceiverUsername());
    }

    public void visit(TieMessage message){
        if(message.getPlayers().contains(clientView.getClientModel().getUsername()))
            clientView.showTieMessage();
        else{
            String loserMessage = "";
            for(String username: message.getPlayers())
                loserMessage += username + " ";
            clientView.showGenericMessage(loserMessage + "have tied");
        }
    }

    public void visit(GameInfoMessage message) {
        clientView.getClientModel().setExpertMode(false);
        clientView.getClientModel().setIslands(message.getIslands());
        clientView.getClientModel().setSchoolBoards(message.getSchoolBoards());
        clientView.getClientModel().setCloudTiles(message.getCloudTiles());
        clientView.getClientModel().setMotherNaturePosition(message.getMotherNaturePosition());
        clientView.getClientModel().setProfessors(message.getProfessors());
        clientView.showGameInfo(message.getIslands(), message.getSchoolBoards(), message.getCloudTiles(), message.getMotherNaturePosition(), message.getProfessors());
    }

    public void visit(ExpertGameInfoMessage message){
        clientView.getClientModel().setExpertMode(true);
        clientView.getClientModel().setAvailableCharacterCards(message.getAvailableCharacterCards());
        clientView.getClientModel().setIslands(message.getIslands());
        clientView.getClientModel().setSchoolBoards(message.getSchoolBoards());
        clientView.getClientModel().setCloudTiles(message.getCloudTiles());
        clientView.getClientModel().setMotherNaturePosition(message.getMotherNaturePosition());
        clientView.getClientModel().setProfessors(message.getProfessors());
        clientView.showGameInfo(message.getAvailableCharacterCards(), message.getIslands(), message.getSchoolBoards(), message.getCloudTiles(), message.getMotherNaturePosition(), message.getProfessors());

    }

    public void visit(GenericMessage message) {
        clientView.showGenericMessage(message.getStringMessage());
    }

    public void visit(EmptyGeneralCoinSupplyMessage message){
        if(message.getReceiverUsername().equals(clientView.getClientModel().getUsername()))
            clientView.showGenericMessage("Sorry, you can't get the coin because the general coin supply is empty!");
    }

    public void visit(ErrorMessage message){
        if(message.getErrorTypeID().equals(ErrorTypeID.USERNAME_ALREADY_CHOSEN)){
            clientView.showErrorMessage(message.getErrorTypeID());
            clientView.askUsername();
        }
        else
            clientView.showErrorMessage(message.getErrorTypeID());
    }

    public void visit(DisconnectionMessage message){
        clientView.showDisconnectionMessage();
    }


}
