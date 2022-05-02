package it.polimi.ingsw.triton.launcher.utils.message;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.BroadcastMessageVisitor;

import java.io.Serializable;

public abstract class Message implements Serializable {

     public void accept(BroadcastMessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }

    public abstract void accept(ClientVisitor messageVisitor);

}
