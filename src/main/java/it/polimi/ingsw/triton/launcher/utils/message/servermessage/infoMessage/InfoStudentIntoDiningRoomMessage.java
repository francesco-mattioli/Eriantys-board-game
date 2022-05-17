package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate that the current player has moved one of his students
 * from his entrance to his dining room.
 * The parameter moveDescription is used on client-side to tell to other players
 * which student is moved to the current player's dining room.
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

    public String[] getProfessors() {
        return professors;
    }
}
