package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;

public abstract class CharacterCardReply extends ClientMessage {
    public CharacterCardReply(MessageType messageType, String senderUsername) {
        super(messageType, senderUsername);
    }

    @Override
    public void createStandardNextMessage(Game game, VirtualView virtualView) {
        virtualView.reSendLastMessage();
    }

    @Override
    public void createInputErrorMessage(Game game, VirtualView virtualView) {

    }
}
