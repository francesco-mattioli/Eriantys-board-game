package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorStrategyWithEffectTest {

    // first three methods are a copy of the ones present in ProfessorStrategyDefaultTest

    ProfessorsManager professorsManager;
    Player player;

    @BeforeEach
    void setUp(){
        professorsManager = new ProfessorsManager();
        professorsManager.setProfessorStrategy(new ProfessorStrategyWithEffect());
        player = new Player("TestPlayer");
        player.setSchoolBoard(TowerColor.BLACK);
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
     * when new player has more students as the previous one
     */
    @Test
    void updateProfessorInfluenceWhenGreaterThan(){
        Player playerWithProfessor = new Player("TestPlayer1");
        playerWithProfessor.setSchoolBoard(TowerColor.WHITE);
        playerWithProfessor.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=1;
        professorsManager.getProfessors()[Color.BLUE.ordinal()] = playerWithProfessor;
        player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=2;
        professorsManager.updateProfessors(player,Color.BLUE);
        assertTrue(player.equals(professorsManager.getProfessors()[Color.BLUE.ordinal()]));
    }

    /**
     * Tests if update correctly the influence when the professor is taken and
     * when new player has the same number of students as the previous one
     */
    @Test
    void updateProfessorInfluenceWhenEquals(){
        Player playerWithProfessor = new Player("TestPlayer1");
        playerWithProfessor.setSchoolBoard(TowerColor.WHITE);
        playerWithProfessor.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=1;
        professorsManager.getProfessors()[Color.BLUE.ordinal()] = playerWithProfessor;
        player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]=1;
        professorsManager.updateProfessors(player,Color.BLUE);
        assertTrue(player.equals(professorsManager.getProfessors()[Color.BLUE.ordinal()]));
    }

}