package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;

/**
 * This abstract class is extended by all the messages sent by the client that are used
 * to communicate to server the parameters to build the effects of the character cards.
 */
public abstract class CharacterCardReply extends ClientMessage {
    public CharacterCardReply(String senderUsername) {
        super(senderUsername);
    }

    @Override
    public void createStandardNextMessage(ClientMessageStandardVisitor visitor) {
        visitor.visitForSendStandardMessage(this);
    }

    @Override
    public void createInputErrorMessage(ClientMessageErrorVisitor visitor) {
        visitor.visitForSendErrorMessage(this);
    }
}
