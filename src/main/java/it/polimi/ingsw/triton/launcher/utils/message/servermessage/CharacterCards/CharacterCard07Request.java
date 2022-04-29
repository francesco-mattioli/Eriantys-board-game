package it.polimi.ingsw.triton.launcher.utils.message.servermessage.CharacterCards;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This message asks the player which student wants to switch from this card to his entrance.
 */
public class CharacterCard07Request extends ServerMessage implements CharacterCardMessages{
    private final int numberMoves;
    public CharacterCard07Request() {
        super(MessageType.CHARACTER_CARD_07_PARAMETER);
        this.numberMoves = 3;
    }

    public int getNumberMoves() {
        return numberMoves;
    }
}
