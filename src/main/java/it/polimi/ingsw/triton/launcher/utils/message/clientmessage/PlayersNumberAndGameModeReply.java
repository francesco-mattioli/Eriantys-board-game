package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;

import java.util.NoSuchElementException;

public class PlayersNumberAndGameModeReply extends ClientMessage {
    private final int playersNumber;
    private final boolean expertMode;

    public PlayersNumberAndGameModeReply(String username, int playersNumber, boolean expertMode) {
        super(username);
        this.playersNumber = playersNumber;
        this.expertMode = expertMode;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public boolean isExpertMode(){
        return expertMode;
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
