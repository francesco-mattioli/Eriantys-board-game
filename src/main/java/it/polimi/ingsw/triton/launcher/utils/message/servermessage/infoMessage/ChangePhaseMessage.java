package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message communicates to the players that a new phase of the game is beginning.
 */
public class ChangePhaseMessage extends InfoMessage {
    private final GameState gameState;

    public ChangePhaseMessage(GameState gameState){
        this.gameState = gameState;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }

    public GameState getGameState() {
        return gameState;
    }
}
