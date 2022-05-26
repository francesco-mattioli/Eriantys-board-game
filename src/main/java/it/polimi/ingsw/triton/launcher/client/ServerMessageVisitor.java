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


/**
 * This visitor manages messages sent by the server to establish which view methods to call and to modify the client model.
 */
public class ServerMessageVisitor {
    private final ClientView clientView;

    public ServerMessageVisitor(ClientView clientView) {
        this.clientView = clientView;
    }

    /**
     * This method is not called, but it's necessary to create the other visit methods.
     *
     * @param message the message received.
     */
    public void visit(ServerMessage message) {
        // DEFAULT METHOD, otherwise it does not work
        clientView.showGenericMessage("This string should not be printed");
    }

    /**
     * Modifies the client model setting the username of the player and calls the view method to show the login reply.
     *
     * @param message the last message received.
     */
    public void visit(LoginReply message) {
        clientView.getClientModel().setUsername(message.getReceiverUsername());
        clientView.showLoginReply();
    }

    /**
     * Calls the view method to ask the number of players and the game mode.
     * This method is called only by the first player.
     *
     * @param message the last message received.
     */
    public void visit(PlayersNumberAndGameModeRequest message) {
        clientView.askNumPlayersAndGameMode();
    }

    /**
     * Calls the view method to ask the tower color.
     *
     * @param message the last message received.
     */
    public void visit(TowerColorRequest message) {
        clientView.askTowerColor(message.getChosenTowerColors());
    }

    /**
     * Calls the view method to show the online players.
     *
     * @param message the last message received.
     */
    public void visit(LobbyMessage message) {
        clientView.showLobbyMessage(message.getOnlineNicknames());
    }

    /**
     * Calls the view method to ask the wizard.
     *
     * @param message the last message received.
     */
    public void visit(WizardRequest message) {
        clientView.askWizard(message.getAvailableWizards());
    }

    /**
     * Calls the view method to show a turn message.
     *
     * @param message the last message received.
     */
    public void visit(ChangeTurnMessage message) {
        clientView.getClientModel().resetLastCharacterCardPlayed();
        if (message.getCurrentUsername().equals(clientView.getClientModel().getUsername()))
            clientView.showGenericMessage("It's your turn!");
        else
            clientView.showGenericMessage("It's " + message.getCurrentUsername() + "'s turn!");
    }

    /**
     * Calls the view method to ask the assistant card.
     *
     * @param message the last message received.
     */
    public void visit(AssistantCardRequest message) {
        clientView.askAssistantCard();
    }

    /**
     * If this player is the one who played the card, remove from his/her deck.
     * Otherwise, show the played card and who played it.
     *
     * @param message the last message received.
     */
    public void visit(InfoAssistantCardPlayedMessage message) {
        clientView.getClientModel().setLastAssistantCardPlayed(message.getCurrentPlayerUsername(), message.getAssistantCardPlayed());
        if (message.getCurrentPlayerUsername().equals(clientView.getClientModel().getUsername())) {
            clientView.getClientModel().removeCard(message.getAssistantCardPlayed());
            clientView.showMyInfoAssistantCardPlayed(message.getAssistantCardPlayed());
        } else
            clientView.showInfoAssistantCardPlayed(message.getCurrentPlayerUsername(), message.getAssistantCardPlayed());
    }

    /**
     * Sets the deck in the player's client model.
     *
     * @param message the last message received.
     */
    public void visit(GiveAssistantDeckMessage message) {
        clientView.getClientModel().setAssistantDeck(message.getAssistantDeck());
    }

    /**
     * Sets the updated wallet in the player's client model and shows the new amount of coins in the wallet.
     *
     * @param message the last message received.
     */
    public void visit(UpdateWalletMessage message) {
        if (clientView.getClientModel().getUsername().equals(message.getReceiverUsername())) {
            clientView.getClientModel().setWallet(message.getWallet());
            clientView.showUpdateWallet();
        }
    }

    /**
     * Shows to the players that a new phase of the game is starting.
     *
     * @param message the last message received.
     */
    public void visit(ChangePhaseMessage message) {
        clientView.showChangePhase(message.getGameState());
    }

    /**
     * Calls the view method to ask which student moves from the entrance.
     *
     * @param message the last message received.
     */
    public void visit(MoveStudentFromEntranceMessage message) {
        clientView.askMoveStudentFromEntrance();
    }

