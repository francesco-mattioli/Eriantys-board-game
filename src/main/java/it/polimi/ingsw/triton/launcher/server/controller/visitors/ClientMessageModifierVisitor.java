package it.polimi.ingsw.triton.launcher.server.controller.visitors;

import it.polimi.ingsw.triton.launcher.server.network.Server;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.*;
import it.polimi.ingsw.triton.launcher.server.model.game.GameMode;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.*;


/**
 * ClientMessageModifierVisitor modifies parameters, basing on received messages.
 * Exceptions thrown by methods are all caught by controller.
 */
public class ClientMessageModifierVisitor {
    private final GameMode game;

    public ClientMessageModifierVisitor(GameMode game) {
        this.game = game;
    }

    /**
     * Sets the tower color to the current player, after receiving the reply from him.
     * @param message the last message received.
     * @throws IllegalClientInputException if the tower color is already used or not available.
     * @throws ChangeTurnException after setting the tower color to the player to change turn.
     */
    public void visitForModify(TowerColorReply message) throws IllegalClientInputException, ChangeTurnException {
        game.chooseTowerColor(game.getCurrentPlayer(), message.getPlayerColor());
    }

    /**
     * Sets the wizard to the current player, after receiving the reply from him.
     * @param message the last message received.
     * @throws IllegalClientInputException if the wizard is already used.
     * @throws ChangeTurnException after setting the wizard to the player to change turn.
     */
    public void visitForModify(WizardReply message) throws IllegalClientInputException, ChangeTurnException {
        game.chooseWizard(game.getCurrentPlayer(), message.getPlayerWizard());
    }

    /**
     * Allows the current player to play an assistant card, after receiving the reply from him.
     * @param message the last message received.
     * @throws IllegalClientInputException if the assistant card is already used.
     * @throws ChangeTurnException after setting current player's last assistant card played to change turn.
     */
    public void visitForModify(AssistantCardReply message) throws IllegalClientInputException, ChangeTurnException {
        game.chooseAssistantCard(game.getCurrentPlayer(), message.getChosenAssistantCard());
    }

    /**
     * Allows the current player to move a student from entrance to his dining room, after receiving the reply from him.
     * @param message the last message received.
     * @throws IllegalClientInputException if there aren't students of the specified color in current player's entrance.
     * @throws LastMoveException after moving the third student in two players game mode, the fourth in three players game mode.
     */
    public void visitForModify(MoveStudentOntoDiningRoomMessage message) throws LastMoveException, IllegalClientInputException {
        game.executeActionMoveStudentToDiningRoom(message.getStudent());
    }

    /**
     * Allows the current player to move a student from entrance to a specified island, after receiving the reply from him.
     * @param message the last message received.
     * @throws IllegalClientInputException if there aren't students of the specified color in current player's entrance or the island doesn't exist.
     * @throws LastMoveException after moving the third student in two players game mode, the fourth in three players game mode.
     */
    public void visitForModify(MoveStudentOntoIslandMessage message) throws LastMoveException, IllegalClientInputException {
        game.executeActionMoveStudentToIsland(message.getStudent(), message.getIslandID());
    }

    /**
     * Allows the current player to move mother nature with the specified number of steps, after receiving the reply from him.
     * @param message the last message received.
     * @throws IllegalClientInputException if the number of steps are uncorrected.
     * @throws EndGameException if a player moved his last tower onto the island with mother nature or the island with mother nature merged with another island and, after that, remain only three groups of islands.
     * @throws ChangeTurnException if the cloud tiles are not fully filled, and so the current player didn't have to draw students from them.
     */
    public void visitForModify(MotherNatureReply message) throws IllegalClientInputException, EndGameException, ChangeTurnException {
        game.moveMotherNature(message.getNumSteps());
    }

    /**
     * Allows the current player to choose a cloud tile to draw students from it, after receiving the reply from him.
     * @param message the last message received.
     * @throws IllegalClientInputException if the cloud tile is already empty or not available.
     * @throws EndGameException if there aren't any assistant cards or the bag is empty.
     * @throws ChangeTurnException when the current player has already chosen one of the available cloud tiles.
     */
    public void visitForModify(CloudTileReply message) throws EndGameException, IllegalClientInputException, ChangeTurnException {
        game.chooseCloudTile(game.getCloudTileById(message.getSelectedCloudTileID()));
    }

