package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorStrategyDefault;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorStrategyDefaultTest {

    private ProfessorsManager professorsManager;
    private Player player;

    @BeforeEach
    void setUp(){
        professorsManager = new ProfessorsManager();
        professorsManager.setProfessorStrategy(new ProfessorStrategyDefault());
        player = new Player("TestPlayer");
        player.setSchoolBoard(TowerColor.BLACK, 2);
    }

    @AfterEach
    void tearDown(){
        professorsManager = null;
        player = null;
    }

    /**
     * Test throws an exception if the parameter color is null
     */
    @Test
    void throwExceptionIfColorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            professorsManager.updateProfessors(player,null);
        });
    }


    /**
     * Tests if update correctly the influence when the professor is not taken
     */
    @Test
    void updateProfessorInfluenceWhenPlayerIsNull(){
        player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=1;
        professorsManager.updateProfessors(player,Color.BLUE);
        assertTrue(player.equals(professorsManager.getProfessors()[Color.BLUE.ordinal()]));
    }

    /**
     * Tests if update correctly the influence when the professor is taken
     */
    @Test
    void updateProfessorInfluenceWhenPlayerIsNotNull(){
        Player playerWithProfessor = new Player("TestPlayer1");
        playerWithProfessor.setSchoolBoard(TowerColor.WHITE, 2);
        playerWithProfessor.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=1;
        professorsManager.getProfessors()[Color.BLUE.ordinal()] = playerWithProfessor;
        player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=2;
        professorsManager.updateProfessors(player,Color.BLUE);
        assertTrue(player.equals(professorsManager.getProfessors()[Color.BLUE.ordinal()]));
    }
}