package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.ArrayList;

/**
 * Communicates that cloud tiles are filled with new students.
 */
public class FillCloudTilesMessage extends BroadcastServerMessage{
    private ArrayList<CloudTile> cloudTiles;
    public FillCloudTilesMessage(MessageType messageType, ArrayList<CloudTile> cloudTiles) {
        super(messageType);
        this.cloudTiles = cloudTiles;
    }
}
