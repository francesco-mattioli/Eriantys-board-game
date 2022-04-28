package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks the player on which island he wants to put a no entry tile.
 */
public class CharacterCard05Request extends ServerMessage{
    public CharacterCard05Request(String receiverUsername) {
        super(MessageType.CHARACTER_CARD_05_PARAMETER, receiverUsername);
    }
}
