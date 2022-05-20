package it.polimi.ingsw.triton.launcher.server.model.islands;

import it.polimi.ingsw.triton.launcher.utils.Utility;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategy;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyDefault;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.ChangeInfluenceMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.MoveTowerOntoIslandMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.MoveTowerOntoSchoolBoardMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.UpdateIslandWithNoEntryTilesMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Island extends Observable<InfoMessage> implements Serializable {

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
     * The current island's parameters will become the sum of the two islands parameters.
     * @param island specifies the island to merge with the current island.
     */
    public void merge(Island island){
        this.dim += island.getDim();
        for (int i = 0; i < students.length; i++) {
            this.students[i] += island.getStudents()[i];
        }
        this.noEntryTiles += island.getNoEntryTiles();
    }

    /**
     * This method calculates the influence that the specified player has on the island.
     * @param player specifies the player we want to calculate the influence.
     * @param professors specifies for each professor which player has that professor.
     * @param dominator specifies the player that is now dominating on the island.
     * @return returns the value of influence for the specified player.
     */
    public int calculateInfluence(Player player, Player[] professors, Player dominator) {
        return influenceStrategy.execute(player, professors, dominator, this);
    }

    public void setInfluenceStrategy(InfluenceStrategy influenceStrategy) {
        this.influenceStrategy = influenceStrategy;
    }

    /**
     * This method updates the island dominator.
     * @param players specifies the list of players.
     * @param professors specifies for each professor which player has that professor.
     */
    public void updateInfluence(List<Player> players, Player[] professors) throws EndGameException{
        boolean modifiedDominator = false;
        if (noEntryTiles == 0) {
            int[] nonDominatorPlayersInfluences = new int[players.size()];
            for(int i = 0; i < players.size(); i++){
                if(dominator != players.get(i)){
                    nonDominatorPlayersInfluences[i] = calculateInfluence(players.get(i), professors, dominator);
                }
            }
            if(!checkForTie(nonDominatorPlayersInfluences)) {
                Player newDominator = dominator;
                int max = calculateInfluence(dominator, professors, dominator);
                for (Player p : players) {
                    int influence = calculateInfluence(p, professors, dominator);
                    if (influence > max) {
                        max = influence;
                        newDominator = p;
                        modifiedDominator = true;
                    }
                }
                towerInfluence(newDominator, professors);
                if(modifiedDominator)
                    notify(new ChangeInfluenceMessage(this, newDominator.getUsername()));
            }
        } else {
            characterCard05.addNoEntryTile();
            noEntryTiles--;
            notify(new UpdateIslandWithNoEntryTilesMessage(this));
        }
    }

    private boolean checkForTie(int [] nonDominatorPlayersInfluences){
        for(int i = 0; i < nonDominatorPlayersInfluences.length-1; i++){
            for (int j = i+1; j<nonDominatorPlayersInfluences.length; j++){
                if(nonDominatorPlayersInfluences[i] == nonDominatorPlayersInfluences[j])
                    return true;
            }
        }
        return false;
    }

    /**
     * This method updates the number of tower on the school boards of the players that are taking or losing the domination.
     * @param newDominator specifies the player that is now dominating on the island.
     * @throws EndGameException if a player has not any other towers.
     */
    public void towerInfluence(Player newDominator, Player [] professors)  throws EndGameException {
        if (dominator != null && dominator != newDominator) {
            dominator.getSchoolBoard().moveTowerOntoSchoolBoard(dim);
            notify(new MoveTowerOntoSchoolBoardMessage(dominator.getUsername(), dominator.getSchoolBoard(), Arrays.stream(professors).map(Player::getUsername).toArray(String[]::new)));
        }
        if (newDominator != null && dominator != newDominator) {
            newDominator.getSchoolBoard().moveTowerOntoIsland(dim);
            dominator = newDominator;
            notify(new MoveTowerOntoIslandMessage(this, newDominator.getUsername(), newDominator.getSchoolBoard()));
        }
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

    public String getDominatorEvenIfNull(){
        if(dominator == null)
            return "/";
        else
            return dominator.getUsername();
    }

    public void setDominator(Player dominator) {
        this.dominator = dominator;
    }

    public String toString(){
        return "\n\t{id:" + id +", " +
                "dimension:" + dim + ", " +
                "dominator:" + getDominatorEvenIfNull() + ", " +
                "students:" + Utility.printColoredStudents(students) + ", " +
                "no entry tiles:" + noEntryTiles + "}";
    }

}
