package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;

import java.util.ArrayList;

public class WizardRequest extends AskMessage {
    private final ArrayList<Wizard> availableWizards;
    private String receiverUsername;
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
