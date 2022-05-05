package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

import java.util.ArrayList;

/**
 * Communicates that cloud tiles are filled with new students.
 */
public class CloudTilesInfoMessage extends InfoMessage {
    private ArrayList<CloudTile> cloudTiles;
    public CloudTilesInfoMessage(ArrayList<CloudTile> cloudTiles) {
        this.cloudTiles = cloudTiles;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
