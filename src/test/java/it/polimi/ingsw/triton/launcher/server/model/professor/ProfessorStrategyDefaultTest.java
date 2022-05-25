package it.polimi.ingsw.triton.launcher.server.model.professor;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorStrategyDefaultTest {
    private ProfessorsManager professorsManager;
    private Player player;
    private Player[] professors;

    @BeforeEach
    void setUp(){
        professorsManager = new ProfessorsManager();
        professorsManager.setProfessorStrategy(new ProfessorStrategyDefault());
        player = new Player("TestPlayer");
        player.setSchoolBoard(TowerColor.BLACK, 2);
        professors = new Player[5];
    }

    @AfterEach
    void tearDown(){
        professorsManager = null;
        player = null;
    }

    /**
     * Tests if the method throws an exception if the parameter color is null.
     */
    @Test
    void throwExceptionIfColorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> professorsManager.updateProfessorsForAddInDiningRoom(player,null, professors));
    }


    /**
     * Tests if the influence is updated correctly when the professor is not taken.
     */
    @Test
    void updateProfessorInfluenceWhenPlayerIsNull(){
        player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=1;
        professorsManager.updateProfessorsForAddInDiningRoom(player,Color.BLUE, professors);
        assertEquals(player, professors[Color.BLUE.ordinal()]);
    }

    /**
     * Tests if the influence is updated correctly when the professor is taken.
     */
    @Test
    void updateProfessorInfluenceWhenPlayerIsNotNull(){
        Player playerWithProfessor = new Player("TestPlayer1");
        playerWithProfessor.setSchoolBoard(TowerColor.WHITE, 2);
        playerWithProfessor.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=1;
        professors[Color.BLUE.ordinal()] = playerWithProfessor;
        player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=2;
        professorsManager.updateProfessorsForAddInDiningRoom(player,Color.BLUE, professors);
        assertEquals(player, professors[Color.BLUE.ordinal()]);
    }
}