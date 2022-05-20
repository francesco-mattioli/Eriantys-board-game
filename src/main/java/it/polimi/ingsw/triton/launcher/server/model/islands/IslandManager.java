package it.polimi.ingsw.triton.launcher.server.model.islands;

import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyDefault;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.MergeIslandsMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IslandManager extends Observable <InfoMessage> {
    private final List<Island> islands;
    public IslandManager(){
        islands = new ArrayList<>();
        createIslands();
    }

    /**
     * Creates twelve islands of the game.
     */
    private void createIslands() {
        for (int i = 0; i < 12; i++) {
            islands.add(new Island(i));
        }
    }

    /**
     * This method merges two or more adjacent islands with the same dominator.
     * Firstly, update the influence on the island where mother nature is on.
     * It merges when dominator is not null, i.e. there is a dominator.
     * @param motherNatureIslandPosition the island where mother nature is located.
     * @param players the list of players.
     * @param professors the array with professors associated to the players.
     * @throws EndGameException when there are only three groups of islands.
     */
    public void mergeNearIslands(Island motherNatureIslandPosition, List<Player> players, Player[] professors) throws EndGameException {
        motherNatureIslandPosition.updateInfluence(players, professors);
        if (motherNatureIslandPosition.getDominator() != null) {
            if (motherNatureIslandPosition.getDominator() == prevIsland(motherNatureIslandPosition).getDominator())
                mergeAndNotify(motherNatureIslandPosition,prevIsland(motherNatureIslandPosition));
            if (motherNatureIslandPosition.getDominator() == nextIsland(motherNatureIslandPosition).getDominator())
                mergeAndNotify(motherNatureIslandPosition,nextIsland(motherNatureIslandPosition));
        }
    }

    private void mergeAndNotify(Island motherNatureIslandPosition,Island islandToRemove) throws EndGameException {
        motherNatureIslandPosition.merge(islandToRemove);
        notify(new MergeIslandsMessage(motherNatureIslandPosition, islandToRemove));
        islands.remove(islandToRemove);
        checkNumberIslands();
    }

    /**
     * @param idIsland the id of the island to find.
     * @return true if the island with that id exists, false otherwise.
     */
    public boolean existsIsland(int idIsland) {
        for (Island island : islands) {
            if (island.getId() == idIsland)
                return true;
        }
        return false;
    }

    /**
     * Checks if the number of remaining groups of islands is three.
     * @throws RuntimeException if the number of groups islands is three because the game must finish.
     */
    private void checkNumberIslands() throws EndGameException {
        if (islands.size() == 3)   //TO END
            throw new EndGameException();
    }

    /**
     * @param currentIsland the current island.
     * @return next island on the left.
     */
    private Island nextIsland(Island currentIsland) {
        if (islands.indexOf(currentIsland) == islands.size() - 1) {
            return islands.get(0);
        } else
            return islands.get(1 + islands.indexOf(currentIsland));
    }

    /**
     * @param currentIsland the current island.
     * @return previous island on the right.
     */
    private Island prevIsland(Island currentIsland) {
        if (islands.indexOf(currentIsland) == 0) {
            return islands.get(islands.size() - 1);
        } else
            return islands.get(islands.indexOf(currentIsland) - 1);
    }

    public List<Island> getIslands() {
        return islands;
    }

    /**
     * @param id the id of the island to return.
     * @return the island with the id passed by parameter.
     * @throws IllegalClientInputException if the island with that id doesn't exist.
     */
    public Island getIslandByID(int id) throws IllegalClientInputException {
        for (Island island : islands) {
            if (island.getId() == id)
                return island;
        }
        throw new IllegalClientInputException(ErrorTypeID.NO_ISLAND_WITH_THAT_ID);
    }

    /**
     * Resets the influence strategy to default strategy when the turn is over.
     */
    public void resetIslandsInfluenceStrategy(){
        for(Island island: islands)
            island.setInfluenceStrategy(new InfluenceStrategyDefault());
    }

    public Island getRandomIsland() {
        return islands.get((new Random()).nextInt(islands.size()));
    }
}
