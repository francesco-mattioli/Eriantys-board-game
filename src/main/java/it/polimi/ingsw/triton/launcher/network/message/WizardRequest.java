package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;

import java.util.ArrayList;

public class WizardRequest extends Message{
    private ArrayList<Wizard> availableWizards;
    private String receiverNickname;
    public WizardRequest(ArrayList<Wizard> availableWizards, String receiverNickname) {
        super(Game.NAME_SERVER, MessageType.WIZARD_REQUEST);
        this.availableWizards = availableWizards;
        this.receiverNickname = receiverNickname;
    }
}
