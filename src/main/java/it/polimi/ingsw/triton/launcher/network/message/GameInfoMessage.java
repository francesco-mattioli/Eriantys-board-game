package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;

import java.util.ArrayList;

public class GameInfoMessage extends Message{
    private ArrayList<String> onlineNicknames;
    private ArrayList<Wizard> onlineWizards;
    private ArrayList<CharacterCard> availableCharacterCards;
    public GameInfoMessage(ArrayList<String> onlineNicknames, ArrayList<Wizard> onlineWizards, ArrayList<CharacterCard> avaiableCharacterCards) {
        super(Game.NAME_SERVER, MessageType.GAME_INFO);
        this.onlineNicknames = onlineNicknames;
        this.onlineWizards = onlineWizards;
        this.availableCharacterCards = avaiableCharacterCards;
    }
}
