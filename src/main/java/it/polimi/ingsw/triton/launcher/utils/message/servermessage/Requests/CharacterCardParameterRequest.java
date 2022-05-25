package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.*;


/**
 * This message is sent by the server to ask the current player the parameters
 * of the character card in order to build the effect.
 * The parameter characterCardID is useful for client view in order to know which
 * parameters are requested to build the effect.
 */
public class CharacterCardParameterRequest extends AskMessage {
    private final int characterCardID;

    public CharacterCardParameterRequest(int characterCardID) {
        super.expectedResponseMessageClasses.add(getClassByCharacterCardId(characterCardID));
        this.characterCardID = characterCardID;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    public int getCharacterCardID() {
        return characterCardID;
    }

    private Class<?> getClassByCharacterCardId(int characterCardID) {
        switch (characterCardID) {
            case 1:
                return CharacterCard01Reply.class;
            case 3:
                return CharacterCard03Reply.class;
            case 5:
                return CharacterCard05Reply.class;
            case 7:
                return CharacterCard07Reply.class;
            case 9:
                return CharacterCard09Reply.class;
            case 10:
                return CharacterCard10Reply.class;
            case 11:
                return CharacterCard11Reply.class;
            case 12:
                return CharacterCard12Reply.class;
            default:
                throw new IllegalStateException();
        }
    }
}
