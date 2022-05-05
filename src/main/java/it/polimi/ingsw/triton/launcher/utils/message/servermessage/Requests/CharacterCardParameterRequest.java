package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;


public class CharacterCardParameterRequest extends AskMessage {
    private final int characterCardID;
    public CharacterCardParameterRequest(int characterCardID) {
        this.characterCardID = characterCardID;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }

}
