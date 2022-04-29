package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This message asks to the player which student wants to move.
 */
public class MoveStudentFromEntranceMessage extends ServerMessage {
    public MoveStudentFromEntranceMessage(String receiverUsername) {
        super(MessageType.MOVE_STUDENT_FROM_ENTRANCE, receiverUsername);
    }
}
