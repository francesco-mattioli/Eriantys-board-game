package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.ArrayList;

/**
 * Communicates that cloud tiles are filled with new students.
 */
public class CloudTilesInfoMessage extends BroadcastServerMessage{
    private ArrayList<CloudTile> cloudTiles;
    public CloudTilesInfoMessage(ArrayList<CloudTile> cloudTiles) {
        super(MessageType.FILLED_CLOUD_TILES);
        this.cloudTiles = cloudTiles;
    }
}
