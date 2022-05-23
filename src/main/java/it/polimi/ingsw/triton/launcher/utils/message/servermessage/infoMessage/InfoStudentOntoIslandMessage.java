package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate that the current player has moved
 * one of his students onto an island.
 * The parameter moveDescription is used on client-side to tell to other players
 * which student is moved and the id of the island.
 */
public class InfoStudentOntoIslandMessage extends InfoMessage {

    private final String playerUsername;
    private final SchoolBoard schoolBoard;
    private final Island island;
    private final String moveDescription;

    public InfoStudentOntoIslandMessage(String username, SchoolBoard schoolBoard, Island island, String moveDescription) {
        this.playerUsername = username;
        this.schoolBoard = schoolBoard;
        this.island = island;
        this.moveDescription = moveDescription;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public SchoolBoard getSchoolBoard() {
        return schoolBoard;
    }

    public Island getIsland() {
        return island;
    }

    public String getMoveDescription() {
        return moveDescription;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
