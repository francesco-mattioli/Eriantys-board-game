package it.polimi.ingsw.triton.launcher.server.controller.visitors;

import it.polimi.ingsw.triton.launcher.server.model.game.GameMode;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCardReply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;

/**
 * ClientMessageErrorVisitor manages error situations in controller
 * When user gives a wrong input, message is re-sent
 */
public class ClientMessageErrorVisitor {
    private final GameMode game;
    private final VirtualView virtualView;

    public ClientMessageErrorVisitor(GameMode game, VirtualView virtualView) {
        this.game = game;
        this.virtualView = virtualView;
    }

    public void visitForSendErrorMessage(TowerColorReply message){
        virtualView.askTowerColor(game.getTowerColorChosen());
    }

    public void visitForSendErrorMessage(WizardReply message){
        virtualView.askWizard(game.getAvailableWizards());
    }

    public void visitForSendErrorMessage(AssistantCardReply message){
        virtualView.askAssistantCard();
    }

    public void visitForSendErrorMessage(MoveStudentOntoDiningRoomMessage message){
        virtualView.askMoveStudentFromEntrance();
    }

    public void visitForSendErrorMessage(MoveStudentOntoIslandMessage message){
        virtualView.askMoveStudentFromEntrance();
    }

    public void visitForSendErrorMessage(MotherNatureReply message){
        virtualView.askNumberStepsMotherNature();
    }

    public void visitForSendErrorMessage(CloudTileReply message){
        virtualView.askCloudTile();
    }

    public void visitForSendErrorMessage(UseCharacterCardRequest message){
        virtualView.reSendLastMessage();
    }

    public void visitForSendErrorMessage(CharacterCardReply message){
        virtualView.reSendLastCharacterCardMessage();
    }

}
