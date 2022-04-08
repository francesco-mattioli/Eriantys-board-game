package it.polimi.ingsw.triton.launcher.model;

public class Wallet {

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

    /**
     * @return number of coins in the wallet.
     */
    public int getValue() {
        return value;
    }

}
