package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks the player on which island he wants to calculate the influence.
 */
public class CharacterCard03Request extends ServerMessage{
    public CharacterCard03Request(String receiverUsername) {
        super(MessageType.CHARACTER_CARD_03_PARAMETER, receiverUsername);
    }
}
