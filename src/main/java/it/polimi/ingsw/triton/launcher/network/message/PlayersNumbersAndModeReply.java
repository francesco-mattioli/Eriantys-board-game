package it.polimi.ingsw.triton.launcher.network.message;

public class PlayersNumbersAndModeReply extends Message{
    private int playersNumber;
    private boolean expertMode;

    public int getPlayersNumber() {
        return playersNumber;
    }

    public PlayersNumbersAndModeReply(String nickname, int playersNumber, boolean expertMode) {
        super(nickname, MessageType.PLAYERSNUMBER_REPLY);
        this.playersNumber = playersNumber;
        this.expertMode = expertMode;
    }
}
