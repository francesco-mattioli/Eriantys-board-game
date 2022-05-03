package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect08;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.Wallet;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/*
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
     * Test if the card can be purchased by the player.
     */
/*
    @Test
    void testCardCanBePurchased() {
        for(int i = 0; i < 4; i++){
            wallet.increaseValue();
        }
        int coinsAmount = wallet.getValue();
        int cardCost = characterCard.getCost();
        UseCharacterCard ucc = new UseCharacterCard(characterCard, new CardEffect08(new ArrayList<>()), wallet);
        try {
            ucc.execute();
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        assertEquals(coinsAmount - cardCost, wallet.getValue());
    }

    /**
     * Test if the card cost is increased after using his effect.
     */
/*
    @Test
    void testCardCostIsIncreased() {
        for(int i = 0; i < 4; i++){
            wallet.increaseValue();
        }
        int oldCost = characterCard.getCost();
        UseCharacterCard ucc = new UseCharacterCard(characterCard, new CardEffect08(new ArrayList<>()), wallet);
        try {
            ucc.execute();
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        assertEquals(oldCost+1, characterCard.getCost());
    }
}

 */