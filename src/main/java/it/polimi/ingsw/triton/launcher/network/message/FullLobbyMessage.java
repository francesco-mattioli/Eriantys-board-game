package it.polimi.ingsw.triton.launcher.network.message;

public class FullLobbyMessage extends Message{

    public FullLobbyMessage(String username) {
        super(username, MessageType.FULL_LOBBY);
    }
}
