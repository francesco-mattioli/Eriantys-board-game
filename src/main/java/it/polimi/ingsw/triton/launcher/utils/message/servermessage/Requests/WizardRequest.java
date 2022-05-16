package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;

import java.util.ArrayList;

/**
 * This message is sent from the server to ask a player which wizard wants
 * in the game.
 * The parameter availableWizards contains the wizards the player can choose.
 */
public class WizardRequest extends AskMessage {
    private final ArrayList<Wizard> availableWizards;
    public WizardRequest(ArrayList<Wizard> availableWizards) {
        this.availableWizards = availableWizards;
    }

    public ArrayList<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
