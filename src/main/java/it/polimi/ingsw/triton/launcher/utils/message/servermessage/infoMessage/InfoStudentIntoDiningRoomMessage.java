package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message communicates to the players which student is moved from the entrance to the dining room of the player.
 */
public class InfoStudentIntoDiningRoomMessage extends InfoMessage {
    private final String playerUsername;
    private final SchoolBoard schoolBoard;
    private final String[] professors;
    private final String moveDescription;

    public InfoStudentIntoDiningRoomMessage(String playerUsername, SchoolBoard schoolBoard, String[] professors, String moveDescription) {
        this.playerUsername = playerUsername;
        this.schoolBoard = schoolBoard;
        this.professors = professors;
        this.moveDescription = moveDescription;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public SchoolBoard getSchoolBoard() {
        return schoolBoard;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }

    public String getMoveDescription() {
        return moveDescription;
    }
}
