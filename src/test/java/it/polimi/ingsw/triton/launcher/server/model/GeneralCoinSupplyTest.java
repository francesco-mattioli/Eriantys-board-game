package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneralCoinSupplyTest {
    private GeneralCoinSupply generalCoinSupply;

    @BeforeEach
    void setUp() {
        generalCoinSupply = new GeneralCoinSupply(5);
    }

    @AfterEach
    void tearDown() {
        generalCoinSupply = null;
    }

    /**
     * Tests if the supply is incremented correctly.
     */
    @Test
    void incrementTest() {
        int oldAmount = generalCoinSupply.getCoinsAmount();
        generalCoinSupply.increment(3);
        assertEquals(oldAmount + 3, generalCoinSupply.getCoinsAmount());
    }

    /**
     * Tests if the supply is decremented correctly.
     */
    @Test
    void decrementTest() {
        int oldAmount = generalCoinSupply.getCoinsAmount();
        try {
            generalCoinSupply.decrement();
        } catch (EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(oldAmount - 1, generalCoinSupply.getCoinsAmount());
    }

    /**
     * Tests if the method returns true when the supply is empty.
     */
    @Test
    void isEmptyTest() {
        for(int i = 0; i < 5; i++){
            try {
                generalCoinSupply.decrement();
            } catch (EmptyGeneralCoinSupplyException e) {
                throw new RuntimeException(e);
            }
        }
        assertTrue(generalCoinSupply.isEmpty());
    }

    /**
     * Tests if the method launches an exception when the supply is empty.
     */
    @Test
    void decrementWhenTheSupplyIsEmpty(){
        for(int i = 0; i < 5; i++){
            try {
                generalCoinSupply.decrement();
            } catch (EmptyGeneralCoinSupplyException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(EmptyGeneralCoinSupplyException.class, ()-> generalCoinSupply.decrement());
    }
}