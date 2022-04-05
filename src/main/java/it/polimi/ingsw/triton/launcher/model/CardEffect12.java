package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;

public class CardEffect12 implements CardEffect{
    private Color student;
    private ArrayList<Player> players;

    public CardEffect12(Color student, ArrayList<Player> players){
        this.student = student;
        this.players = players;
    }

    @Override
    public void executeEffect() {
        // TODO implement here
    }
}
