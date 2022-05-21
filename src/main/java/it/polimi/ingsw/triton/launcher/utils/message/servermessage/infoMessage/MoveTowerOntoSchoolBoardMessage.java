package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate to the players that one player had to move his towers back to his school board.
 * The parameter usernameDominated is the username of the player who had to move his tower back to his school board.
 */
public class MoveTowerOntoSchoolBoardMessage extends InfoMessage {
    private final String usernameDominated;
    private final SchoolBoard schoolBoard;

    public MoveTowerOntoSchoolBoardMessage(String usernameDominated, SchoolBoard schoolBoard) {
        this.usernameDominated = usernameDominated;
        this.schoolBoard = schoolBoard;
    }

    public String getUsernameDominated() {
        return usernameDominated;
    }

    public SchoolBoard getSchoolBoard() {
        return schoolBoard;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
