package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardEffect11Test {

    private Player player;
    private CharacterCard characterCard;
    private Bag bag;
    private Player[] professors;

    @BeforeEach
    void setUp() {
        bag = new Bag();
        bag.fillBag();
        player = new Player("TestPlayer");
        player.setSchoolBoard(TowerColor.BLACK, 2);
        characterCard = new CharacterCard(11, 0, 0, bag);
        characterCard.getStudents()[Color.BLUE.ordinal()] = 1;
        characterCard.getStudents()[Color.GREEN.ordinal()] = 0;
        professors = new Player[5];
        professors[Color.BLUE.ordinal()] = null;
        professors[Color.RED.ordinal()] = null;
        professors[Color.GREEN.ordinal()] = null;
        professors[Color.PINK.ordinal()] = null;
        professors[Color.YELLOW.ordinal()] = null;
    }

    @AfterEach
    void tearDown() {
        bag = null;
        player = null;
        characterCard = null;
    }

    /**
     * This test checks if the effect launches an exception when the player wants to move a student which
     * is not on the character card.
     */
    @Test
    void addStudentWithColorNotOnTheCard() {
        assertThrows(IllegalClientInputException.class, () -> characterCard.executeEffect(new CardEffect11(Color.GREEN, player, bag, characterCard, new GeneralCoinSupply(5), new ProfessorsManager(), professors)));
    }

    /**
     * This test checks if the effect adds only one student when the entrance has zero.
     */
    @Test
    void addStudentWhenDiningRoomHasZero() {
        try {
            characterCard.executeEffect(new CardEffect11(Color.BLUE, player, bag, characterCard, new GeneralCoinSupply(5), new ProfessorsManager(), professors));
        } catch (EndGameException | IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);
    }

    /**
     * Checks if the player's wallet is updated when he plays the character card 11, and he moves
     * a student in a position of his dining room which is multiple of three.
     */
    @Test
    void checkUpdateWalletIfMultipleOfThree() {
        int oldWallet = player.getWallet().getValue();
        try {
            player.getSchoolBoard().addStudentIntoDiningRoom(Color.BLUE);
            player.getSchoolBoard().addStudentIntoDiningRoom(Color.BLUE);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        try {
            characterCard.executeEffect(new CardEffect11(Color.BLUE, player, bag, characterCard, new GeneralCoinSupply(5), new ProfessorsManager(), professors));
        } catch (EndGameException | IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(oldWallet + 1, player.getWallet().getValue());
    }
}