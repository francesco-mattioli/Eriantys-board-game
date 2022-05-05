package it.polimi.ingsw.triton.launcher.client.model;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public String toString() {

        return "ClientModel{" +
                " \n- assistantDeck=" + assistantDeck.toString() +
                ", \n- availableCharacterCards=" + printAvailableCharacterCard() +
                ", \n- schoolBoards=\n\t" + schoolBoards +
                ", \n- cloudTiles =" + printCloudTiles() +
                '}';
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

    public ArrayList<CharacterCard> getAvailableCharacterCards() {
        return availableCharacterCards;
    }

    public void setAvailableCharacterCards(ArrayList<CharacterCard> availableCharacterCards) {
        this.availableCharacterCards = availableCharacterCards;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public void setIslands(ArrayList<Island> islands) {
        this.islands = islands;
    }

    public Map<String, SchoolBoard> getSchoolBoards() {
        return schoolBoards;
    }

    public void setSchoolBoards(Map<String, SchoolBoard> schoolBoards) {
        this.schoolBoards = schoolBoards;
    }

    public ArrayList<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    public void setCloudTiles(ArrayList<CloudTile> cloudTiles) {
        this.cloudTiles = cloudTiles;
    }

    public String printCloudTiles(){
        String results = " ";
        for (CloudTile cloudTile: cloudTiles) {
            results += cloudTile.toString();
        }
        return results;
    }

    public String printAvailableCharacterCard(){
        String results = " ";
        for (CharacterCard characterCard : availableCharacterCards) {
            results += characterCard.toString();
        }
        return results;
    }



}
