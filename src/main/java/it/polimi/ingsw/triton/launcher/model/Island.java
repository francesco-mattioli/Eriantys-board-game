package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;

import java.util.ArrayList;

public class Island{

    private int id;
    private int dim;
    private Player dominator;
    private int[] students;
    private int noEntryTiles;
    private InfluenceStrategy influenceStrategy;
    private CharacterCard characterCard5 = null;

    public Island(int id) {
        this.id=id;
        this.dim=1;
        this.students=new int[5];
        this.noEntryTiles = 0;
        this.influenceStrategy = new InfluenceStrategyDefault();
        dominator = null;
    }

    public int[] getStudents() {
        return students;
    }

    public int getDim() {
        return dim;
    }

    public int getId() {
        return id;
    }

    public int getNoEntryTiles() {
        return noEntryTiles;
    }

    //to test
    public void merge(Island island) {
        this.dim += island.getDim();
        for(int i = 0; i< students.length; i++){
            this.students[i]+= island.getStudents()[i];
        }
        this.noEntryTiles += island.getNoEntryTiles();
    }

    public int calculateInfluence(Player player, Player[] professors) {
        int influence = 0;
        if(dominator == player)
            influence+= dim;
        for(int i = 0; i<professors.length; i++){
            if(professors[i] == player){
                influence += students[i];
            }
        }
        return influence;
    }

    public void updateInfluence(ArrayList<Player> players, Player [] professors) {
        Player newDominator = null;
        int max = 0;
        for(Player p : players){
            int influence = calculateInfluence(p, professors);
            if(influence> max) {
                max = influence;
                newDominator = p;
            }
        }
        towerInfluence(newDominator);
    }

    public void towerInfluence(Player newDominator) {
        if (dominator != null && newDominator != null && dominator != newDominator){
            dominator.getSchoolBoard().moveTowerOntoSchoolBoard(dim);
            newDominator.getSchoolBoard().moveTowerOntoIsland(dim, this);
            dominator = newDominator;
        }
    }

    public void addStudent(Color color){
        students[color.ordinal()]++;
    }



}
