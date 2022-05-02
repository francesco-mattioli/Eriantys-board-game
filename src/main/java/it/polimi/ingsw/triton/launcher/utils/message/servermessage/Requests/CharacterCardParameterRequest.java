package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

public class CharacterCardParameterRequest extends ServerMessage {
    private final int characterCardID;
    public CharacterCardParameterRequest(int characterCardID) {
        this.characterCardID = characterCardID;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }

}
