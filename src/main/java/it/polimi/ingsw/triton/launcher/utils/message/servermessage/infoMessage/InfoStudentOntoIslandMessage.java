package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

public class InfoStudentOntoIslandMessage extends InfoMessage {

    private final String playerUsername;
    private final SchoolBoard modifiedSchoolBoard;
    private final Island modifiedIsland;

    public String getPlayerUsername() {
        return playerUsername;
    }

    public SchoolBoard getModifiedSchoolBoard() {
        return modifiedSchoolBoard;
    }

    public Island getModifiedIsland() {
        return modifiedIsland;
    }

    public InfoStudentOntoIslandMessage(String username, SchoolBoard modifiedSchoolBoard, Island modifiedIsland) {
        this.playerUsername = username;
        this.modifiedSchoolBoard = modifiedSchoolBoard;
        this.modifiedIsland = modifiedIsland;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
