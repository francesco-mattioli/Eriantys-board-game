package it.polimi.ingsw.triton.launcher.server.view;

import it.polimi.ingsw.triton.launcher.client.view.View;
import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ErrorMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.GenericMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.LoginReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;

import java.util.List;

/**
 * This class is used by the server to call view methods.
 * Each method of the virtual view allows the server to send a message to the client that has to reply
 * to the request.
 */
public class VirtualView extends Observable<ClientMessage> implements View, Observer<InfoMessage> {
    private final ServeOneClient serveOneClient;
    private final String username;
    private Message lastMessage;
    private Message lastCharacterCardMessage;

    public VirtualView(ServeOneClient serveOneClient, String username) {
        this.serveOneClient = serveOneClient;
        this.username = username;
    }


    //--------------------------------------------- UTILITY METHODS -----------------------------------------------------

    /**
     * Receives a message by notification of ServeOneClient.
     * Manages the message using Visitor Patter.
     *
     * @param message received from the Client
     */
    @Override
    public void update(InfoMessage message) {
        message.accept(new InfoMessageVisitor(serveOneClient, username));
    }

    /**
     * Resends a message which was saved in lastMessage attribute.
     */
    public void reSendLastMessage() {
        serveOneClient.sendMessage(lastMessage);
    }

    /**
     * Resends a message which was saved in lastCharacterCardMessage attribute.
     */
    public void reSendLastCharacterCardMessage() {
        serveOneClient.sendMessage(lastCharacterCardMessage);
    }
    //------------------------------------------------------------------------------------------------------------------

    //--------------------------------------------- ASK METHODS --------------------------------------------------------

    /**
     * Sends a message to ask the first player how many player wants in the game and the game mode.
     */
    @Override
    public void askNumPlayersAndGameMode() {
        serveOneClient.sendMessage(new PlayersNumberAndGameModeRequest());
    }

    /**
     * Sends a message to the current player to ask which assistant card wants to play.
     * It also saves this message in lastMessage in order to repeat the request if the player chooses
     * to play a character card.
     */
    @Override
    public void askAssistantCard() {
        AssistantCardRequest requestMessage = new AssistantCardRequest();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    /**
     * Sends a message to the current player to ask which cloud tile wants.
     * It also saves this message in lastMessage in order to repeat the request if the player chooses
     * to play a character card.
     */
    @Override
    public void askCloudTile() {
        CloudTileRequest requestMessage = new CloudTileRequest();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    /**
     * Sends a message to the current player to ask which student wants to move from his entrance.
     * It also saves this message in lastMessage in order to repeat the request if the player chooses
     * to play a character card.
     */
    @Override
    public void askMoveStudentFromEntrance() {
        MoveStudentFromEntranceMessage requestMessage = new MoveStudentFromEntranceMessage();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    /**
     * Sends a message to the current player to ask how many steps mother nature has to do.
     * It also saves this message in lastMessage in order to repeat the request if the player chooses
     * to play a character card.
     */
    @Override
    public void askNumberStepsMotherNature() {
        MotherNatureRequest requestMessage = new MotherNatureRequest();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    /**
     * Sends a message to the current player to ask which tower color wants.
     */
    @Override
    public void askTowerColor(boolean[] towerColorChosen) {
        serveOneClient.sendMessage(new TowerColorRequest(towerColorChosen));
    }

    /**
     * Sends a message to the current player to ask which wizard wants.
     */
    @Override
    public void askWizard(List<Wizard> wizards) {
        serveOneClient.sendMessage(new WizardRequest(wizards));
    }

    /**
     * Sends a message to the current player to ask which cloud tile wants.
     * It also saves this message in lastCharacterCardMessage in order to repeat the request if an error occurs during
     * the execution.
     */
    @Override
    public void askCharacterCardParameters(int id) {
        CharacterCardParameterRequest requestMessage = new CharacterCardParameterRequest(id);
        serveOneClient.sendMessage(requestMessage);
        lastCharacterCardMessage = requestMessage;
    }
    //------------------------------------------------------------------------------------------------------------------


    //------------------------------------------- SHOW METHODS ---------------------------------------------------------

    /**
     * Sends a message to the current player to communicate that an error occurs during the execution.
     */
    @Override
    public void showErrorMessage(ErrorTypeID errorTypeID) {
        serveOneClient.sendMessage(new ErrorMessage(errorTypeID));
    }

    /**
     * Sends a message to the current player to communicate the correct login.
     */
    @Override
    public void showLoginReply() {
        serveOneClient.sendMessage(new LoginReply(username));
    }

    /**
     * Sends a message to the player to print the message passed as a parameter.
     *
     * @param message the string to send.
     */
    public void showGenericMessage(String message) {
        serveOneClient.sendMessage(new GenericMessage(message));
    }
    //------------------------------------------------------------------------------------------------------------------


    //--------------------------------------------- GETTER METHODS -----------------------------------------------------
    public String getUsername() {
        return username;
    }

    public ServeOneClient getServeOneClient() {
        return serveOneClient;
    }
    //------------------------------------------------------------------------------------------------------------------

}
