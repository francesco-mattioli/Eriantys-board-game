package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class WinMessage extends BroadcastServerMessage {
    private final String nicknameWinner;
    public WinMessage(String nicknameWinner) {
        super(MessageType.WIN);
        this.nicknameWinner = nicknameWinner;
    }

    public String getNicknameWinner() {
        return nicknameWinner;
    }
}
