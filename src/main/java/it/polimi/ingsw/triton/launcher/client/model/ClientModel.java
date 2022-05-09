package it.polimi.ingsw.triton.launcher.client.model;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class saves the state of the game and relevant information.
 */
public class ClientModel extends Observable<Object> {
    private String username;
    private AssistantDeck assistantDeck;
    private ArrayList<CharacterCard> availableCharacterCards;
    private ArrayList<Island> islands;
    private Map<String, SchoolBoard> schoolBoards;
    private ArrayList<CloudTile> cloudTiles;
    private Island motherNaturePosition;
    private AssistantCard lastAssistantCardPlayed;

    @Override
    public String toString() {

        return "{" +
                " \n- assistantDeck = " + assistantDeck.toString() +
                ", \n- availableCharacterCards = " + printAvailableCharacterCard() +
                ", \n- schoolBoards = \n\t" + schoolBoards +
                ", \n- cloudTiles =" + printCloudTiles() +
                ", \n- islands =" + printIslands() +
                ", \n- motherNature is on island " + motherNaturePosition.getId() +
                "\n}";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }

    public void setAssistantDeck(AssistantDeck assistantDeck) {
        this.assistantDeck = assistantDeck;
    }

    public void removeCard(AssistantCard assistantCardToRemove) {
        assistantDeck.getAssistantDeck().removeIf(assistantCard -> assistantCard.getType().getValue() == assistantCardToRemove.getType().getValue());
    }

    public AssistantCard getLastAssistantCardPlayed() {
        return lastAssistantCardPlayed;
    }

    public void setLastAssistantCardPlayed(AssistantCard lastAssistantCardPlayed) {
        this.lastAssistantCardPlayed = lastAssistantCardPlayed;
    }

    public ArrayList<CharacterCard> getAvailableCharacterCards() {
        return availableCharacterCards;
    }

    public void setAvailableCharacterCards(ArrayList<CharacterCard> availableCharacterCards) {
        this.availableCharacterCards = availableCharacterCards;
    }

    public void setAvailableCharacterCard(CharacterCard newCharacterCard) {
        CharacterCard characterCardToUpdate = getCharacterCardById(newCharacterCard.getId());
        availableCharacterCards.set(availableCharacterCards.indexOf(characterCardToUpdate),newCharacterCard);
    }


    public CharacterCard getCharacterCardById(int id){
        for(CharacterCard characterCard: availableCharacterCards){
            if(characterCard.getId() == id)
                return characterCard;
        }
        return null;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public void setIslands(ArrayList<Island> islands) {
        this.islands = islands;
    }

    public void setIsland(Island newIsland) {
        for (Island island : islands) {
            if (island.getId() == newIsland.getId())
                islands.set(islands.indexOf(island), newIsland);
        }
    }

    public void removeIsland(Island islandToDelete) {
        for (Island island : islands) {
            if (island.getId() == islandToDelete.getId())
                islands.remove(island);
        }
    }


    public Map<String, SchoolBoard> getSchoolBoards() {
        return schoolBoards;
    }

    public void setSchoolBoards(Map<String, SchoolBoard> schoolBoards) {
        this.schoolBoards = schoolBoards;
    }

    public void setSchoolBoard(String username, SchoolBoard schoolBoard) {
        this.schoolBoards.put(username, schoolBoard);
    }

    public ArrayList<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    /**
     * @param cloudTiles is the new list to substitute
     */
    public void setCloudTiles(ArrayList<CloudTile> cloudTiles) {
        this.cloudTiles = cloudTiles;
    }

    public void setCloudTile(CloudTile newCloudTile) {
        for (CloudTile cloudTile : cloudTiles) {
            if (cloudTile.getId() == newCloudTile.getId())
                cloudTiles.set(cloudTiles.indexOf(cloudTile), newCloudTile);
        }
    }

    /**
     * @param newCloudTile to remove, indeed it has no more students on it
     */
    public void removeCloudTile(CloudTile newCloudTile) {
        cloudTiles.removeIf(cloudTile -> cloudTile.getId() == newCloudTile.getId());
    }


    public String printCloudTiles() {
        String results = " ";
        for (CloudTile cloudTile : cloudTiles) {
            results += cloudTile.toString();
        }
        return results;
    }

    public String printAvailableCharacterCard() {
        String results = " ";
        for (CharacterCard characterCard : availableCharacterCards) {
            results += characterCard.toString();
        }
        return results;
    }

    public String printIslands() {
        String results = " ";
        for (Island island : islands) {
            results += island.toString();
        }
        return results;
    }

    public String printOtherSchoolBoards(){
        String results = "";
        for(Map.Entry<String, SchoolBoard> schoolBoardEntry: schoolBoards.entrySet()){
            if(!schoolBoardEntry.getKey().equals(username))
                results += schoolBoardEntry.getKey() + "'s SchoolBoard: " + schoolBoardEntry.getValue().toString() + "\n";
        }
        return results;
    }


    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public void setMotherNaturePosition(Island motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }
}
