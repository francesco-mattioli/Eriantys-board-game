package it.polimi.ingsw.triton.launcher.model;

public class CharacterCard03 extends CharacterCard{

    public CharacterCard03(int id, int cost) {
        super(id, cost);
        //by this way I do not have to set id and cost in every subclass constructor
    }

    @Override
    public void cardEffect(Color student, Island island) {
        island.calculateInfluence();
    }
}
