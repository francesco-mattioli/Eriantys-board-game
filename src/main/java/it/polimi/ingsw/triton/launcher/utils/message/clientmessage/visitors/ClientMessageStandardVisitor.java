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

    }

    public void visitForSendStandardMessage(WizardReply message){

    }

    public void visitForSendStandardMessage(AssistantCardReply message){

    }

    public void visitForSendStandardMessage(MoveStudentOntoDiningRoomMessage message){

    }

    public void visitForSendStandardMessage(MoveStudentOntoIslandMessage message){

    }

    public void visitForSendStandardMessage(MotherNatureReply message){

    }

    public void visitForSendStandardMessage(CloudTileReply message){

    }

    public void visitForSendStandardMessage(UseCharacterCardRequest message){

    }

    public void visitForSendStandardMessage(CharacterCard01Reply message){

    }

    public void visitForSendStandardMessage(CharacterCard03Reply message){

    }

    public void visitForSendStandardMessage(CharacterCard05Reply message){

    }

    public void visitForSendStandardMessage(CharacterCard07Reply message){

    }

    public void visitForSendStandardMessage(CharacterCard09Reply message){

    }

    public void visitForSendStandardMessage(CharacterCard10Reply message){

    }

    public void visitForSendStandardMessage(CharacterCard11Reply message){

    }

    public void visitForSendStandardMessage(CharacterCard12Reply message){

    }
}
