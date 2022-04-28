package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks the player to choose a color to apply the effect.
 */
public class CharacterCard12Request extends ServerMessage{
    public CharacterCard12Request(String receiverUsername) {
        super(MessageType.CHARACTER_CARD_12_PARAMETER, receiverUsername);
    }
}
