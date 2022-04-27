package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks to the player which student wants to move.
 */
public class MoveStudentFromEntranceMessage extends ServerMessage{
    private int[] studentIntoEntrance;
    public MoveStudentFromEntranceMessage(String receiverUsername, int[] studentIntoEntrance) {
        super(MessageType.MOVE_STUDENT_FROM_ENTRANCE, receiverUsername);
        this.studentIntoEntrance = studentIntoEntrance;
    }

    public int[] getStudentIntoEntrance() {
        return studentIntoEntrance;
    }
}
