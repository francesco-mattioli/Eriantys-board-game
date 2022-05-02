package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;


import it.polimi.ingsw.triton.launcher.server.Server;
import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect02;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect04;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect06;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect08;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class UseCharacterCardRequest extends ClientMessage {
    private final int characterCardID;
    public UseCharacterCardRequest(String username, int characterCardID) {
        super(MessageType.CHARACTER_CARD_REQUEST, username);
        this.characterCardID = characterCardID;    }

    public int getCharacterCardID() {
        return characterCardID;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, CharacterCardWithParametersException, EndGameException {
        game.useCharacterCard(characterCardID);
        switch (characterCardID){
            case 2:
                game.applyCharacterCardEffect(characterCardID, new CardEffect02(game.getCurrentPlayer(), game.getProfessorsManager()));
                break;
            case 4:
                game.applyCharacterCardEffect(characterCardID, new CardEffect04(game.getMotherNature()));
                break;
            case 6:
                game.applyCharacterCardEffect(characterCardID, new CardEffect06(game.getIslands()));
                break;
            case 8:
                game.applyCharacterCardEffect(characterCardID, new CardEffect08(game.getIslands()));
                break;
            default:
                Server.LOGGER.severe("ERROR");
        }

    }

    //The character card has no parameters: effect will be immediately executed
    @Override
    public void createStandardNextMessage(Game game, VirtualView virtualView) {
        virtualView.reSendLastMessage();
    }

    //The character card needs some parameters
    @Override
    public void createExceptionalNextMessage(Game game, VirtualView virtualView){
        virtualView.askCharacterCardParameters(characterCardID);
    }

    //The character card is not available
    @Override
    public void createInputErrorMessage(Game game, VirtualView virtualView) {
        virtualView.reSendLastMessage();
    }
}
