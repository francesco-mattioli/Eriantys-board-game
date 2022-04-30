package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class MoveStudentOntoDiningRoomMessage extends ClientMessage {
    public MoveStudentOntoDiningRoomMessage(String senderUsername) {
        super(MessageType.MOVE_STUDENT_ONTO_DININGROOM, senderUsername);
    }
}
