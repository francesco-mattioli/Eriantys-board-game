package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.model.CloudTile;
import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

import java.util.ArrayList;

public class CloudTileRequest extends ServerMessage {

    private final ArrayList<CloudTile> availableCloudTiles;
    private String receiverUsername;
    public CloudTileRequest(ArrayList<CloudTile> availableCloudTiles, String receiverUsername) {
        super(MessageType.CLOUD_TILE_REQUEST, receiverUsername);
        this.availableCloudTiles = availableCloudTiles;
    }

    public ArrayList<CloudTile> getAvailableCloudTiles() {
        return availableCloudTiles;
    }
}
