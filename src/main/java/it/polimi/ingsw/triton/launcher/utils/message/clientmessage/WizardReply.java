package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class WizardReply extends ClientMessage {
    private final Wizard playerWizard;

    public WizardReply(String username, Wizard playerWizard) {
        super(MessageType.WIZARD_REPLY, username);
        this.playerWizard = playerWizard;
    }

    public Wizard getPlayerWizard() {
        return playerWizard;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, NoSuchElementException {
        game.chooseWizard(senderUsername, playerWizard);
        game.setCurrentPlayer(game.getNextPlayer(game.getPlayerByUsername(senderUsername)));
    }

    @Override
    public void createStandardNextMessage(Game game, VirtualView virtualView) {
        virtualView.askWizard(game.getAvailableWizards());
    }

    @Override
    public void createExceptionalNextMessage(Game game, VirtualView virtualView) {
        virtualView.askAssistantCard();
    }

    @Override
    public void createInputErrorMessage(Game game, VirtualView virtualView) {
        virtualView.askWizard(game.getAvailableWizards());
    }

}
