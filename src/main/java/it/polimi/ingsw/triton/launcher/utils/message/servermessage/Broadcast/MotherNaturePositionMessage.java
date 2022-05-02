package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class MotherNaturePositionMessage extends BroadcastServerMessage {
    private final Island motherNaturePosition;
    public MotherNaturePositionMessage(Island motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }

    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
