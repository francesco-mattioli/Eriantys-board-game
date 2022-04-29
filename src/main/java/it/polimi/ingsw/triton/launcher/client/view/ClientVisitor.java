package it.polimi.ingsw.triton.launcher.client.view;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.GenericMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.LobbyMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.GameModeRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.PlayersNumberRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.TowerColorRequest;

public class ClientVisitor {
    private final ClientView clientView;

    public ClientVisitor(ClientView clientView){
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
        clientView.askTowerColor(message.getAvailableTowerColors());
    }

    public void visit(LobbyMessage message){
        clientView.showLobbyMessage(message.getOnlineNicknames(),message.getMaxNumberPlayers());
    }

    public void visit(GenericMessage message){
        clientView.showGenericMessage(message.getMessage());
    }



}