package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;

public class MessageVisitor {

    private ServeOneClient serveOneClient;
    private String username;

   public MessageVisitor(ServeOneClient serveOneClient, String username){
       this.serveOneClient = serveOneClient;
       this.username = username;
   }

    public void visit(Message message){
        serveOneClient.sendMessage(message);
    }

    public void visit(BroadcastServerMessage broadcastServerMessage){
       serveOneClient.sendMessage(broadcastServerMessage);
    }

    /*public void visit(ServerMessage serverMessage){
       if (serverMessage.getReceiverUsername().equals(username))
            serveOneClient.sendMessage(serverMessage);
    }

    public void visit(WinMessage winMessage){
       if (winMessage.getReceiverUsername().equals(username))
            serveOneClient.sendMessage(winMessage);
       else serveOneClient.sendMessage(new LoseMessage(winMessage.getReceiverUsername()));
    }
    */

}
