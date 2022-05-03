package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoveStudentIntoDiningRoomTest {
    private Color color;
    private Player player;
    private GeneralCoinSupply generalCoinSupply;

    @BeforeEach
    void setup() {
        player = new Player("Test");
        player.setSchoolBoard(TowerColor.BLACK, 2);
        generalCoinSupply = new GeneralCoinSupply(3);
        color = Color.BLUE;
    }

    @AfterEach
    void tearDown() {
        player = null;
        generalCoinSupply = null;
        color = null;
    }

    /**
     * Tests if the method launches an exception when the entrance of the player is empty.
     */
    @Test
    void checksEmptyEntrance(){
        assertThrows(IllegalClientInputException.class, ()->new MoveStudentIntoDiningRoom(color, player, generalCoinSupply).execute());
    }

    /**
     * Tests if the method doesn't launches an exception when the entrance of the player is not empty.
     */
    @Test
    void checksNotEmptyEntrance(){
        player.getSchoolBoard().getEntrance()[Color.BLUE.ordinal()]++;
        assertDoesNotThrow(()->new MoveStudentIntoDiningRoom(color, player, generalCoinSupply).execute());
    }

    /**
     * Tests if the method doesn't launches an exception when the entrance of the player is not empty.
     */
    @Test
    void checksNotBlueStudentsInEntrance(){
        player.getSchoolBoard().getEntrance()[Color.GREEN.ordinal()]++;
        player.getSchoolBoard().getEntrance()[Color.RED.ordinal()]++;
        assertThrows(IllegalClientInputException.class, ()->new MoveStudentIntoDiningRoom(color, player, generalCoinSupply).execute());
    }
}
