package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorStrategyWithEffect;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect02Test {
    private ProfessorsManager professorsManager;
    private Player p1;
    private Player p2;
    private Player[] professors;

    @BeforeEach
    void setUp() {
        p1 = new Player("playerTest1");
        p2 = new Player("playerTest2");
        p1.setSchoolBoard(TowerColor.BLACK, 2);
        p2.setSchoolBoard(TowerColor.WHITE, 2);
        professorsManager = new ProfessorsManager();
        professorsManager.setProfessorStrategy(new ProfessorStrategyWithEffect());
        professors = new Player[5];
        professors[Color.GREEN.ordinal()] = p1;
        professors[Color.RED.ordinal()] = p2;
        professors[Color.YELLOW.ordinal()] = p1;
        professors[Color.PINK.ordinal()] = p2;
        professors[Color.BLUE.ordinal()] = p1;
    }

    @AfterEach
    void tearDown() {
        p1 = null;
        p2 = null;
        professors = null;
        professorsManager = null;
    }

    /**
     * Checks if a professor goes to the current player's school board (who is using the effect) when players have
     * the same number of students of a certain color.
     */
    @Test
    void testChangeProfessorWithSameNumberOfStudents() {
        CardEffect02 cardEffect02 = new CardEffect02 (professorsManager);
        cardEffect02.execute();
        assertEquals(p1, professors[Color.RED.ordinal()]);
    }

    /**
     * Checks if a professor remains to the current owner's school board when he has
     * more students than the player who is using the effect.
     */
    @Test
    void testNotChangeProfessorWhenAPlayerHasMoreStudents() {
        try {
            p2.getSchoolBoard().addStudentIntoDiningRoom(Color.RED);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        CardEffect02 cardEffect02 = new CardEffect02(professorsManager);
        cardEffect02.execute();
        assertEquals(p2, professors[Color.RED.ordinal()]);
    }
}