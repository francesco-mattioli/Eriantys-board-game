package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import java.util.ArrayList;

/**
 * This message communicates to all the players the info of the game.
 */
public class GameInfoMessage extends BroadcastServerMessage {
    private final ArrayList<String> onlineNicknames;
    private final ArrayList<CharacterCard> availableCharacterCards;
    public GameInfoMessage(ArrayList<String> onlineNicknames, ArrayList<CharacterCard> avaiableCharacterCards) {
        super(MessageType.GAME_INFO);
        this.onlineNicknames = onlineNicknames;
        this.availableCharacterCards = avaiableCharacterCards;
    }

    public ArrayList<String> getOnlineNicknames() {
        return onlineNicknames;
    }

    public ArrayList<CharacterCard> getAvailableCharacterCards() {
        return availableCharacterCards;
    }
}
