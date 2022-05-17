package it.polimi.ingsw.triton.launcher.server.controller.visitors;

import it.polimi.ingsw.triton.launcher.server.model.game.GameMode;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCardReply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;

/**
 * ClientMessageErrorVisitor manages error situations in controller.
 * When user gives a wrong input, message is re-sent.
 */
public class ClientMessageErrorVisitor {
    private final GameMode game;
    private final VirtualView virtualView;

    public ClientMessageErrorVisitor(GameMode game, VirtualView virtualView) {
        this.game = game;
        this.virtualView = virtualView;
    }

    /**
     * Asks again the player to choose the tower color.
     * @param message the message to resend.
     */
    public void visitForSendErrorMessage(TowerColorReply message){
        virtualView.askTowerColor(game.getTowerColorChosen());
    }

    /**
     * Asks again the player to choose the wizard.
     * @param message the message to resend.
     */
    public void visitForSendErrorMessage(WizardReply message){
        virtualView.askWizard(game.getAvailableWizards());
    }

    /**
     * Asks again the player to choose the assistant card.
     * @param message the message to resend.
     */
    public void visitForSendErrorMessage(AssistantCardReply message){
        virtualView.askAssistantCard();
    }

    /**
     * Asks again the player to move a student from entrance.
     * @param message the message to resend.
     */
    public void visitForSendErrorMessage(MoveStudentOntoDiningRoomMessage message){
        virtualView.askMoveStudentFromEntrance();
    }

    /**
     * Asks again the player to move a student from entrance.
     * @param message the message to resend.
     */
    public void visitForSendErrorMessage(MoveStudentOntoIslandMessage message){
        virtualView.askMoveStudentFromEntrance();
    }

    /**
     * Asks again the player to choose the number of steps mother nature has to do.
     * @param message the message to resend.
     */
    public void visitForSendErrorMessage(MotherNatureReply message){
        virtualView.askNumberStepsMotherNature();
    }

    /**
     * Asks again the player to choose a cloud tile to draw students.
     * @param message the message to resend.
     */
    public void visitForSendErrorMessage(CloudTileReply message){
        virtualView.askCloudTile();
    }

    /**
     * Asks again the player the last request before choosing a character card
     * @param message the message to resend.
     */
    public void visitForSendErrorMessage(UseCharacterCardRequest message){
        virtualView.reSendLastMessage();
    }

    /**
     * Asks again the player a request about the character card.
     * @param message the message to resend.
     */
    public void visitForSendErrorMessage(CharacterCardReply message){
        virtualView.reSendLastCharacterCardMessage();
    }

}
