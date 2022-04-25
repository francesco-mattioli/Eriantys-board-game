package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

import java.util.ArrayList;

public class GameInfoMessage extends BroadcastServerMessage {
    private final ArrayList<String> onlineNicknames;
    private final ArrayList<Wizard> onlineWizards;
    private final ArrayList<CharacterCard> availableCharacterCards;
    public GameInfoMessage(ArrayList<String> onlineNicknames, ArrayList<Wizard> onlineWizards, ArrayList<CharacterCard> avaiableCharacterCards) {
        super(MessageType.GAME_INFO);
        this.onlineNicknames = onlineNicknames;
        this.onlineWizards = onlineWizards;
        this.availableCharacterCards = avaiableCharacterCards;
    }

    public ArrayList<String> getOnlineNicknames() {
        return onlineNicknames;
    }

    public ArrayList<Wizard> getOnlineWizards() {
        return onlineWizards;
    }

    public ArrayList<CharacterCard> getAvailableCharacterCards() {
        return availableCharacterCards;
    }
}
