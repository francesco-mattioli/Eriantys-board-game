package it.polimi.ingsw.triton.launcher.server.controller.visitors;

import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.game.GameMode;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.clientmessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;

/**
 * ClientMessageExceptionalVisitor manages exceptional situations in controller
 * In javadoc of every method is specified the particular exceptional situation
 */
public class ClientMessageExceptionalVisitor {
    private final GameMode game;
    private final VirtualView virtualView;

    public ClientMessageExceptionalVisitor(GameMode game, VirtualView virtualView) {
        this.game = game;
        this.virtualView = virtualView;
    }

    /**
     * All tower colors were set, so we need to ask first player to choose his wizard
     * @param message
     */
    public void visitForSendExceptionalMessage(TowerColorReply message){
        virtualView.askWizard(game.getAvailableWizards());
    }

    /**
     * All wizards were assigned, so we need to ask first player to play an assistant card
     * @param message
     */
    public void visitForSendExceptionalMessage(WizardReply message){
        virtualView.askAssistantCard();
    }

    /**
     * The planning phase is over, action phase must be started and we need to ask first player to move the first student
     * Before all, we clearly need to order the player array, calling the game method sortPlayerPerTurn
     * @param message
     */
    public void visitForSendExceptionalMessage(AssistantCardReply message){
        game.setGameState(GameState.ACTION_PHASE);
        virtualView.askMoveStudentFromEntrance();
    }

    /**
     * The player has moved the last student, so now we need to ask him to move mother nature
     * @param message
     */
    public void visitForSendExceptionalMessage(MoveStudentOntoDiningRoomMessage message){
        virtualView.askNumberStepsMotherNature();
    }

    /**
     * The player has moved the last student, so now we need to ask him to move mother nature
     * @param message
     */
    public void visitForSendExceptionalMessage(MoveStudentOntoIslandMessage message){
        virtualView.askNumberStepsMotherNature();
    }


    /**
     * Not all cloud tiles are full, so players have not to choose the cloud tile and turn goes on without that micro-phase
     * @param message
     */
    public void visitForSendExceptionalMessage(MotherNatureReply message){
        virtualView.askMoveStudentFromEntrance();
    }

    /**
     * Last player has chosen his cloudtile, so we need to restart with planning phase and ask first player a new assistant card
     * @param message
     */
    public void visitForSendExceptionalMessage(CloudTileReply message){
        virtualView.askAssistantCard();
    }

    /**
     * Player wants, to play a character card, but it needs some parameters
     * @param message
     */
    public void visitForSendExceptionalMessage(UseCharacterCardRequest message){
        virtualView.askCharacterCardParameters(message.getCharacterCardID());
    }
}
