package it.polimi.ingsw.triton.launcher.network.message;

public abstract class Message {
    private final String nickname;
    private final MessageType messageType;

    public Message(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
    }

}