    /**
     * Updates the client model with new school board and professors (after moving a student from entrance).
     * After that, it shows the move description to the other players.
     *
     * @param message the last message received.
     */
    public void visit(InfoStudentIntoDiningRoomMessage message) {
        clientView.getClientModel().setSchoolBoard(message.getPlayerUsername(), message.getSchoolBoard());
        clientView.getClientModel().setProfessors(message.getProfessors());
        clientView.showInfoStudentIntoDiningRoom(message.getPlayerUsername(), message.getMoveDescription());
    }

    /**
     * Updates the client model with new school board and island (after moving a student from entrance).
     * After that, it shows the move description to the other players.
     *
     * @param message the last message received.
     */
    public void visit(InfoStudentOntoIslandMessage message) {
        clientView.getClientModel().setIsland(message.getIsland());
        clientView.getClientModel().setSchoolBoard(message.getPlayerUsername(), message.getSchoolBoard());
        clientView.showInfoStudentOntoIsland(message.getPlayerUsername(), message.getMoveDescription());
    }

    /**
     * Updates the client model with the new position of mother nature.
     * After that, it shows the new position of mother nature.
     *
     * @param message the last message received.
     */
    public void visit(MotherNaturePositionMessage message) {
        clientView.getClientModel().setIsland(message.getMotherNaturePosition());
        clientView.getClientModel().setMotherNaturePosition(message.getMotherNaturePosition());
        clientView.showMotherNaturePosition(message.getMotherNaturePosition().getId());
    }

    /**
     * Updates the client model with the new island with entry tiles on it.
     *
     * @param message the last message received.
     */
    public void visit(UpdateIslandWithNoEntryTilesMessage message) {
        clientView.getClientModel().setIsland(message.getIslandToUpdate());
    }

    /**
     * Updates the client model with the new island and the new position of mother nature.
     * After that, it prints all the islands.
     *
     * @param message the last message received.
     */
    public void visit(ChangeInfluenceMessage message) {
        clientView.getClientModel().setIsland(message.getIslandWithNewInfluence());
        clientView.showChangeInfluenceMessage(message.getUsernameDominator(), message.getIslandWithNewInfluence().getId());
    }

    /**
     * Updates the client model with new islands after merging and show the new list of islands.
     *
     * @param message the last message received.
     */
    public void visit(MergeIslandsMessage message) {
        clientView.getClientModel().removeIsland(message.getIslandToDelete());
        clientView.showMergeIslandsMessage(message.getIslandWithMotherNature().getId(), message.getIslandToDelete().getId());
    }

    /**
     * Updates the client model with the new school board and removes the cloud tile selected by the player.
     * After that, it shows the remaining available cloud tiles.
     *
     * @param message the last message received.
     */
    public void visit(InfoChosenCloudTileMessage message) {
        clientView.getClientModel().setSchoolBoard(message.getPlayerUsername(), message.getPlayerSchoolBoard());
        clientView.getClientModel().removeCloudTile(message.getCloudTile());
        clientView.showInfoChosenCloudTile(message.getPlayerUsername(), message.getChoiceDescription());
    }

    /**
     * Calls a view method to ask the player to choose the cloud tile
     *
     * @param message the last message received.
     */
    public void visit(CloudTileRequest message) {
        clientView.askCloudTile();
    }

    /**
     * Updates the client model with new available cloud tiles.
     *
     * @param message the last message received.
     */
    public void visit(CloudTilesInfoMessage message) {
        clientView.getClientModel().setCloudTiles(message.getCloudTiles());
    }

    /**
     * Calls a view method to ask the player to insert the number of steps mother nature has to do.
     *
     * @param message the last message received.
     */
    public void visit(MotherNatureRequest message) {
        clientView.askNumberStepsMotherNature();
    }

    /**
     * Updates the client model with new school board and professors (after moving a student from entrance).
     * After that, it shows the move description to the other players.
     *
     * @param message the last message received.
     */
    public void visit(MoveTowerOntoIslandMessage message) {
        clientView.getClientModel().setIsland(message.getIsland());
        clientView.getClientModel().setSchoolBoard(message.getDominatorUsername(), message.getSchoolBoardDominator());
        clientView.showMoveTowerOntoIsland(message.getIsland().getId());
    }

    /**
     * Updates the client model with updated school board and shows it.
     *
     * @param message the last message received.
     */
    public void visit(MoveTowerOntoSchoolBoardMessage message) {
        clientView.getClientModel().setSchoolBoard(message.getUsernameDominated(), message.getSchoolBoard());
        clientView.showMoveTowerOntoSchoolBoard(message.getUsernameDominated(), message.getSchoolBoard());
    }

    /**
     * Calls a view method to notify the player that the bag is empty.
     *
     * @param message the last message received.
     */
    public void visit(EmptyBagMessage message) {
        clientView.showEmptyBagMessage();
    }

