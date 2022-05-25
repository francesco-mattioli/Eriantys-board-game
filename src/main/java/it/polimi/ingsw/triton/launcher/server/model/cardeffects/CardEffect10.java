package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.io.Serializable;

public class CardEffect10 implements CardEffect, Serializable {
    private final int[] fromEntrance;
    private final int[] fromDiningRoom;
    private final SchoolBoard schoolBoard;

    /**
     * @param fromEntrance   students to take from entrance.
     * @param fromDiningRoom students to take from dining room.
     * @param schoolBoard    of the player who played the character card.
     */
    public CardEffect10(int[] fromEntrance, int[] fromDiningRoom, SchoolBoard schoolBoard) {
        this.fromEntrance = fromEntrance;
        this.fromDiningRoom = fromDiningRoom;
        this.schoolBoard = schoolBoard;
    }

    /**
     * This method swaps a maximum of three students from the dining room to the entrance.
     */
    @Override
    public void execute() throws IllegalClientInputException {
        removeStudents(schoolBoard.getDiningRoom(), fromDiningRoom);
        removeStudents(schoolBoard.getEntrance(), fromEntrance);
        addStudentsInto(schoolBoard.getDiningRoom(), fromEntrance);
        addStudentsInto(schoolBoard.getEntrance(), fromDiningRoom);


    }


    /**
     * This method remove the selected students (in studentToRemove array) from the source array (entrance or dining room).
     * @throws IllegalClientInputException if the number of students to remove from source is incorrect.
     */
    public void removeStudents(int[] source, int[] studentsToRemove) throws IllegalClientInputException {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < studentsToRemove.length; j++) {
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                   removeOrThrowException(source,studentsToRemove,i,j);
            }
        }
    }

    private void removeOrThrowException(int[] source, int[] studentsToRemove,int i,int j) throws IllegalClientInputException {
        if (studentsToRemove[j] <= source[i]) {
            source[i] -= studentsToRemove[j];
        } else throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE);
    }


    /**
     * This method adds the students taken from the studentsToAdd array into the destination array (entrance or dining room)
     */
    public void addStudentsInto(int[] destination, int[] studentsToAdd) {
        for (int i = 0; i < destination.length; i++) {
            for (int j = 0; j < studentsToAdd.length; j++) {
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    destination[i] += studentsToAdd[j];
            }
        }
    }
}
