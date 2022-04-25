package it.polimi.ingsw.triton.launcher.network.message.clientmessage;


import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class CharacterCardRequest extends ClientMessage {

    public CharacterCardRequest(String username) {
        super(MessageType.CHARACTER_CARD_REQUEST, username);
    }

}
