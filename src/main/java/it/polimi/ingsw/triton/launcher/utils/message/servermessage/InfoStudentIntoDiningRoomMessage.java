package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the players which student is moved from the entrance to the dining room of the player.
 */
public class InfoStudentIntoDiningRoomMessage extends BroadcastServerMessage{
    private final String playerUsername;
    public InfoStudentIntoDiningRoomMessage(String playerUsername) {
        super(MessageType.INFO_STUDENT_INTO_DINING_ROOM);
        this.playerUsername = playerUsername;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }
}
