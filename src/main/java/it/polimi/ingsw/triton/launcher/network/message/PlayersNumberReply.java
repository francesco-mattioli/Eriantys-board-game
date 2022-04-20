package it.polimi.ingsw.triton.launcher.network.message;

public class PlayersNumberReply extends Message{
    private int playersNumber;
    private boolean expertMode;
    public PlayersNumberReply(String nickname, int playersNumber, boolean expertMode) {
        super(nickname, MessageType.PLAYERSNUMBER_REPLY);
        this.playersNumber = playersNumber;
        this.expertMode = expertMode;
    }
}
