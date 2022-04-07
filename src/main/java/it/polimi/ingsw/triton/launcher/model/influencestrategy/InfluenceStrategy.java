package it.polimi.ingsw.triton.launcher.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.model.Player;

import java.util.ArrayList;

public abstract class InfluenceStrategy {
    public abstract void execute(ArrayList<Player> players);

    public void towerInfluence(ArrayList<Player> players){
        // TODO implement here
    }
}
