package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.CloudTile;
import it.polimi.ingsw.triton.launcher.model.Game;

import java.util.ArrayList;

public class CloudTileRequest extends Message{
    private ArrayList<CloudTile> availableCloudTiles;
    private String receiverNickname;
    public CloudTileRequest(ArrayList<CloudTile> availableCloudTiles, String receiverNickname) {
        super(Game.NAME_SERVER, MessageType.CLOUD_TILE_REQUEST);
        this.availableCloudTiles = availableCloudTiles;
        this.receiverNickname = receiverNickname;
    }
}
