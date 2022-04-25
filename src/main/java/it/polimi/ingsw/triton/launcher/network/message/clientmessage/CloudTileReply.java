package it.polimi.ingsw.triton.launcher.network.message.clientmessage;

import it.polimi.ingsw.triton.launcher.model.CloudTile;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class CloudTileReply extends ClientMessage {
    private final CloudTile selectedCloudTile;

    public CloudTileReply(String username, CloudTile selectedCloudTile) {
        super(MessageType.CLOUD_TILE_REPLY, username);
        this.selectedCloudTile = selectedCloudTile;
    }

    public CloudTile getSelectedCloudTile() {
        return selectedCloudTile;
    }

}
