package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

import java.util.ArrayList;

public class CloudTileRequest extends ServerMessage {

    private final ArrayList<CloudTile> availableCloudTiles;
    public CloudTileRequest(ArrayList<CloudTile> availableCloudTiles) {
        super(MessageType.CLOUD_TILE_REQUEST);
        this.availableCloudTiles = availableCloudTiles;
    }

    public ArrayList<CloudTile> getAvailableCloudTiles() {
        return availableCloudTiles;
    }
}
