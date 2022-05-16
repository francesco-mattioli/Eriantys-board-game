package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;


/**
 * This message is sent by the server to ask the current player the parameters
 * of the character card in order to build the effect.
 * The parameter characterCardID is useful for client view in order to know which
 * parameters are requested to build the effect.
 */
public class CharacterCardParameterRequest extends AskMessage {
    private final int characterCardID;
    public CharacterCardParameterRequest(int characterCardID) {
        this.characterCardID = characterCardID;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    public int getCharacterCardID() {
        return characterCardID;
    }
}
