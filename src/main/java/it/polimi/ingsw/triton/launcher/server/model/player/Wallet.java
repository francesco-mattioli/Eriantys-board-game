package it.polimi.ingsw.triton.launcher.server.model.player;

import java.io.Serializable;

public class Wallet implements Serializable {

    private int value;

    /**
     * Sets the value of the wallet at 0
     */
    public Wallet() {
        this.value = 0;
    }

    /**
     * Increases the amount of coins of wallet.
     */
    public void increaseValue(){
        value++;
    }

    public void decrease(int amount){
        value -= amount;
    }
    /**
     * @return number of coins in the wallet.
     */
    public int getValue() {
        return value;
    }

}
