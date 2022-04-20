package it.polimi.ingsw.triton.launcher.network.message;

public class CharacterCardRequest extends Message{
    public CharacterCardRequest(String nickname) {
        super(nickname, MessageType.CHARACTER_CARD_REQUEST);
    }
}
