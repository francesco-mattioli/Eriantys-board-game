package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the players which student is moved from the entrance to the dining room of the player.
 */
public class InfoStudentIntoDiningRoomMessage extends BroadcastServerMessage{
    private final String playerUsername;
    private final SchoolBoard modifiedSchoolBoard;
    public InfoStudentIntoDiningRoomMessage(String playerUsername, SchoolBoard modifiedSchoolBoard) {
        super(MessageType.INFO_STUDENT_INTO_DINING_ROOM);
        this.playerUsername = playerUsername;
        this.modifiedSchoolBoard = modifiedSchoolBoard;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
