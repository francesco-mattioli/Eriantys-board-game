package it.polimi.ingsw.triton.launcher.utils.message.servermessage.CharacterCards;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This message asks the player on which island he wants to calculate the influence.
 */
public class CharacterCard03Request extends ServerMessage implements CharacterCardMessages{
    public CharacterCard03Request() {
        super(MessageType.CHARACTER_CARD_03_PARAMETER);
    }
}
