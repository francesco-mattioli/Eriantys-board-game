package it.polimi.ingsw.triton.launcher.model;

public abstract class CharacterCard {
    protected int id;
    protected int cost;

    public CharacterCard(int id, int cost) {
        this.id = id;
        this.cost = cost;
    }

    public void cardEffect(Color student, Island island){
        throw new UnsupportedOperationException("Subclass responsiblity");
    }
    public void increaseCost(){
        this.cost++;
    }
}
