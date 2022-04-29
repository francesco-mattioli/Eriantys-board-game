package it.polimi.ingsw.triton.launcher.utils.message.servermessage.CharacterCards;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This message asks the player which students wants to swap.
 */
public class CharacterCard10Request extends ServerMessage implements CharacterCardMessages{
    private final int maxNumberMoves;
    public CharacterCard10Request() {
        super(MessageType.CHARACTER_CARD_10_PARAMETER;
        maxNumberMoves = 2;
    }

    public int getMaxNumberMoves() {
        return maxNumberMoves;
    }
}
