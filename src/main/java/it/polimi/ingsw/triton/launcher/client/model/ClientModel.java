package it.polimi.ingsw.triton.launcher.client.model;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.Utility;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class saves the state of the game and relevant information.
 */
public class ClientModel extends Observable<Object> {
    private String username;
    private AssistantDeck assistantDeck;
    private ArrayList<CharacterCard> availableCharacterCards;
    private List<Island> islands;
    private Map<String, SchoolBoard> schoolBoards;
    private ArrayList<CloudTile> cloudTiles;
    private Island motherNaturePosition;
    private boolean expertMode;
    private String[] professors;
    private int wallet;
    private Map<String, Wizard> chosenWizardsPerUsername;
    private final Map<String,AssistantCard> lastAssistantCardPlayedPerUsername=new HashMap<>();

    public Map<String, AssistantCard> getLastAssistantCardPlayedPerUsername() {
        return lastAssistantCardPlayedPerUsername;
    }

    @Override
    public String toString() {
        String result = "{";
        if(expertMode)
            result += "\n- EXPERT GAME:" ;
        else
            result += "\n- GAME:" ;
        result+=    " \n- " + assistantDeck.toString() +
                    " \n- players' wizards: " + chosenWizardsPerUsername.toString()+
                    " \n- schoolBoards: \n\t" + printAllSchoolBoards() +
                    " \n- cloudTiles:" + printCloudTiles() +
                    " \n- islands =" + printIslands() +
                    " \n- motherNature is on island " + motherNaturePosition.getId() +
                    " \n- professors: " + Utility.printColoredProfessorsOnTable(professors);
        if(expertMode)
            result += ", \n- availableCharacterCards: " + printAvailableCharacterCard();
        result+="\n}";
        return result;
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

    public List<Island> getIslands() {
        return islands;
    }

    public void setIslands(List<Island> islands) {
        this.islands = islands;
    }

    public void setIsland(Island newIsland) {
        for (Island island : islands) {
            if (island.getId() == newIsland.getId())
                islands.set(islands.indexOf(island), newIsland);
        }
    }

    public void removeIsland(Island islandToDelete) {
        islands.removeIf(island -> island.getId() == islandToDelete.getId());
    }

    public Map<String, SchoolBoard> getSchoolBoards() {
        return schoolBoards;
    }

    public void setSchoolBoards(Map<String, SchoolBoard> schoolBoards) {
        this.schoolBoards = schoolBoards;
    }

    public SchoolBoard getMySchoolBoard(){
        return schoolBoards.get(this.username);
    }

    public void setSchoolBoard(String username, SchoolBoard schoolBoard) {
        this.schoolBoards.put(username, schoolBoard);
    }

    public void setLastAssistantCardPlayed(String username,AssistantCard lastAssistantCardPlayed){
        this.lastAssistantCardPlayedPerUsername.put(username,lastAssistantCardPlayed);
    }

    public AssistantCard getMyLastAssistantCardPlayed(){
        return this.lastAssistantCardPlayedPerUsername.get(this.username);
    }




    public String printWallet(){
        String results = "Your Wallet: " + wallet;
        if(wallet == 1)
            results += " coin";
        else
            results += " coins";
        return results;
    }

    public CloudTile getCloudTileById(int id) {
        for(CloudTile cloudTile: cloudTiles)
            if(cloudTile.getId() == id)
                return cloudTile;
            return null;
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

    public String printYourSchoolBoard(){
        String results = "";
        for(Map.Entry<String, SchoolBoard> schoolBoardEntry: schoolBoards.entrySet()){
            if(schoolBoardEntry.getKey().equals(username)) {
                results += "Your SchoolBoard: " + schoolBoardEntry.getValue().toString();
                results += "\t\t\tProfessors: " + printProfessors(schoolBoardEntry.getKey()) + "\n\n";
                break;
            }
        }
        return results;
    }

    public String printOtherSchoolBoards(){
        String results = "";
        for(Map.Entry<String, SchoolBoard> schoolBoardEntry: schoolBoards.entrySet()){
            if(!schoolBoardEntry.getKey().equals(username)) {
                results += schoolBoardEntry.getKey() + "'s SchoolBoard: " + schoolBoardEntry.getValue().toString();
                results += "\t\t\tProfessors: " + printProfessors(schoolBoardEntry.getKey()) + "\n\n";
            }
        }
        return results;
    }

    public String printAllSchoolBoards(){
        String results = "";
        for(Map.Entry<String, SchoolBoard> schoolBoardEntry: schoolBoards.entrySet()){
            results += schoolBoardEntry.getKey() + "'s SchoolBoard: " + schoolBoardEntry.getValue().toString();
            results += "\t\t\tProfessors: " + printProfessors(schoolBoardEntry.getKey()) + "\n\n";
        }
        return results;
    }

    public String printProfessors(String owner){
        return Utility.printColoredProfessorsOnSchoolBoard(professors, owner);
    }


    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public void setMotherNaturePosition(Island motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }

    public String[] getProfessors() {
        return professors;
    }

    public void setProfessors(String[] professors) {
        this.professors = professors;
    }

    public int getWallet(){
        return wallet;
    }

    public void setWallet(int value){
        this.wallet = value;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public Map<String, Wizard> getChosenWizardsPerUsername() {
        return chosenWizardsPerUsername;
    }

    public void setChosenWizardsPerUsername(Map<String, Wizard> chosenWizardsPerUsername) {
        this.chosenWizardsPerUsername = chosenWizardsPerUsername;
    }



}
