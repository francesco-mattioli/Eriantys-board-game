package it.polimi.ingsw.triton.launcher.utils.message;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;

import java.io.Serializable;

public interface Message extends Serializable {

    public void accept(ServerMessageVisitor messageVisitor);

}
