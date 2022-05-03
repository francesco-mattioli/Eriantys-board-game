package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;

/**
 * This message communicates to the players that a new phase of the game is beginning.
 */
public class ChangePhaseMessage extends BroadcastServerMessage{
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
