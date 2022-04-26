package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

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
