package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.io.Serializable;

public class CardEffect10 implements CardEffect, Serializable {
    private final int[] fromEntrance;
    private final int[] fromDiningRoom;
    private final Player player;
    private final GeneralCoinSupply generalCoinSupply;
    private final int[] oldDiningRoom;

    /**
     * @param fromEntrance students to take from entrance.
     * @param fromDiningRoom students to take from dining room.
     * @param player the player who played the character card.
     * @param generalCoinSupply the coin supply of the game.
     */
    public CardEffect10(int[] fromEntrance, int[] fromDiningRoom, Player player, GeneralCoinSupply generalCoinSupply){
        this.fromEntrance = fromEntrance;
        this.fromDiningRoom = fromDiningRoom;
        this.player = player;
        this.generalCoinSupply = generalCoinSupply;
        this.oldDiningRoom = player.getSchoolBoard().getDiningRoom().clone();
    }

    /**
     * This method swaps a maximum of three students from the dining room to the entrance.
     */
    @Override
    public void execute() throws IllegalClientInputException, EmptyGeneralCoinSupplyException {
        removeStudentsFromDiningRoom();
        removeStudentsFromEntrance();
        addStudentsIntoDiningRoom();
        addStudentsIntoEntrance();
        checkPositionForWallet();
    }


    /**
     * This method remove the selected students from the dining room.
     * @throws IllegalClientInputException if the number of students to remove from dining room is uncorrected.
     */
    private void removeStudentsFromDiningRoom() throws IllegalClientInputException {
        for (int i = 0; i < player.getSchoolBoard().getDiningRoom().length; i++){
            for (int j = 0; j < fromDiningRoom.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal()){
                    if (fromDiningRoom[j] <= player.getSchoolBoard().getDiningRoom()[i]){
                        player.getSchoolBoard().getDiningRoom()[i] -= fromDiningRoom[j];
                    }
                    else throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE);
                }

            }
        }
    }


    /**
     * This method remove the selected students from the entrance.
     * @throws IllegalClientInputException if the number of students to remove from entrance is uncorrected.
     */
    private void removeStudentsFromEntrance() throws IllegalClientInputException {
        for (int i = 0; i < player.getSchoolBoard().getEntrance().length; i++){
            for (int j = 0; j < fromEntrance.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal()){
                    if (fromEntrance[j] <= player.getSchoolBoard().getEntrance()[i]){
                        player.getSchoolBoard().getEntrance()[i] -= fromEntrance[j];
                    }
                    else throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE);
                }
            }
        }
    }

    /**
     * This method adds the students taken from the entrance to the dining room.
     */
    private void addStudentsIntoDiningRoom() {
        for (int i = 0; i < player.getSchoolBoard().getDiningRoom().length; i++){
            for (int j = 0; j < fromEntrance.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal()) {
                    player.getSchoolBoard().getDiningRoom()[i] += fromEntrance[j];
                }
            }
        }
    }

    /**
     * This method adds the students taken from the dining room to the entrance.
     */
    private void addStudentsIntoEntrance(){
        for (int i = 0; i < player.getSchoolBoard().getEntrance().length; i++){
            for (int j = 0; j < fromDiningRoom.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    player.getSchoolBoard().getEntrance()[i] += fromDiningRoom[j];
            }
        }
    }

    /**
     * Checks if the player moved some students in positions that allow him to receive an additional coin.
     * @throws EmptyGeneralCoinSupplyException if the general coin supply is empty and so, the player will not receive
     * an additional coin.
     */
    private void checkPositionForWallet() throws EmptyGeneralCoinSupplyException {
        for (int i = 0; i < player.getSchoolBoard().getDiningRoom().length; i++){
            for(int index = oldDiningRoom[i]; index < player.getSchoolBoard().getDiningRoom()[i]; index++){
                if(player.getSchoolBoard().getAvailableCoins()[Color.values()[i].ordinal()][index]){
                    generalCoinSupply.decrement();
                    player.getWallet().increaseValue();
                    player.getSchoolBoard().getAvailableCoins()[Color.values()[i].ordinal()][index] = false;
                }
            }
        }
    }
}
