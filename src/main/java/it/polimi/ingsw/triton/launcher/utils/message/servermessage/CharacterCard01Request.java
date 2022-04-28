package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This messages asks the player which student wants to move from the card to the island.
 */
public class CharacterCard01Request extends ServerMessage{
    private final int numberMoves;
    public CharacterCard01Request(String receiverUsername) {
        super(MessageType.CHARACTER_CARD_01_PARAMETER, receiverUsername);
        this.numberMoves = 1;
    }

    public int getNumberMoves() {
        return numberMoves;
    }
}