    /**
     * Allows the current player to play a character card, after receiving the request from him.
     * If the character card requested from the player needs some parameters to build the effect, it asks them.
     * @param message the last message received.
     * @throws IllegalClientInputException if the specified character card is not available or the player has already played one.
     * @throws EndGameException if the bag is empty after drawing students from it and deposited them on a character card.
     * @throws CharacterCardWithParametersException if the specified character card needs some parameters to build the effect.
     */
    public void visitForModify(UseCharacterCardRequest message) throws IllegalClientInputException, CharacterCardWithParametersException, EndGameException {
        game.useCharacterCard(game.getCurrentPlayer(),message.getCharacterCardID());
        // If the player has not played a character card ability yet, set true the attribute that indicates so.
        // Otherwise, an IllegalClientInputException is thrown.
        game.getCurrentPlayer().setTrueHasAlreadyPlayedACharacterCard();
        switch (message.getCharacterCardID()){
            case 2:
                game.applyCharacterCardEffect(message.getCharacterCardID(), new CardEffect02(game.getCurrentPlayer(), game.getProfessorsManager(), game.getProfessors()));
                break;
            case 4:
                game.applyCharacterCardEffect(message.getCharacterCardID(), new CardEffect04(game.getIslandManager().getMotherNature()));
                break;
            case 6:
                game.applyCharacterCardEffect(message.getCharacterCardID(), new CardEffect06(game.getIslandManager().getIslands()));
                break;
            case 8:
                game.applyCharacterCardEffect(message.getCharacterCardID(), new CardEffect08(game.getIslandManager().getIslands(), game.getCurrentPlayer()));
                break;
            default:
                Server.LOGGER.severe("ERROR");
        }
    }

    /**
     * Builds the effect for the character card 01.
     * @param message the last message received.
     * @throws IllegalClientInputException if one of the parameters are uncorrected.
     * @throws EndGameException if the bag is empty after drawing a student from it to deposit on the character card.
     */
    public void visitForModify(CharacterCard01Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(1, new CardEffect01(game.getCharacterCardByID(1), message.getStudent(), game.getIslandManager().getIslandByID(message.getIslandID()), game.getBag()));
    }

    /**
     * Builds the effect for the character card 03.
     * @param message the last message received.
     * @throws IllegalClientInputException if the island doesn't exist.
     * @throws EndGameException if a player moved his last tower onto the island with mother nature or an island merged with another one and remains only three groups of islands.
     */
    public void visitForModify(CharacterCard03Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(3, new CardEffect03(game.getIslandManager().getIslandByID(message.getIslandID()), game.getIslandManager(), game.getPlayers(), game.getProfessors()));
    }

    /**
     * Builds the effect for the character card 05.
     * @param message the last message received.
     * @throws IllegalClientInputException if the island doesn't exist.
     * @throws EndGameException if a player moved his last tower onto the island with mother nature or an island merged with another one and remains only three groups of islands.
     */
    public void visitForModify(CharacterCard05Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(5, new CardEffect05(game.getIslandManager().getIslandByID(message.getIslandID()), game.getCharacterCardByID(5)));
    }

    /**
     * Builds the effect for the character card 07.
     * @param message the last message received.
     * @throws IllegalClientInputException if one of the parameters are uncorrected.
     * @throws EndGameException this exception is not launched in this method.
     */
    public void visitForModify(CharacterCard07Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(7, new CardEffect07(game.getCharacterCardByID(7),message.getStudentsOnCard(), message.getFromCard(), message.getFromSchoolBoard(), game.getCurrentPlayer().getSchoolBoard()));
    }

    /**
     * Builds the effect for the character card 09.
     * @param message the last message received.
     * @throws IllegalClientInputException this exception is not launched in this method.
     * @throws EndGameException if a player moved his last tower onto the island with mother nature or an island merged with another one and remains only three groups of islands.
     */
    public void visitForModify(CharacterCard09Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(9, new CardEffect09(game.getIslandManager().getIslands(), message.getColor()));
    }

    /**
     * Builds the effect for the character card 10.
     * @param message the last message received.
     * @throws IllegalClientInputException if one of the parameters are uncorrected.
     * @throws EndGameException this exception is not launched in this method.
     */
    public void visitForModify(CharacterCard10Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(10, new CardEffect10(message.getFromEntrance(), message.getFromDiningRoom(), game.getCurrentPlayer().getSchoolBoard()));
    }

    /**
     * Builds the effect for the character card 11.
     * @param message the last message received.
     * @throws IllegalClientInputException if the color of the student is uncorrected.
     * @throws EndGameException if the bag is empty after drawing a student from it to deposit on the character card.
     */
    public void visitForModify(CharacterCard11Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(11, new CardEffect11(message.getStudent(), game.getCurrentPlayer().getSchoolBoard(), game.getBag(), game.getCharacterCardByID(11)));
        game.getProfessorsManager().updateProfessors(game.getCurrentPlayer(), message.getStudent(), game.getProfessors());
    }

    /**
     * Builds the effect for the character card 12.
     * @param message the last message received.
     * @throws IllegalClientInputException this exception is not launched.
     * @throws EndGameException this exception is not launched.
     */
    public void visitForModify(CharacterCard12Reply message) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(12, new CardEffect12(message.getStudent(), game.getPlayers(), game.getBag()));
    }
}
