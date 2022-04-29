package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates the win to the winner player.
 */
public class WinMessage extends ServerMessage {
    public WinMessage(String usernameWinner) {
        super(MessageType.WIN, usernameWinner);
    }

    public void accept(MessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }
}
