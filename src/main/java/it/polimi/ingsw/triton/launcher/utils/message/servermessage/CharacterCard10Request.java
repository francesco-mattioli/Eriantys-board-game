package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks the player which students wants to swap.
 */
public class CharacterCard10Request extends ServerMessage{
    private final int maxNumberMoves;
    public CharacterCard10Request(String receiverUsername) {
        super(MessageType.CHARACTER_CARD_10_PARAMETER, receiverUsername);
        maxNumberMoves = 2;
    }

    public int getMaxNumberMoves() {
        return maxNumberMoves;
    }
}
