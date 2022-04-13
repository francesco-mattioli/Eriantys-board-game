package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.player.Wallet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    /**
     * Test if wallet increases his value after getValue()
     */
    @Test
    void increaseValue() {
        Wallet wallet = new Wallet();
        assertEquals(0, wallet.getValue());
        wallet.increaseValue();
        assertEquals(1, wallet.getValue());
    }
}