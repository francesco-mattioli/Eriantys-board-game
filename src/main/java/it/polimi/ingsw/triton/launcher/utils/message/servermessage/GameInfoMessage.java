package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.MotherNature;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * This message communicates to all the players the info of the game.
 */
public class GameInfoMessage extends BroadcastServerMessage {
    private final ArrayList<String> onlineNicknames;
    private final ArrayList<CharacterCard> availableCharacterCards;
    private final ArrayList<Island> islands;
    private final Island motherNaturePosition;
    private final Map<String, SchoolBoard> schoolBoards;
    public GameInfoMessage(ArrayList<String> onlineNicknames, ArrayList<CharacterCard> availableCharacterCards, ArrayList<Island> islands, Island motherNaturePosition, Map<String, SchoolBoard> schoolBoards) {
        super(MessageType.GAME_INFO);
        this.onlineNicknames = onlineNicknames;
        this.availableCharacterCards = availableCharacterCards;
        this.islands = islands;
        this.motherNaturePosition = motherNaturePosition;
        this.schoolBoards = schoolBoards;
    }

    public ArrayList<String> getOnlineNicknames() {
        return onlineNicknames;
    }

    public ArrayList<CharacterCard> getAvailableCharacterCards() {
        return availableCharacterCards;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public Map<String, SchoolBoard> getSchoolBoards() {
        return schoolBoards;
    }
}