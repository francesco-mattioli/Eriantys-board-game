package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.network.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class TieMessage extends BroadcastServerMessage{
    private final List<String> players;

    public TieMessage(List<String> players) {
        super(MessageType.TIE);
        this.players = players;
    }
}
