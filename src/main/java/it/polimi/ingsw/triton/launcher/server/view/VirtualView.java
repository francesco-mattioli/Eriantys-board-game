package it.polimi.ingsw.triton.launcher.server.view;

import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.LoginReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.utils.message.*;
import it.polimi.ingsw.triton.launcher.utils.View;

import java.util.ArrayList;

public class VirtualView extends Observable<Message> implements View, Observer<ServerMessage> {
    private ServeOneClient serveOneClient;
    private String username;
    private Message lastMessage;
    private Message lastCharacterCardMessage;

    public VirtualView(ServeOneClient serveOneClient, String username){
        this.serveOneClient = serveOneClient;
        this.username = username;
    }

    @Override
    public void update(ServerMessage message) {
        message.accept(new BroadcastMessageVisitor(serveOneClient,this.getUsername()));
    }

    public void askGameMode(){
        serveOneClient.sendMessage(new GameModeRequest());
    }

    public void askNumOfPlayers(){
        serveOneClient.sendMessage(new PlayersNumberRequest());
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
    public void askCloudTile() {
        CloudTileRequest requestMessage = new CloudTileRequest();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    @Override
    public void showLoginReply() {
        serveOneClient.sendMessage(new LoginReply(username));
    }

    @Override
    public void askMoveStudentFromEntrance() {
        MoveStudentFromEntranceMessage requestMessage = new MoveStudentFromEntranceMessage();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    @Override
    public void askNumberStepsMotherNature() {
        MotherNatureRequest requestMessage = new MotherNatureRequest();
        serveOneClient.sendMessage(requestMessage);
        lastMessage = requestMessage;
    }

    @Override
    public void askTowerColor(boolean[] towerColorChosen) {
        serveOneClient.sendMessage(new TowerColorRequest(towerColorChosen));
    }

    @Override
    public void askWizard(ArrayList<Wizard> wizards) {
        serveOneClient.sendMessage(new WizardRequest(wizards));
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
