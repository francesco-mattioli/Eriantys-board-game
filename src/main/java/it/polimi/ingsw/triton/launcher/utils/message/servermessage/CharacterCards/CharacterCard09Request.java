package it.polimi.ingsw.triton.launcher.utils.message.servermessage.CharacterCards;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This message asks the player which color wants to apply the effect.
 */
public class CharacterCard09Request extends ServerMessage implements CharacterCardMessages{
    public CharacterCard09Request() {
        super(MessageType.CHARACTER_CARD_09_PARAMETER);
    }
}
