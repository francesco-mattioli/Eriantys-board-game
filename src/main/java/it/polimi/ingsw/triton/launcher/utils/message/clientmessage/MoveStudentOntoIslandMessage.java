package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class MoveStudentOntoIslandMessage extends ClientMessage{
    private final int islandID;
    private final Color student;

    public int getIslandID() {
        return islandID;
    }

    public Color getStudent() {
        return student;
    }

    public MoveStudentOntoIslandMessage(String senderUsername, int islandID, Color student) {
        super(MessageType.MOVE_STUDENT_ONTO_ISLAND, senderUsername);
        this.islandID = islandID;
        this.student = student;
    }
}
