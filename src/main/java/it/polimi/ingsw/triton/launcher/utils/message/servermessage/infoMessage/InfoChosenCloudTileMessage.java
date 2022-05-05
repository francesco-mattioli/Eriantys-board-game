package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

public class InfoChosenCloudTileMessage extends InfoMessage {
    private final String playerUsername;
    private final SchoolBoard playerSchoolBoard;
    private final CloudTile cloudTile;
    public InfoChosenCloudTileMessage(String playerUsername, SchoolBoard playerSchoolBoard, CloudTile cloudTile) {
        this.playerUsername = playerUsername;
        this.playerSchoolBoard = playerSchoolBoard;
        this.cloudTile = cloudTile;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

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
