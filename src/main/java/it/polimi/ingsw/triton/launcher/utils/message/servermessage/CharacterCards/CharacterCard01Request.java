package it.polimi.ingsw.triton.launcher.utils.message.servermessage.CharacterCards;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This messages asks the player which student wants to move from the card to the island.
 */
public class CharacterCard01Request extends ServerMessage implements CharacterCardMessages{
    private final int numberMoves;
    public CharacterCard01Request() {
        super(MessageType.CHARACTER_CARD_01_PARAMETER);
        this.numberMoves = 1;
    }

    public int getNumberMoves() {
        return numberMoves;
    }
}
