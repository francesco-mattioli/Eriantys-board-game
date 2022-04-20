package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.CloudTile;

public class CloudTileReply extends Message{
    private CloudTile selectedCloudTile;
    public CloudTileReply(String nickname, CloudTile selectedCloudTile) {
        super(nickname, MessageType.CLOUD_TILE_REPLY);
        this.selectedCloudTile = selectedCloudTile;
    }
}
