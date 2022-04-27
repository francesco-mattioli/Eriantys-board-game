package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class PlayersNumbersAndModeReply extends ClientMessage {
    private final int playersNumber;
    private final boolean expertMode;



    public PlayersNumbersAndModeReply(String username, int playersNumber, boolean expertMode) {
        super(MessageType.PLAYERSNUMBER_REPLY, username);
        this.playersNumber = playersNumber;
        this.expertMode = expertMode;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

}
