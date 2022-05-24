package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages.PlayersNumberAndGameModeReply;

/**
 * This message is sent from the server to ask the first player the parameters of
 * the game such as the number of players and game mode (expert or not).
 */
public class PlayersNumberAndGameModeRequest extends AskMessage {
    public PlayersNumberAndGameModeRequest() {
        super.expectedResponseMessageClass= PlayersNumberAndGameModeReply.class;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
