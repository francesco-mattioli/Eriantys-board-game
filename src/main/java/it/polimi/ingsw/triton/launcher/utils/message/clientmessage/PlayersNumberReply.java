package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class PlayersNumberReply extends ClientMessage {
    private final int playersNumber;


    public PlayersNumberReply(String username, int playersNumber) {
        super(username);
        this.playersNumber = playersNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }


    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, NoSuchElementException, LastMoveException, EndGameException, CharacterCardWithParametersException {

    }

    @Override
    public void createStandardNextMessage(ClientMessageStandardVisitor visitor) {

    }

    @Override
    public void createInputErrorMessage(ClientMessageErrorVisitor visitor) {

    }
}
