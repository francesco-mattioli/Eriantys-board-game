package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate which cloud tile is chosen by the current player.
 * The parameter choiceDescription is used on client-side to tell to other players the id of the chosen cloud tile.
 */
public class InfoChosenCloudTileMessage extends InfoMessage {
    private final String playerUsername;
    private final SchoolBoard playerSchoolBoard;
    private final CloudTile cloudTile;
    private final String[] professors;
    private final String choiceDescription;

    public InfoChosenCloudTileMessage(String playerUsername, SchoolBoard playerSchoolBoard, CloudTile cloudTile, String[] professors, String choiceDescription) {
        this.playerUsername = playerUsername;
        this.playerSchoolBoard = playerSchoolBoard;
        this.cloudTile = cloudTile;
        this.professors = professors;
        this.choiceDescription = choiceDescription;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public SchoolBoard getPlayerSchoolBoard() {
        return playerSchoolBoard;
    }

    public CloudTile getCloudTile() {
        return cloudTile;
    }

    public String getChoiceDescription() {
        return choiceDescription;
    }
}
