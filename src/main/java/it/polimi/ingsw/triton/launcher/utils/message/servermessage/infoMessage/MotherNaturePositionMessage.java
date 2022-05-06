package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

public class MotherNaturePositionMessage extends InfoMessage {
    private final Island motherNaturePosition;
    public MotherNaturePositionMessage(Island motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }

    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
