package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

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
     * Tests if the method launches an exception when the player has not blue students in the entrance.
     */
    @Test
    void checksNotBlueStudentsInEntrance(){
        player.getSchoolBoard().getEntrance()[Color.GREEN.ordinal()]++;
        player.getSchoolBoard().getEntrance()[Color.RED.ordinal()]++;
        assertThrows(IllegalClientInputException.class, ()->new MoveStudentIntoDiningRoom(color, player, generalCoinSupply).execute());
    }

    /**
     * Tests if the dining room corresponding to blue color is correctly updated.
     */
    @Test
    void checksBlueStudentAddedInEntranceNotMultipleThree(){
        player.getSchoolBoard().getEntrance()[color.ordinal()]++;
        int oldBlueStudents = player.getSchoolBoard().getDiningRoom()[color.ordinal()];
        try {
            new MoveStudentIntoDiningRoom(color, player, generalCoinSupply).execute();
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);   //The test doesn't enter here.
        }
        assertEquals(oldBlueStudents + 1, player.getSchoolBoard().getDiningRoom()[color.ordinal()]);
    }

    /**
     * Tests if the player's wallet is increased when he puts a student in a position which
     * is multiple of three and the supply is not empty.
     */
    @Test
    void checksUpdateWalletWhenMultipleThreeAndSupplyNotEmpty(){
        player.getSchoolBoard().getDiningRoom()[color.ordinal()]++;
        player.getSchoolBoard().getDiningRoom()[color.ordinal()]++;
        player.getSchoolBoard().getEntrance()[color.ordinal()]++;
        int oldWallet = player.getWallet().getValue();
        try {
            new MoveStudentIntoDiningRoom(color, player, generalCoinSupply).execute();
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);    //The test doesn't enter here.
        }
        assertEquals(oldWallet + 1, player.getWallet().getValue());
    }

    /**
     * Tests if the general coin supply is decreased when the player
     * puts a student in a position which is multiple of three and the supply is not empty.
     */
    @Test
    void checksUpdateSupplyWhenMultipleThreeAndSupplyNotEmpty(){
        player.getSchoolBoard().getDiningRoom()[color.ordinal()]++;
        player.getSchoolBoard().getDiningRoom()[color.ordinal()]++;
        player.getSchoolBoard().getEntrance()[color.ordinal()]++;
        int oldSupply = generalCoinSupply.getCoinsAmount();
        try {
            new MoveStudentIntoDiningRoom(color, player, generalCoinSupply).execute();
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);    //The test doesn't enter here.
        }
        assertEquals(oldSupply - 1, generalCoinSupply.getCoinsAmount());
    }

    /**
     * Tests if the general coin supply is not updated when the player
     * puts a student in a position which is multiple of three and the supply is already empty.
     */
    @Test
    void checksUpdateSupplyWhenMultipleThreeAndSupplyEmpty(){
        player.getSchoolBoard().getDiningRoom()[color.ordinal()]++;
        player.getSchoolBoard().getDiningRoom()[color.ordinal()]++;
        player.getSchoolBoard().getEntrance()[color.ordinal()]++;
        for(int i = 0; i < 3; i++){
            generalCoinSupply.decrement();
        }
        int oldSupply = generalCoinSupply.getCoinsAmount();
        try {
            new MoveStudentIntoDiningRoom(color, player, generalCoinSupply).execute();
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);    //The test doesn't enter here.
        }
        assertEquals(oldSupply, generalCoinSupply.getCoinsAmount());
    }
}
