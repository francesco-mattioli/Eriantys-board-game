package it.polimi.ingsw.triton.launcher.client.model;

import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.ArrayList;

/**
 * This class saves the state of the game and relevant information.
 */
public class ClientModel extends Observable<Object> {
    private String username;
    private AssistantDeck assistantDeck;

    public SchoolBoard getSchoolBoard() {
        return schoolBoard;
    }

    public void setSchoolBoard(SchoolBoard schoolBoard) {
        this.schoolBoard = schoolBoard;
    }

    private SchoolBoard schoolBoard;

    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }

    public void setAssistantDeck(AssistantDeck assistantDeck) {
        this.assistantDeck = assistantDeck;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
