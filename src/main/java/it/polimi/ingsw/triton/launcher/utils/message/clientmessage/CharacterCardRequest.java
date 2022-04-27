package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;


import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class CharacterCardRequest extends ClientMessage {

    public CharacterCardRequest(String username) {
        super(MessageType.CHARACTER_CARD_REQUEST, username);
    }

}
