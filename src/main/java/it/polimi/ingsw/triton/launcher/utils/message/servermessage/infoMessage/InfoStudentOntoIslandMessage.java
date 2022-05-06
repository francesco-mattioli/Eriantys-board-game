package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

public class InfoStudentOntoIslandMessage extends InfoMessage {

    private final String playerUsername;
    private final SchoolBoard schoolBoard;
    private final Island island;
    private final String[] professors;

    public String getPlayerUsername() {
        return playerUsername;
    }

    public SchoolBoard getSchoolBoard() {
        return schoolBoard;
    }

    public Island getIsland() {
        return island;
    }

    public InfoStudentOntoIslandMessage(String username, SchoolBoard schoolBoard, Island island, String[] professors) {
        this.playerUsername = username;
        this.schoolBoard = schoolBoard;
        this.island = island;
        this.professors = professors;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
