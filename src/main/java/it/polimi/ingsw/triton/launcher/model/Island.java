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

    /**
     * This method merges two islands, keeping the current island.
     * The current island's parameters will become the sum of the two islands parameters
     * @param island specifies the island to merge with the current island
     */
    public void merge(Island island) throws IllegalArgumentException {
        if (this.dominator != island.getDominator() || this.dominator == null)
            throw new IllegalArgumentException("The islands must have the same dominator");
        this.dim += island.getDim();
        for (int i = 0; i < students.length; i++) {
            this.students[i] += island.getStudents()[i];
        }
        this.noEntryTiles += island.getNoEntryTiles();
    }

    /**
     * This method calculates the influence that the specified player has on the island
     * @param player specifies the player we want to calculate the influence
     * @param professors specifies for each professor which player has that professor
     * @param dominator specifies the player that is now dominating on the island
     * @return returns the value of influence for the specified player
     */
    public int calculateInfluence(Player player, Player[] professors, Player dominator) {
        return influenceStrategy.execute(player, professors, dominator, this);
    }

    public void setInfluenceStrategy(InfluenceStrategy influenceStrategy) {
        this.influenceStrategy = influenceStrategy;
    }

    /**
     * This methods updates the island dominator
     * @param players specifies the ArrayList of players
     * @param professors specifies for each professor which player has that professor
     */
    public void updateInfluence(ArrayList<Player> players, Player[] professors) {
        if (noEntryTiles == 0) {
            Player newDominator = dominator;
            int max = calculateInfluence(dominator, professors, dominator);
            boolean modifiedMax = false;
            for (Player p : players) {
                int influence = calculateInfluence(p, professors, dominator);
                if (influence > max) {
                    max = influence;
                    newDominator = p;
                    modifiedMax = true;
                }
                else if(dominator == null && modifiedMax && influence == max){
                    newDominator = null;
                    break;
                }
            }
            towerInfluence(newDominator);
        } else {
            characterCard05.addNoEntryTile();
            noEntryTiles--;
        }
    }

    /**
     * This method updates the number of tower on the schoolboards of the players that are taking or losing the domination
     * @param newDominator specifies the player that is now dominating on the island
     */
    public void towerInfluence(Player newDominator) {
        if (dominator != null && dominator != newDominator) {
            dominator.getSchoolBoard().moveTowerOntoSchoolBoard(dim);
        }
        if (newDominator != null && dominator != newDominator) {
            newDominator.getSchoolBoard().moveTowerOntoIsland(dim);
        }
        dominator = newDominator;
    }

    /**
     * This method adds one student on the onto this island
     * @param color specifies the color of the student that has to be added onto the island
     */
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
