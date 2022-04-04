package it.polimi.ingsw.triton.launcher.model;

public class CharacterCard {
    protected int id;
    protected int cost;
    private int[] students;
    private int noEntryTiles;

    public CharacterCard(int id, int cost,int noEntryTiles) {
        this.id = id;
        this.cost = cost;
        this.students=new int[5];
        this.noEntryTiles=noEntryTiles;
    }

    public void increaseCost(){
        this.cost++;
    }

}
