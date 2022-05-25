package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardEffect02Test {
    private ProfessorsManager professorsManager;
    private Player p1;
    private Player p2;
    private Player[] professors;


    @BeforeEach
    void setUp() {
        p1 = new Player("TestPlayer1");
        p2 = new Player("TestPlayer2");
        p1.setSchoolBoard(TowerColor.BLACK, 2);
        p2.setSchoolBoard(TowerColor.WHITE, 2);
        p2.getSchoolBoard().getDiningRoom()[Color.RED.ordinal()] = 1;
        professorsManager = new ProfessorsManager();
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
        new CardEffect02(professorsManager).execute();
        p1.getSchoolBoard().getDiningRoom()[Color.RED.ordinal()] = 1;
        professorsManager.updateProfessorsForAddInDiningRoom(p1, Color.RED, professors);
        assertEquals(p1, professors[Color.RED.ordinal()]);
    }

    /**
     * Checks if a professor remains to the current owner's school board when he has
     * more students than the player who is using the effect.
     */
    @Test
    void testNotChangeProfessorWhenAPlayerHasMoreStudents() {
        p2.getSchoolBoard().getDiningRoom()[Color.RED.ordinal()] = 2;
        new CardEffect02(professorsManager).execute();
        p1.getSchoolBoard().getDiningRoom()[Color.RED.ordinal()] = 1;
        professorsManager.updateProfessorsForAddInDiningRoom(p1, Color.RED, professors);
        assertEquals(p2, professors[Color.RED.ordinal()]);
    }
}