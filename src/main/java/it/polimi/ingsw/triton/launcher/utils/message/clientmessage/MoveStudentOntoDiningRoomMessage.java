package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class MoveStudentOntoDiningRoomMessage extends ClientMessage {
    private final Color student;

    public Color getStudent() {
        return student;
    }

    public MoveStudentOntoDiningRoomMessage(String senderUsername, Color student) {
        super(MessageType.MOVE_STUDENT_ONTO_DININGROOM, senderUsername);
        this.student = student;
    }
}
