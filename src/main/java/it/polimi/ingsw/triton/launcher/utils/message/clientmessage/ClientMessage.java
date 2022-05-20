package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.network.Server;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageExceptionalVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;
import java.util.NoSuchElementException;

/**
 * This abstract class is extended by all the messages sent by the client used to reply to
 * server requests.
 */
public abstract class ClientMessage implements Message{

    public abstract void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, NoSuchElementException, LastMoveException, EndGameException, CharacterCardWithParametersException, ChangeTurnException;

    public abstract void createStandardNextMessage(ClientMessageStandardVisitor visitor);

    public void createExceptionalNextMessage(ClientMessageExceptionalVisitor visitor) {
        Server.LOGGER.severe(  "Called a wrong function");
    }

    public abstract void createInputErrorMessage(ClientMessageErrorVisitor visitor);
}
