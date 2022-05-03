package it.polimi.ingsw.triton.launcher.server.model;

import java.util.NoSuchElementException;

public class GeneralCoinSupply {
    private int coinsAmount;

    public GeneralCoinSupply(int initialNumberCoins){
        this.coinsAmount = initialNumberCoins;
    }

    public int getCoinsAmount(){
        return coinsAmount;
    }

    public boolean isEmpty(){
        return coinsAmount == 0;
    }

    /**
     * Increments the number of coins of the supply.
     * @param coinsToAdd the number of coins to add
     */
    public void increment(int coinsToAdd){
        coinsAmount += coinsToAdd;
    }

    /**
     * Decrements by one coins the supply.
     */
    public void decrement() throws NoSuchElementException{
        if(coinsAmount > 0)
            coinsAmount--;
        else
            throw new NoSuchElementException();
    }
}
