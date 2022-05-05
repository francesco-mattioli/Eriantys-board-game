package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

import java.util.ArrayList;
import java.util.Map;

public class InfoActionPhase extends InfoMessage {
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
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
