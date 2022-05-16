package it.polimi.ingsw.triton.launcher.server.controller.visitors;

import it.polimi.ingsw.triton.launcher.server.Server;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.*;
import it.polimi.ingsw.triton.launcher.server.model.game.GameMode;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.*;


/**
 * ClientMessageModifierVisitor modifies parameters, basing on received messages
 * Exceptions thrown by methods are all checked by controller
 */
public class ClientMessageModifierVisitor {
    private final GameMode game;

    public ClientMessageModifierVisitor(GameMode game) {
        this.game = game;
    }

    public void visitForModify(TowerColorReply message) throws IllegalClientInputException, ChangeTurnException {
        game.chooseTowerColor(message.getSenderUsername(), message.getPlayerColor());
    }

    public void visitForModify(WizardReply message) throws IllegalClientInputException, ChangeTurnException {
        game.chooseWizard(message.getSenderUsername(), message.getPlayerWizard());
    }

    public void visitForModify(AssistantCardReply message) throws IllegalClientInputException, ChangeTurnException {
        game.chooseAssistantCard(message.getSenderUsername(), message.getChosenAssistantCard());
    }

    public void visitForModify(MoveStudentOntoDiningRoomMessage message) throws LastMoveException, IllegalClientInputException {
        game.executeActionMoveStudentToDiningRoom(message.getStudent());
    }

    public void visitForModify(MoveStudentOntoIslandMessage message) throws LastMoveException, IllegalClientInputException {
        game.executeActionMoveStudentToIsland(message.getStudent(), message.getIslandID());
    }

    public void visitForModify(MotherNatureReply message) throws IllegalClientInputException, EndGameException, ChangeTurnException {
        game.moveMotherNature(message.getNumSteps());
    }

    public void visitForModify(CloudTileReply message) throws EndGameException, IllegalClientInputException, ChangeTurnException {
        game.chooseCloudTile(game.getCloudTileById(message.getSelectedCloudTileID()));
    }


    public void visitForModify(UseCharacterCardRequest message) throws IllegalClientInputException, CharacterCardWithParametersException, EndGameException {
        game.useCharacterCard(message.getSenderUsername(),message.getCharacterCardID());
        // If the player has not played a character card ability yet, set true the attribute that indicates so.
        // Otherwise, an IllegalClientInputException is thrown.
        game.getPlayerByUsername(message.getSenderUsername()).setTrueHasAlreadyPlayedACharacterCard();
        switch (message.getCharacterCardID()){
            case 2:
                game.applyCharacterCardEffect(message.getCharacterCardID(), new CardEffect02(game.getCurrentPlayer(), game.getProfessorsManager(), game.getProfessors()));
                break;
            case 4:
                game.applyCharacterCardEffect(message.getCharacterCardID(), new CardEffect04(game.getMotherNature()));
                break;
            case 6:
                game.applyCharacterCardEffect(message.getCharacterCardID(), new CardEffect06(game.getIslands()));
                break;
            case 8:
                game.applyCharacterCardEffect(message.getCharacterCardID(), new CardEffect08(game.getIslands()));
                break;
            default:
                Server.LOGGER.severe("ERROR");
        }
    }

    public void visitForModify(CharacterCard01Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(1, new CardEffect01(game.getCharacterCardByID(1), message.getStudent(), game.getIslandByID(message.getIslandID()), game.getBag()));
    }

    public void visitForModify(CharacterCard03Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(3, new CardEffect03(game.getIslandByID(message.getIslandID()), game.getPlayers(), game.getProfessors()));
    }

    public void visitForModify(CharacterCard05Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(5, new CardEffect05(game.getIslandByID(message.getIslandID()), game.getCharacterCardByID(5)));
    }

    public void visitForModify(CharacterCard07Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(7, new CardEffect07(game.getCharacterCardByID(7),message.getStudentsOnCard(), message.getFromCard(), message.getFromSchoolBoard(), game.getPlayerByUsername(message.getSenderUsername()).getSchoolBoard()));
    }

    public void visitForModify(CharacterCard09Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(9, new CardEffect09(game.getIslands(), message.getColor()));
    }

    public void visitForModify(CharacterCard10Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(10, new CardEffect10(message.getFromEntrance(), message.getFromDiningRoom(), game.getPlayerByUsername(message.getSenderUsername()).getSchoolBoard()));
    }

    public void visitForModify(CharacterCard11Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(11, new CardEffect11(message.getStudent(), game.getPlayerByUsername(message.getSenderUsername()).getSchoolBoard(), game.getBag(), game.getCharacterCardByID(11)));
        game.getProfessorsManager().updateProfessors(game.getCurrentPlayer(), message.getStudent(), game.getProfessors());
    }

    public void visitForModify(CharacterCard12Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(12, new CardEffect12(message.getStudent(), game.getPlayers(), game.getBag()));
    }


}
