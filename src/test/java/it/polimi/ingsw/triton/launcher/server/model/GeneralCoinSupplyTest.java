package it.polimi.ingsw.triton.launcher.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
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
        generalCoinSupply.decrement();
        assertEquals(oldAmount - 1, generalCoinSupply.getCoinsAmount());
    }

    /**
     * Tests if returns true when the supply is empty.
     */
    @Test
    void isEmptyTest() {
        for(int i = 0; i < 5; i++){
            generalCoinSupply.decrement();
        }
        assertTrue(generalCoinSupply.isEmpty());
    }

    /**
     * Tests if the method launches an exception when the supply is empty.
     */
    @Test
    void decrementWhenTheSupplyIsEmpty(){
        for(int i = 0; i < 5; i++){
            generalCoinSupply.decrement();
        }
        assertThrows(NoSuchElementException.class, ()-> generalCoinSupply.decrement());
    }
}