package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.ArrayList;

/**
 * Communicates that cloud tiles are filled with new students.
 */
public class CloudTilesInfoMessage extends BroadcastServerMessage{
    private ArrayList<CloudTile> cloudTiles;
    public CloudTilesInfoMessage(ArrayList<CloudTile> cloudTiles) {
        this.cloudTiles = cloudTiles;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
