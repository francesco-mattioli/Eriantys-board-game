package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class CloudTileReply extends ClientMessage {
    private final int selectedCloudTileID;

    public CloudTileReply(String username, int selectedCloudTileID) {
        super(MessageType.CLOUD_TILE_REPLY, username);
        this.selectedCloudTileID = selectedCloudTileID;
    }

    public int getSelectedCloudTileID() {
        return selectedCloudTileID;
    }

}
