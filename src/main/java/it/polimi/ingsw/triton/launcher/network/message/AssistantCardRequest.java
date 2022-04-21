package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class AssistantCardRequest extends Message{
    private String receiverNickname;
    public AssistantCardRequest(String receiverNickname) {
        super(Game.NAME_SERVER, MessageType.ASSISTANT_CARD_REQUEST);
        this.receiverNickname = receiverNickname;
    }
}
