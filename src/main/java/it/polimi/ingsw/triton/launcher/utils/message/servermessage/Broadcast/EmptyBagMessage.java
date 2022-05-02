package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * Communicates to the players that the bag is empty and this round will be the last one.
 */
public class EmptyBagMessage extends BroadcastServerMessage{

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
