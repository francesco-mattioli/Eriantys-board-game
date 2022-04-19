package it.polimi.ingsw.triton.launcher.network.message;

public class PlayersNumberReply extends Message{
    private int playersNumber;
    public PlayersNumberReply(String nickname, int playersNumber) {
        super(nickname, MessageType.PLAYERSNUMBER_REPLY);
        this.playersNumber = playersNumber;
    }
}
