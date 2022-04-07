package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.Player;

import java.util.ArrayList;

public class CardEffect03 implements CardEffect{
    private Island island;
    private ArrayList<Player> players;

    public CardEffect03(Island island, ArrayList<Player> players){
        this.island = island;
        this.players = players;
    }

    @Override
    public void execute() {
        // TODO implement here
    }
}
