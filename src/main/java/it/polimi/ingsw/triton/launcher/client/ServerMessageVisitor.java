package it.polimi.ingsw.triton.launcher.client;

import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.GiveAssistantDeckMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.LoginReply;

public class ServerMessageVisitor {
    private final ClientView clientView;

    public ServerMessageVisitor(ClientView clientView){
        this.clientView=clientView;
    }


    public void visit(ServerMessage message){
        // DEFAULT METHOD, otherwise it does not work
        clientView.showGenericMessage("GRPOW");
    }

    public void visit(LoginReply message){
        clientView.showGenericMessage("Username accepted");
    }

    public void visit(GameModeRequest message){
        clientView.askGameMode();
    }

    public void visit(PlayersNumberRequest message){
        clientView.askNumOfPlayers();
    }

    public void visit(TowerColorRequest message){
        clientView.askTowerColor(message.getChosenTowerColors());
    }

    public void visit(LobbyMessage message){
        clientView.showLobbyMessage(message.getOnlineNicknames(),message.getMaxNumberPlayers());
    }

    public void visit(WizardRequest message){
        clientView.askWizard(message.getAvailableWizards());
    }

    public void visit(AssistantCardRequest message){
        clientView.askAssistantCard();
    }

    public void visit(InfoAssistantCardPlayedMessage message){
        clientView.showInfoAssistantCardPlayed(message.getCurrentPlayerUsername(),message.getAssistantCardPlayed());
    }

    public void visit(GiveAssistantDeckMessage message){
        clientView.getClientModel().setAssistantDeck(message.getAssistantDeck());
    }

    public void visit(MoveStudentFromEntranceMessage message){
        clientView.askMoveStudentFromEntrance();
    }

    public void visit(GenericMessage message){
        clientView.showGenericMessage(message.getStringMessage());
    }

    public void visit(GameInfoMessage message){
        clientView.showGameInfo(message.getAvailableCharacterCards(),message.getIslands(),message.getSchoolBoards(),message.getCloudTiles());
    }

    public void visit(CloudTileRequest message){
        clientView.askCloudTile();
    }

    public void visit(MotherNatureRequest message){
        clientView.askNumberStepsMotherNature();
    }



}
