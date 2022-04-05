package it.polimi.ingsw.triton.launcher.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void increaseValue() {
        Wallet wallet = new Wallet();
        assertEquals(0, wallet.getValue());
        wallet.increaseValue();
        assertEquals(1, wallet.getValue());
    }
}