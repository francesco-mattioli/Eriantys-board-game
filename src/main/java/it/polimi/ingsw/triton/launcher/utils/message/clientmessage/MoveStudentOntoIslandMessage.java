package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class MoveStudentOntoIslandMessage extends ClientMessage{
    public MoveStudentOntoIslandMessage(String senderUsername) {
        super(MessageType.MOVE_STUDENT_ONTO_ISLAND, senderUsername);
    }
}
