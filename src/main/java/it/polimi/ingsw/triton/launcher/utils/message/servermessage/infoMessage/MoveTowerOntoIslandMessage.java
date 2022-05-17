package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate to the players that one player has moved his towers onto an island.
 */
public class MoveTowerOntoIslandMessage extends InfoMessage {
    private final Island island;
    private final String dominatorUsername;
    private final SchoolBoard schoolBoardDominator;
    public MoveTowerOntoIslandMessage(Island island, String dominatorUsername, SchoolBoard schoolBoardDominator) {
        this.island = island;
        this.dominatorUsername = dominatorUsername;
        this.schoolBoardDominator = schoolBoardDominator;
    }

    public Island getIsland() {
        return island;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }

    public String getDominatorUsername() {
        return dominatorUsername;
    }

    public SchoolBoard getSchoolBoardDominator() {
        return schoolBoardDominator;
    }
}