    /**
     * Updates the client model after a player played a character card with updated islands, school boards and available character cards.
     * After that, it shows the card choice description to other players.
     *
     * @param message the last message received.
     */
    public void visit(InfoCharacterCardPlayedMessage message) {
        clientView.getClientModel().setAvailableCharacterCard(message.getCharacterCard());
        clientView.getClientModel().setIslands(message.getUpdatedIslands());
        clientView.getClientModel().setSchoolBoards(message.getUpdatedSchoolBoards());
        clientView.getClientModel().setProfessors(message.getProfessors());
        if (!clientView.getClientModel().getUsername().equals(message.getPlayerUsername()))
            clientView.showGenericMessage(message.getChoiceDescription());
        if (clientView.getClientModel().getUsername().equals(message.getPlayerUsername()))
            clientView.getClientModel().setLastCharacterCardPlayed(message.getCharacterCard());
        clientView.showGameInfo(message.getCharacterCard().getId());
    }

    /**
     * Calls a view method to ask the player to insert the parameters to build the effect of the character card.
     *
     * @param message the last message received.
     */
    public void visit(CharacterCardParameterRequest message) {
        clientView.askCharacterCardParameters(message.getCharacterCardID());
    }

    /**
     * Calls a view method to show the winner player.
     *
     * @param message the last message received.
     */
    public void visit(WinMessage message) {
        if (clientView.getClientModel().getUsername().equals(message.getReceiverUsername()))
            clientView.showWinMessage();
        else clientView.showLoseMessage(message.getReceiverUsername());
    }

    /**
     * Calls a view method to show the players who have tied.
     *
     * @param message the last message received.
     */
    public void visit(TieMessage message) {
        if (message.getPlayers().contains(clientView.getClientModel().getUsername()))
            clientView.showTieMessage();
        else {

            StringBuilder loserMessage = new StringBuilder();
            for (String username : message.getPlayers())
                loserMessage.append(username).append(" ");
            clientView.showGenericMessage(loserMessage + "have tied");
        }
    }

    /**
     * Sets the client model expert mode attribute to false and prints all the information about the game.
     *
     * @param message the last message received.
     */
    public void visit(GameInfoMessage message) {
        clientView.getClientModel().setExpertMode(false);
        setForEveryGameMode(message);
    }

    /**
     * Sets the client model expert mode attribute to true and prints all the information about the expert game.
     *
     * @param message the last message received.
     */
    public void visit(ExpertGameInfoMessage message) {
        clientView.getClientModel().setExpertMode(true);
        clientView.getClientModel().setAvailableCharacterCards(message.getAvailableCharacterCards());
        setForEveryGameMode(message);
    }

    /**
     * Sets all the attributes of client model and show them.
     *
     * @param message the last message received.
     */
    private void setForEveryGameMode(GameInfoMessage message) {
        clientView.getClientModel().setIslands(message.getIslands());
        clientView.getClientModel().setSchoolBoards(message.getSchoolBoards());
        clientView.getClientModel().setCloudTiles(message.getCloudTiles());
        clientView.getClientModel().setMotherNaturePosition(message.getMotherNaturePosition());
        clientView.getClientModel().setProfessors(message.getProfessors());
        clientView.getClientModel().setChosenWizardsPerUsername(message.getChosenWizardsPerUsername());
        clientView.showGameInfo();
    }

    /**
     * Shows a generic string message to the players.
     *
     * @param message the last message received.
     */
    public void visit(GenericMessage message) {
        clientView.showGenericMessage(message.getStringMessage());
    }

    /**
     * Shows a message to the players if the general coin supply is empty.
     *
     * @param message the last message received.
     */
    public void visit(EmptyGeneralCoinSupplyMessage message) {
        if (message.getReceiverUsername().equals(clientView.getClientModel().getUsername()))
            clientView.showGenericMessage("Sorry, you can't get the coin because the general coin supply is empty!");
    }

    /**
     * Shows an error message to the current player. The description of the error is specified in the errorTypeID.
     *
     * @param message the last message received.
     */
    public void visit(ErrorMessage message) {
        if (message.getErrorTypeID().equals(ErrorTypeID.USERNAME_ALREADY_CHOSEN) || message.getErrorTypeID().equals(ErrorTypeID.FORBIDDEN_USERNAME)) {
            clientView.showErrorMessage(message.getErrorTypeID());
            clientView.askUsername();
        } else
            clientView.showErrorMessage(message.getErrorTypeID());
    }

    /**
     * Shows to the remaining players that a player has disconnected.
     *
     * @param message the last message received.
     */
    public void visit(DisconnectionMessage message) {
        clientView.showDisconnectionMessage();
    }
}
