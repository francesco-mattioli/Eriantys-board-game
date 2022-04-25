package it.polimi.ingsw.triton.launcher.network.message.clientmessage;

import it.polimi.ingsw.triton.launcher.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class WizardReply extends ClientMessage {
    private final Wizard playerWizard;

    public WizardReply(String username, Wizard playerWizard) {
        super(MessageType.WIZARD_REPLY, username);
        this.playerWizard = playerWizard;
    }

    public Wizard getPlayerWizard() {
        return playerWizard;
    }

}
