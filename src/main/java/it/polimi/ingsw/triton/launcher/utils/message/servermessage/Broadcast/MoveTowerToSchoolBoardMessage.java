package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the players that one player had to move his towers back to his schoolboard.
 */
public class MoveTowerToSchoolBoardMessage extends BroadcastServerMessage{
    private String usernameDominated;
    private int numTowersToSchoolBoard;
    public MoveTowerToSchoolBoardMessage(String usernameDominated, int numTowersToSchoolBoard) {
        super(MessageType.TOWER_ON_SCHOOLBOARD);
        this.usernameDominated = usernameDominated;
        this.numTowersToSchoolBoard = numTowersToSchoolBoard;
    }

    public String getUsernameDominated() {
        return usernameDominated;
    }

    public int getNumTowersToSchoolBoard() {
        return numTowersToSchoolBoard;
    }
}
