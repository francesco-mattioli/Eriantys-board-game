package it.polimi.ingsw.triton.launcher.client;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.GameInfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.GenericMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.LobbyMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.*;

public class ServerMessageVisitor {
    private final ClientView clientView;

    public ServerMessageVisitor(ClientView clientView){
        this.clientView=clientView;
    }


    public void visit(BroadcastServerMessage message){
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
        clientView.askPlayersNumber();
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

    public void visit(GiveAssistantDeckMessage message){
        // NOT SURE
        clientView.getClientModel().setAssistantDeck(message.getAssistantDeck());
    }

    public void visit(GenericMessage message){
        clientView.showGenericMessage(message.getStringMessage());
    }

    public void visit(GameInfoMessage message){
        clientView.showGameInfo(message.getAvailableCharacterCards(),message.getIslands(),message.getSchoolBoards(),message.getCloudTiles());
    }



}
