package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks to the player which student wants to move.
 */
public class MoveStudentFromEntranceMessage extends ServerMessage{
    public MoveStudentFromEntranceMessage(String receiverUsername) {
        super(MessageType.MOVE_STUDENT_FROM_ENTRANCE, receiverUsername);
    }
}
