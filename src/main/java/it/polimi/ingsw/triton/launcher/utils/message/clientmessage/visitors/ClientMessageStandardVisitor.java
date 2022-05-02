package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.visitors;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.*;

public class ClientMessageStandardVisitor {
    private final Game game;
    private final VirtualView virtualView;

    public ClientMessageStandardVisitor(Game game, VirtualView virtualView) {
        this.game = game;
        this.virtualView = virtualView;
    }

    public void visitForSendStandardMessage(TowerColorReply message){
        virtualView.askTowerColor(game.getTowerColorChosen());
    }

    public void visitForSendStandardMessage(WizardReply message){
        virtualView.askWizard(game.getAvailableWizards());
    }

    public void visitForSendStandardMessage(AssistantCardReply message){
        virtualView.askAssistantCard();
    }

    public void visitForSendStandardMessage(MoveStudentOntoDiningRoomMessage message){
        virtualView.askMoveStudentFromEntrance();
    }

    public void visitForSendStandardMessage(MoveStudentOntoIslandMessage message){
        virtualView.askMoveStudentFromEntrance();
    }

    public void visitForSendStandardMessage(MotherNatureReply message){
        virtualView.askCloudTile();
    }

    public void visitForSendStandardMessage(CloudTileReply message){
        virtualView.askMoveStudentFromEntrance();
    }

    public void visitForSendStandardMessage(UseCharacterCardRequest message){
        virtualView.reSendLastMessage();
    }

    public void visitForSendStandardMessage(CharacterCardReply message){

    }
}
