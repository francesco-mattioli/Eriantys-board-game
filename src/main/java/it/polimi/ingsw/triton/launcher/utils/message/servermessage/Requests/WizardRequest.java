package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.WizardReply;

import java.util.List;

/**
 * This message is sent from the server to ask a player which wizard wants
 * in the game.
 * The parameter availableWizards contains the wizards the player can choose.
 */
public class WizardRequest extends AskMessage {

    private final List<Wizard> availableWizards;

    public WizardRequest(List<Wizard> availableWizards) {
        super.expectedResponseMessageClass=WizardReply.class;
        this.availableWizards = availableWizards;
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }



    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
