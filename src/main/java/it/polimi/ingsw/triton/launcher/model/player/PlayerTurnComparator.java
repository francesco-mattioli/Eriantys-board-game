package it.polimi.ingsw.triton.launcher.model.player;

import java.util.Comparator;

public class PlayerTurnComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        int cardValueOfPlayer1 = p1.getLastPlayedAssistantCard().getType().getValue();
        int cardValueOfPlayer2 = p2.getLastPlayedAssistantCard().getType().getValue();
        return Integer.compare(cardValueOfPlayer1, cardValueOfPlayer2);
    }
}
