package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CardEffect10 implements CardEffect, Serializable {
    private final int[] fromEntrance;
    private final int[] fromDiningRoom;
    private final Player player;
    private final GeneralCoinSupply generalCoinSupply;
    private final int[] oldDiningRoom;
    private final ProfessorsManager professorsManager;
    private final Player[] professors;
    private final List<Player> players;

    /**
     * @param fromEntrance      students to take from entrance.
     * @param fromDiningRoom    students to take from dining room.
     * @param player            the player who played the character card.
     * @param generalCoinSupply the coin supply of the game.
     * @param professorsManager to calculate influence.
     * @param professors        owned by players.
     * @param players           in game.
     */
    public CardEffect10(int[] fromEntrance, int[] fromDiningRoom, Player player, GeneralCoinSupply generalCoinSupply, ProfessorsManager professorsManager, Player[] professors, List<Player> players) {
        this.fromEntrance = fromEntrance;
        this.fromDiningRoom = fromDiningRoom;
        this.player = player;
        this.generalCoinSupply = generalCoinSupply;
        this.oldDiningRoom = player.getSchoolBoard().getDiningRoom().clone();
        this.professorsManager = professorsManager;
        this.professors = professors;
        this.players = players;
    }

    /**
     * This method swaps a maximum of three students from the dining room to the entrance.
     */
    @Override
    public void execute() throws IllegalClientInputException, EmptyGeneralCoinSupplyException {
        if (Arrays.stream(fromDiningRoom).sum() > 2 || Arrays.stream(fromDiningRoom).sum() != Arrays.stream(fromEntrance).sum())
            throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE);
        else {
            removeStudents(player.getSchoolBoard().getDiningRoom(), fromDiningRoom);
            removeStudents(player.getSchoolBoard().getEntrance(), fromEntrance);
            addStudentsInto(player.getSchoolBoard().getDiningRoom(), fromEntrance);
            addStudentsInto(player.getSchoolBoard().getEntrance(), fromDiningRoom);
            checkForProfessors();
            checkPositionForWallet();
        }
    }


    /**
     * This method remove the selected students (in studentToRemove array) from the source array (entrance or dining room).
     *
     * @param source           entrance or dining room.
     * @param studentsToRemove students to remove.
     * @throws IllegalClientInputException if the number of students to remove from source is incorrect.
     */
    public void removeStudents(int[] source, int[] studentsToRemove) throws IllegalClientInputException {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < studentsToRemove.length; j++) {
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    removeOrThrowException(source, studentsToRemove, i, j);
            }
        }
    }

    /**
     * This method removes students from source if present, otherwise it throws an exception.
     *
     * @param source           entrance or dining room.
     * @param studentsToRemove students to remove.
     * @param i                source color.
     * @param j                color to remove.
     * @throws IllegalClientInputException if students to remove are more than available ones.
     */
    private void removeOrThrowException(int[] source, int[] studentsToRemove, int i, int j) throws IllegalClientInputException {
        if (studentsToRemove[j] <= source[i]) {
            source[i] -= studentsToRemove[j];
        } else throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE);
    }

    /**
     * This method adds the students taken from the studentsToAdd array into the destination array (entrance or dining room)
     *
     * @param destination   entrance or dining room.
     * @param studentsToAdd students to add into the destination.
     */
    public void addStudentsInto(int[] destination, int[] studentsToAdd) {
        for (int i = 0; i < destination.length; i++) {
            for (int j = 0; j < studentsToAdd.length; j++) {
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    destination[i] += studentsToAdd[j];
            }
        }
    }

    /**
     * Checks if the player moved some students in positions that allow him to receive an additional coin.
     *
     * @throws EmptyGeneralCoinSupplyException if the general coin supply is empty and so, the player will not receive
     *                                         an additional coin.
     */
    private void checkPositionForWallet() throws EmptyGeneralCoinSupplyException {
        for (int i = 0; i < player.getSchoolBoard().getDiningRoom().length; i++) {
            for (int index = oldDiningRoom[i]; index < player.getSchoolBoard().getDiningRoom()[i]; index++) {
                if (player.getSchoolBoard().getAvailableCoins()[Color.values()[i].ordinal()][index]) {
                    generalCoinSupply.decrement();
                    player.getWallet().increaseValue();
                    player.getSchoolBoard().getAvailableCoins()[Color.values()[i].ordinal()][index] = false;
                }
            }
        }
    }

    private void checkForProfessors() {
        for (int i = 0; i < fromEntrance.length; i++) {
            int changedNumberStudents = fromEntrance[i] - fromDiningRoom[i];
            if (changedNumberStudents > 0)
                professorsManager.updateProfessorsForAddInDiningRoom(player, Color.values()[i], professors);
            else if (changedNumberStudents < 0) {
                professorsManager.updateProfessorsForRemoveFromDiningRoom(players, Color.values()[i], professors);
            }
        }
    }
}