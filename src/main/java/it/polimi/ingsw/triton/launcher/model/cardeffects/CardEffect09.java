package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Color;
import it.polimi.ingsw.triton.launcher.model.Island;

import java.util.ArrayList;

public class CardEffect09 implements CardEffect{
    private ArrayList<Island> islands;
    private Color color;

    public CardEffect09(ArrayList<Island> islands, Color color){
        this.islands = islands;
        this.color = color;
    }

    @Override
    public void executeEffect() {
        // TODO implement here
    }
}
