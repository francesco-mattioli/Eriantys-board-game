package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.model.cardeffects.CardEffect08;
import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.model.player.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UseCharacterCardTest {
    private CharacterCard characterCard;
    private CardEffect cardEffect;
    private Wallet wallet;
    private Bag bag;

    @BeforeEach
    void setup(){
        characterCard = new CharacterCard(3, 1, 0, new Bag(2));
        wallet = new Wallet();
    }

    @AfterEach
    void tearDown(){
        characterCard = null;
        wallet = null;
        bag = null;
        cardEffect = null;
    }

    /**
     * Test if the execute method throws a Runtime exception when the player has not enough coins
     * to buy the card.
     */
    @Test
    void testCardCanNotBePurchased() {
        UseCharacterCard ucc = new UseCharacterCard(characterCard, new CardEffect08(new ArrayList<>()), wallet);
        assertThrows(RuntimeException.class, ucc::execute);
    }

    /**
     * Test if the card can be purchased by the player.
     */
    @Test
    void testCardCanBePurchased() {
        for(int i = 0; i < 4; i++){
            wallet.increaseValue();
        }
        int coinsAmount = wallet.getValue();
        int cardCost = characterCard.getCost();
        UseCharacterCard ucc = new UseCharacterCard(characterCard, new CardEffect08(new ArrayList<>()), wallet);
        ucc.execute();
        assertEquals(coinsAmount - cardCost, wallet.getValue());
    }

    /**
     * Test if the card cost is increased after using his effect.
     */
    @Test
    void testCardCostIsIncreased() {
        for(int i = 0; i < 4; i++){
            wallet.increaseValue();
        }
        int oldCost = characterCard.getCost();
        UseCharacterCard ucc = new UseCharacterCard(characterCard, new CardEffect08(new ArrayList<>()), wallet);
        ucc.execute();
        assertEquals(oldCost+1, characterCard.getCost());
    }
}