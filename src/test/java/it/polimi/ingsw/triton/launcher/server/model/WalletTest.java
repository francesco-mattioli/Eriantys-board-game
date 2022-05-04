package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.player.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    private Wallet wallet;

    @BeforeEach
    void setUp(){
        wallet = new Wallet();
    }

    @AfterEach
    void tearDown(){
        wallet = null;
    }

    /**
     * Tests if wallet increases his value.
     */
    @Test
    void increaseValue() {
        int oldValue = wallet.getValue();
        wallet.increaseValue();
        assertEquals(oldValue + 1, wallet.getValue());
    }

    /**
     * Tests if wallet decreases his value.
     */
    @Test
    void decreaseValue() {
        wallet.increaseValue();
        wallet.increaseValue();
        int oldValue = wallet.getValue();
        wallet.decrease(1);
        assertEquals(oldValue -1, wallet.getValue());
    }
}