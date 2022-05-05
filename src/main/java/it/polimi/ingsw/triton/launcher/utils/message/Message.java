package it.polimi.ingsw.triton.launcher.utils.message;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.BroadcastMessageVisitor;

import java.io.Serializable;

public interface Message extends Serializable {

    public void accept(BroadcastMessageVisitor messageVisitor);

    public void accept(ServerMessageVisitor messageVisitor);

}
