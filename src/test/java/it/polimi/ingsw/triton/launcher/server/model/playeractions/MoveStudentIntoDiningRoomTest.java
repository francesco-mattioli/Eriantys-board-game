package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MoveStudentIntoDiningRoomTest {
    private Player player;

    @BeforeEach
    void setup() {
        player = new Player("Test");
        player.setSchoolBoard(TowerColor.BLACK, 2);
    }

    @AfterEach
    void tearDown() {
        player = null;
    }

    /**
     * Checks if the method throws an exception when the player has not
     * any students of the color he selected in his entrance.
     */
    @Test
    void moveStudentWithColorIncorrect(){
        assertThrows(IllegalClientInputException.class, () -> new MoveStudentIntoDiningRoom(Color.BLUE, player).execute());
    }

    /**
     * Checks if the method throws an exception when the player's entrance
     * is empty.
     */
    @Test
    void moveStudentWhenEntranceIsEmpty(){
        player.getSchoolBoard().addStudentIntoEntrance(Color.YELLOW);
        assertThrows(IllegalClientInputException.class, () -> new MoveStudentIntoDiningRoom(Color.BLUE, player).execute());
    }

    /**
     * Checks if the number of students in the entrance is decreased after
     * executing the action.
     */
    @Test
    void checkNumberStudentsEntrance(){
        player.getSchoolBoard().addStudentIntoEntrance(Color.YELLOW);
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        int oldNumberStudents = Arrays.stream(player.getSchoolBoard().getEntrance()).sum();
        try {
            new MoveStudentIntoDiningRoom(Color.BLUE, player).execute();
        } catch (IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(oldNumberStudents - 1, Arrays.stream(player.getSchoolBoard().getEntrance()).sum());
    }

    /**
     * Checks if the number of students in the dining room corresponding of the selected color
     * is increased after executing the action.
     */
    @Test
    void checkNumberStudentsDiningRoom(){
        player.getSchoolBoard().addStudentIntoEntrance(Color.YELLOW);
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        int oldNumberBlueStudents = player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()];
        try {
            new MoveStudentIntoDiningRoom(Color.BLUE, player).execute();
        } catch (IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(oldNumberBlueStudents + 1, player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);
    }
}