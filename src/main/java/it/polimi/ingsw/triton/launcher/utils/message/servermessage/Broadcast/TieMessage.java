package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.List;

public class TieMessage extends BroadcastServerMessage{
    private final List<String> players;
    public TieMessage(List<String> players) {
        this.players = players;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
