package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategy;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyDefault;
import it.polimi.ingsw.triton.launcher.model.player.Player;

import java.util.ArrayList;

public class Island {

    private final int id;
    private int dim;
    private Player dominator;
    private final int[] students;
    private int noEntryTiles;
    private InfluenceStrategy influenceStrategy;
    private CharacterCard characterCard05;

    public Island(int id) {
        this.id = id;
        this.dim = 1;
        this.students = new int[5];
        this.noEntryTiles = 0;
        this.influenceStrategy = new InfluenceStrategyDefault();
        this.characterCard05 = null;
        dominator = null;
    }


    //to test
    public void merge(Island island) {
        this.dim += island.getDim();
        for (int i = 0; i < students.length; i++) {
            this.students[i] += island.getStudents()[i];
        }
        this.noEntryTiles += island.getNoEntryTiles();
    }

    public int calculateInfluence(Player player, Player[] professors, Player dominator) {
        return influenceStrategy.execute(player, professors, dominator, this);
    }
    public void setInfluenceStrategy(InfluenceStrategy influenceStrategy) {
        this.influenceStrategy = influenceStrategy;
    }

    public void updateInfluence(ArrayList<Player> players, Player[] professors) {
        if (noEntryTiles == 0) {
            Player newDominator = null;
            int max = 0;
            for (Player p : players) {
                int influence = calculateInfluence(p, professors, dominator);
                if (influence > max) {
                    max = influence;
                    newDominator = p;
                }
            }
            towerInfluence(newDominator);
        } else {
            characterCard05.addNoEntryTile();
            noEntryTiles--;
        }
    }

    public void towerInfluence(Player newDominator) {
        if (dominator != null && newDominator != null && dominator != newDominator) {
            dominator.getSchoolBoard().moveTowerOntoSchoolBoard(dim);
            newDominator.getSchoolBoard().moveTowerOntoIsland(dim);
            dominator = newDominator;
        }
    }

    public void addStudent(Color color) {
        students[color.ordinal()]++;
    }



    public void setCharacterCard05(CharacterCard characterCard05) {
        this.characterCard05 = characterCard05;
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

    public void setNoEntryTiles(int noEntryTiles) {
        this.noEntryTiles = noEntryTiles;
    }

    public Player getDominator() {
        return dominator;
    }

}
