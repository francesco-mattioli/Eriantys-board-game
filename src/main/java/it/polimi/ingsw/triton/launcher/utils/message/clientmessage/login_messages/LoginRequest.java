package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages;

import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;

import java.util.NoSuchElementException;

/**
 * This message is sent by the client to communicate to server that a new player wants to log in.
 */
public class LoginRequest extends ClientMessage {
    private final String username;

    public LoginRequest(String username) {
        this.username = username;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, NoSuchElementException, LastMoveException, EndGameException, CharacterCardWithParametersException {
        throw new UnsupportedOperationException(super.unsupportedStringMessage(this));
    }

    @Override
    public void createStandardNextMessage(ClientMessageStandardVisitor visitor) {
        throw new UnsupportedOperationException(super.unsupportedStringMessage(this));
    }

    @Override
    public void createInputErrorMessage(ClientMessageErrorVisitor visitor) {
        throw new UnsupportedOperationException(super.unsupportedStringMessage(this));
    }


    public String getUsername() {
        return username;
    }


}
