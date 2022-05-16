package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

public abstract class AskMessage extends ServerMessage {

    public abstract void accept(ServerMessageVisitor messageVisitor);
}
