package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate to the players that a new phase of the game is beginning.
 */
public class ChangePhaseMessage extends InfoMessage {
    private final GameState gameState;

    public ChangePhaseMessage(GameState gameState){
        this.gameState = gameState;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }

    public GameState getGameState() {
        return gameState;
    }
}
