package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks the player which student wants to put on his dining room.
 */
public class CharacterCard11Request extends ServerMessage{
    public CharacterCard11Request(String receiverUsername) {
        super(MessageType.CHARACTER_CARD_11_PARAMETER, receiverUsername);
    }
}
