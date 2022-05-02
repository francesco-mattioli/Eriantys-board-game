package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

import java.util.ArrayList;

public class WizardRequest extends ServerMessage {
    private final ArrayList<Wizard> availableWizards;
    private String receiverUsername;
    public WizardRequest(ArrayList<Wizard> availableWizards) {
        this.availableWizards = availableWizards;
    }

    public ArrayList<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
