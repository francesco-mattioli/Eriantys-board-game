package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.visitors;

import it.polimi.ingsw.triton.launcher.client.cli.Cli;
import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.CharacterCardParameterRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.MoveStudentFromEntranceMessage;

public class ClientMessageModifierVisitor {
    private final Game game;

    public ClientMessageModifierVisitor(Game game) {
        this.game = game;
    }

    public void visitForModify(TowerColorReply message){

    }

    public void visitForModify(WizardReply message){

    }

    public void visitForModify(AssistantCardReply message){

    }

    public void visitForModify(MoveStudentOntoDiningRoomMessage message){

    }

    public void visitForModify(MoveStudentOntoIslandMessage message){

    }

    public void visitForModify(MotherNatureReply message){

    }

    public void visitForModify(CloudTileReply message) throws EndGameException, IllegalClientInputException {
        game.chooseCloudTile(game.getCloudTileById(message.getSelectedCloudTileID()));
        game.nextGameTurn();
    }

    public void visitForModify(UseCharacterCardRequest message){

    }

    public void visitForModify(CharacterCard01Reply message){

    }

    public void visitForModify(CharacterCard03Reply message){

    }

    public void visitForModify(CharacterCard05Reply message){

    }

    public void visitForModify(CharacterCard07Reply message){

    }

    public void visitForModify(CharacterCard09Reply message){

    }

    public void visitForModify(CharacterCard10Reply message){

    }

    public void visitForModify(CharacterCard11Reply message){

    }

    public void visitForModify(CharacterCard12Reply message){

    }


}
