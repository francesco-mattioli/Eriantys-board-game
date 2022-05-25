package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
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

class ExpertMoveStudentIntoDiningRoomTest {
    private Player player;
    private GeneralCoinSupply generalCoinSupply;

    @BeforeEach
    void setUp() {
        player = new Player("playerTest");
        player.setSchoolBoard(TowerColor.BLACK, 2);
        generalCoinSupply = new GeneralCoinSupply(3);
    }

    @AfterEach
    void tearDown() {
        player = null;
        generalCoinSupply = null;
    }

    /**
     * Checks if the method throws an exception when the player has not
     * any students of the color he selected in his entrance.
     */
    @Test
    void moveStudentWithColorIncorrect(){
        assertThrows(IllegalClientInputException.class, () -> new ExpertMoveStudentIntoDiningRoom(Color.BLUE, player, generalCoinSupply).execute());
    }

    /**
     * Checks if the method throws an exception when the player's entrance
     * is empty.
     */
    @Test
    void moveStudentWhenEntranceIsEmpty(){
        player.getSchoolBoard().addStudentIntoEntrance(Color.YELLOW);
        assertThrows(IllegalClientInputException.class, () -> new ExpertMoveStudentIntoDiningRoom(Color.BLUE, player, generalCoinSupply).execute());
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
            new ExpertMoveStudentIntoDiningRoom(Color.BLUE, player, generalCoinSupply).execute();
        } catch (IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(oldNumberStudents - 1, Arrays.stream(player.getSchoolBoard().getEntrance()).sum());
    }

    /**
     * Checks if the wallet is not updated if the position of the student is not multiple of three.
     */
    @Test
    void checkUpdatedWalletWhenNotMultipleOfThree(){
        int oldWallet = player.getWallet().getValue();
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        try {
            new ExpertMoveStudentIntoDiningRoom(Color.BLUE, player, generalCoinSupply).execute();
        } catch (IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(oldWallet, player.getWallet().getValue());
    }

    /**
     * Checks if the wallet is updated correctly.
     */
    @Test
    void checkUpdatedWalletWhenMultipleOfThree(){
        int oldWallet = player.getWallet().getValue();
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        for(int i = 0; i < 3; i++){
            try {
                new ExpertMoveStudentIntoDiningRoom(Color.BLUE, player, generalCoinSupply).execute();
            } catch (IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(oldWallet + 1, player.getWallet().getValue());
    }

    /**
     * Checks if the wallet is not increased if the general coin supply is empty.
     */
    @Test
    void testNotUpdateWalletIfSupplyIsEmpty(){
        int oldWallet = player.getWallet().getValue();
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        GeneralCoinSupply newGeneralCoinSupply = new GeneralCoinSupply(0);
        for(int i = 0; i < 2; i++){
            try {
                new ExpertMoveStudentIntoDiningRoom(Color.BLUE, player, newGeneralCoinSupply).execute();
            } catch (IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(EmptyGeneralCoinSupplyException.class, () -> new ExpertMoveStudentIntoDiningRoom(Color.BLUE, player, newGeneralCoinSupply).execute());
        assertEquals(oldWallet, player.getWallet().getValue());
    }

    /**
     * Checks if the wallet is updated correctly.
     */
    @Test
    void checkUpdatedGeneralCoinsSupply(){
        int oldSupply = generalCoinSupply.getCoinsAmount();
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        player.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        for(int i = 0; i < 3; i++){
            try {
                new ExpertMoveStudentIntoDiningRoom(Color.BLUE, player, generalCoinSupply).execute();
            } catch (IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(oldSupply - 1, generalCoinSupply.getCoinsAmount());
    }
}