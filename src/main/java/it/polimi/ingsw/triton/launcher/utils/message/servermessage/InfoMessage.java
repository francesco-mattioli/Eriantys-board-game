package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;

public abstract class InfoMessage extends ServerMessage{

    public void accept(InfoMessageVisitor visitor){
        visitor.visit(this);
    }
}
