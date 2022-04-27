package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class PlayersNumberReply extends ClientMessage {
    private final int playersNumber;


    public PlayersNumberReply(String username, int playersNumber) {
        super(MessageType.PLAYERSNUMBER_REPLY, username);
        this.playersNumber = playersNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }


}
