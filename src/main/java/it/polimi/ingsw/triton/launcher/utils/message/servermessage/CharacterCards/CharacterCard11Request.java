package it.polimi.ingsw.triton.launcher.utils.message.servermessage.CharacterCards;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This message asks the player which student wants to put on his dining room.
 */
public class CharacterCard11Request extends ServerMessage implements CharacterCardMessages{
    public CharacterCard11Request() {
        super(MessageType.CHARACTER_CARD_11_PARAMETER);
    }
}
