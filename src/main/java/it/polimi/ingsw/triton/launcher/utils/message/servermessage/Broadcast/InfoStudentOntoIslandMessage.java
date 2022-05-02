package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class InfoStudentOntoIslandMessage extends BroadcastServerMessage{

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
        super(MessageType.INFO_STUDENT_ONTO_ISLAND);
        this.playerUsername = username;
        this.modifiedSchoolBoard = modifiedSchoolBoard;
        this.modifiedIsland = modifiedIsland;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}