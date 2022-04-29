package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class MotherNaturePositionMessage extends BroadcastServerMessage {
    private final Island motherNaturePosition;
    public MotherNaturePositionMessage(Island motherNaturePosition) {
        super(MessageType.MOTHER_NATURE_POSITION);
        this.motherNaturePosition = motherNaturePosition;
    }

    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }
}
