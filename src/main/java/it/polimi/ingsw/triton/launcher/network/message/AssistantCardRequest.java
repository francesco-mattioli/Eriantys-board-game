package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class AssistantCardRequest extends Message{
    public AssistantCardRequest() {
        super(Game.NAME_SERVER, MessageType.ASSISTANT_CARD_REQUEST);
    }
}
