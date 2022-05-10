package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;

import java.util.ArrayList;
import java.util.Map;

public class ExpertGameInfoMessage extends GameInfoMessage{
    private final ArrayList<CharacterCard> availableCharacterCards;

    public ExpertGameInfoMessage(ArrayList<CharacterCard> availableCharacterCards, ArrayList<Island> islands, Island motherNaturePosition, Map<String, SchoolBoard> schoolBoards, ArrayList<CloudTile> cloudTiles, String[] professors) {
        super(islands, motherNaturePosition, schoolBoards, cloudTiles, professors);
        super.expertMode=true;
        this.availableCharacterCards = availableCharacterCards;
    }

    public ArrayList<CharacterCard> getAvailableCharacterCards() {
        return availableCharacterCards;
    }

    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
