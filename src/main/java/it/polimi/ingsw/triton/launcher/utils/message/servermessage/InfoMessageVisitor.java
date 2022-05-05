package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;

public class InfoMessageVisitor {
    private final ServeOneClient serveOneClient;
    private final String username;

    public InfoMessageVisitor(ServeOneClient serveOneClient, String username) {
        this.serveOneClient = serveOneClient;
        this.username = username;
    }

    public void visit(InfoMessage message){
        serveOneClient.sendMessage(message);
    }

    public void visit(InfoWithReceiverMessage message){
        if(message.receiverUsername.equals(username))
            serveOneClient.sendMessage(message);
    }
}
