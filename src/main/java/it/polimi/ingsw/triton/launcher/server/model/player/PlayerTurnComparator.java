package it.polimi.ingsw.triton.launcher.server.model.player;

import java.io.Serializable;
import java.util.Comparator;

public class PlayerTurnComparator implements Comparator<Player>, Serializable {

    /**
     * @param p1 a player.
     * @param p2 another player.
     * @return 1 if p1 has played an assistant card with a value greater than the assistant card played by the second player, otherwise -1.
     */
    @Override
    public int compare(Player p1, Player p2) {
        int cardValueOfPlayer1 = p1.getLastPlayedAssistantCard().getType().getValue();
        int cardValueOfPlayer2 = p2.getLastPlayedAssistantCard().getType().getValue();
        return Integer.compare(cardValueOfPlayer1, cardValueOfPlayer2);
    }
}
