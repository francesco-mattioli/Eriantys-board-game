package it.polimi.ingsw.triton.launcher.server.controller.visitors;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;

public class ClientMessageExceptionalVisitor {
    private final Game game;
    private final VirtualView virtualView;

    public ClientMessageExceptionalVisitor(Game game, VirtualView virtualView) {
        this.game = game;
        this.virtualView = virtualView;
    }

    public void visitForSendExceptionalMessage(TowerColorReply message){
        virtualView.askWizard(game.getAvailableWizards());
    }

    public void visitForSendExceptionalMessage(WizardReply message){
        virtualView.askAssistantCard();
    }

    public void visitForSendExceptionalMessage(AssistantCardReply message){
        game.setGameState(GameState.ACTION_PHASE);
        virtualView.askMoveStudentFromEntrance();
    }

    public void visitForSendExceptionalMessage(MoveStudentOntoDiningRoomMessage message){
        virtualView.askNumberStepsMotherNature();
    }

    public void visitForSendExceptionalMessage(MoveStudentOntoIslandMessage message){
        virtualView.askNumberStepsMotherNature();
    }

    public void visitForSendExceptionalMessage(CloudTileReply message){
        game.setGameState(GameState.PLANNING_PHASE);
        virtualView.askAssistantCard();
    }

    public void visitForSendExceptionalMessage(UseCharacterCardRequest message){
        virtualView.askCharacterCardParameters(message.getCharacterCardID());
    }
}
