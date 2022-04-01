package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;

public class Island{

    public Island(int id) {
        this.id=id;
        this.dim=1;
        this.students=new int[5];
    }

    private int id;
    private int dim;
    private TowerColor dominance;
    private int[] students;

    public Island merge(Island i) {
        // TODO implement here
        return null;
    }

    public int calculateInfluence(Player player) {
        // TODO implement here
        return 0;
    }

    public void updateInfluence(ArrayList<Player> players) {
        // TODO implement here
    }

    public void towerInfluence(Player player) {
        // TODO implement here
    }

    public void addStudent(Color color){
        students[color.ordinal()]++;
    }

}
