package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message communicates to the players that one player had to move his towers back to his schoolboard.
 */
public class MoveTowerToSchoolBoardMessage extends InfoMessage {
    private final String usernameDominated;
    private final SchoolBoard schoolBoard;
    private final String[] professors;

    public MoveTowerToSchoolBoardMessage(String usernameDominated, SchoolBoard schoolBoard, String [] professors) {
        this.usernameDominated = usernameDominated;
        this.schoolBoard = schoolBoard;
        this.professors = professors;
    }

    public String getUsernameDominated() {
        return usernameDominated;
    }

    public SchoolBoard getSchoolBoard() {
        return schoolBoard;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
