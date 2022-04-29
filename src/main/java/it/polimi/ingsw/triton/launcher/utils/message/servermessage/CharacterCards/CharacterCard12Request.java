package it.polimi.ingsw.triton.launcher.utils.message.servermessage.CharacterCards;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This message asks the player to choose a color to apply the effect.
 */
public class CharacterCard12Request extends ServerMessage implements CharacterCardMessages{
    public CharacterCard12Request() {
        super(MessageType.CHARACTER_CARD_12_PARAMETER);
    }
}
