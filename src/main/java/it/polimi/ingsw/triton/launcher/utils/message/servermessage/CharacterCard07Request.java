package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks which player wants to switch from this card to his entrance.
 */
public class CharacterCard07Request extends ServerMessage{
    private final int numberMoves;
    public CharacterCard07Request(String receiverUsername) {
        super(MessageType.CHARACTER_CARD_07_PARAMETER, receiverUsername);
        this.numberMoves = 3;
    }

    public int getNumberMoves() {
        return numberMoves;
    }
}
