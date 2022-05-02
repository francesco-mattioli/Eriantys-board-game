package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.Server;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageExceptionalVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;

import java.util.NoSuchElementException;

public abstract class ClientMessage extends Message{
    protected final String senderUsername;

    public ClientMessage(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }

    public abstract void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, NoSuchElementException, LastMoveException, EndGameException, CharacterCardWithParametersException;

    public abstract void createStandardNextMessage(ClientMessageStandardVisitor visitor);

    public void createExceptionalNextMessage(ClientMessageExceptionalVisitor visitor) {
        Server.LOGGER.severe(  "Called a wrong function");
    }

    public abstract void createInputErrorMessage(ClientMessageErrorVisitor visitor);
}
