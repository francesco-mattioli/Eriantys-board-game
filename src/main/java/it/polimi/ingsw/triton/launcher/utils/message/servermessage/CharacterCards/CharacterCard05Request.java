package it.polimi.ingsw.triton.launcher.utils.message.servermessage.CharacterCards;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This message asks the player on which island he wants to put a no entry tile.
 */
public class CharacterCard05Request extends ServerMessage implements CharacterCardMessages{
    public CharacterCard05Request() {
        super(MessageType.CHARACTER_CARD_05_PARAMETER);
    }
}
