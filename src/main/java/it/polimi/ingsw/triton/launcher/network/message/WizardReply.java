package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.enums.Wizard;

public class WizardReply extends Message{
    private Wizard playerWizard;
    public WizardReply(String nickname, Wizard playerWizard) {
        super(nickname, MessageType.WIZARD_REPLY);
        this.playerWizard = playerWizard;
    }
}
