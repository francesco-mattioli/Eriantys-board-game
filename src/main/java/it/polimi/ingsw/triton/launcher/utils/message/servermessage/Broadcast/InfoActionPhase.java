package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;


import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

import java.util.ArrayList;
import java.util.Map;

public class InfoActionPhase extends ServerMessage {
    private Map<String, SchoolBoard> allSchoolBoards;
    private ArrayList<Island> islands;
    private Island motherNaturePosition;
    public InfoActionPhase(String receiverUsername, Map<String, SchoolBoard> allSchoolBoards, ArrayList<Island> islands, Island motherNaturePosition) {
        this.allSchoolBoards = allSchoolBoards;
        this.islands = islands;
        this.motherNaturePosition = motherNaturePosition;
    }

    public Map<String, SchoolBoard> getAllSchoolBoards() {
        return allSchoolBoards;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
