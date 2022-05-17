package it.polimi.ingsw.triton.launcher.server.controller.visitors;

import it.polimi.ingsw.triton.launcher.server.model.game.GameMode;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.CharacterCardReply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;

/**
 * ClientMessageStandardVisitor manages exceptional situations in controller.
 * In javadoc of every method is specified the particular standard situation.
 */
public class ClientMessageStandardVisitor {
    private final GameMode game;
    private final VirtualView virtualView;

    public ClientMessageStandardVisitor(GameMode game, VirtualView virtualView) {
        this.game = game;
        this.virtualView = virtualView;
    }

    /**
     * One player has chosen his tower color, so we need to ask it to the next one.
     * @param message the last message received.
     */
    public void visitForSendStandardMessage(TowerColorReply message){
        virtualView.askTowerColor(game.getTowerColorChosen());
    }

    /**
     * One player has chosen his wizard, so we need to ask it to the next one.
     * @param message the last message received.
     */
    public void visitForSendStandardMessage(WizardReply message){
        virtualView.askWizard(game.getAvailableWizards());
    }

    /**
     * One player has chosen his assistant card, so we need to ask it to the next one.
     * @param message the last message received.
     */
    public void visitForSendStandardMessage(AssistantCardReply message){
        virtualView.askAssistantCard();
    }

    /**
     * Player has chosen a student to move, so we need to ask him the next one to move.
     * @param message the last message received.
     */
    public void visitForSendStandardMessage(MoveStudentOntoDiningRoomMessage message){
        virtualView.askMoveStudentFromEntrance();
    }

    /**
     * Player has chosen a student to move, so we need to ask him the next one to move.
     * @param message the last message received.
     */
    public void visitForSendStandardMessage(MoveStudentOntoIslandMessage message){
        virtualView.askMoveStudentFromEntrance();
    }

    /**
     * Player has moved mother nature, so we need to ask him to choose a cloud tile.
     * @param message the last message received.
     */
    public void visitForSendStandardMessage(MotherNatureReply message){
        virtualView.askCloudTile();
    }

    /**
     * Player has chosen his cloud tile, so we need to ask to move a student to next player (we stay in action phase!).
     * @param message the last message received.
     */
    public void visitForSendStandardMessage(CloudTileReply message){
        virtualView.askMoveStudentFromEntrance();
    }

    /**
     * Player has requested to play a character card and this one has no parameters, so we need to go back to last request.
     * @param message the last message received.
     */
    public void visitForSendStandardMessage(UseCharacterCardRequest message){
        virtualView.reSendLastMessage();
    }

    /**
     * Player has given the character card parameters, so we need to go back to the last request.
     * @param message the last message received.
     */
    public void visitForSendStandardMessage(CharacterCardReply message){
        virtualView.reSendLastMessage();
    }
}
