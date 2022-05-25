package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

import java.util.List;

/**
 * This message is sent by the server to communicate to the players that cloud tiles are now filled with new students.
 */
public class CloudTilesInfoMessage extends InfoMessage {
    private final List<CloudTile> cloudTiles;

    public CloudTilesInfoMessage(List<CloudTile> cloudTiles) {
        this.cloudTiles = cloudTiles;
    }

    public List<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
