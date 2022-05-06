package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

public class InfoChosenCloudTileMessage extends InfoMessage {
    private final String playerUsername;
    private final SchoolBoard playerSchoolBoard;
    private final CloudTile cloudTile;
    private final String[] professors;

    public InfoChosenCloudTileMessage(String playerUsername, SchoolBoard playerSchoolBoard, CloudTile cloudTile, String[] professors) {
        this.playerUsername = playerUsername;
        this.playerSchoolBoard = playerSchoolBoard;
        this.cloudTile = cloudTile;
        this.professors = professors;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public SchoolBoard getPlayerSchoolBoard() {
        return playerSchoolBoard;
    }

    public CloudTile getCloudTile() {
        return cloudTile;
    }
}
