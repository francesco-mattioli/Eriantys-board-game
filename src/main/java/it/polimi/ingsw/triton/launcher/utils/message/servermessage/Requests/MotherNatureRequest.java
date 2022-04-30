package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

public class MotherNatureRequest extends ServerMessage {
    public MotherNatureRequest() {
        super(MessageType.NUMBER_STEPS_MOTHER_NATURE_REQUEST);
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
