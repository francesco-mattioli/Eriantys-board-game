package it.polimi.ingsw.triton.launcher.model;

public class Wallet {

    private int value = 0;

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
