package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks the player which color wants to apply the effect.
 */
public class CharacterCard09Request extends ServerMessage{
    public CharacterCard09Request(String receiverUsername) {
        super(MessageType.CHARACTER_CARD_09_PARAMETER, receiverUsername);
    }
}
