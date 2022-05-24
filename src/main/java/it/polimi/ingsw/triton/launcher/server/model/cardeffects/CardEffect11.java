package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

import java.io.Serializable;

public class CardEffect11 implements CardEffect, Serializable {
    private final Color student;
    private final Player player;
    private final Bag bag;
    private final CharacterCard characterCard;
    private final GeneralCoinSupply generalCoinSupply;

    /**
     * @param student to draw from the Card.
     * @param player the current player.
     * @param bag to draw a student.
     * @param characterCard the character card 11.
     */
    public CardEffect11(Color student, Player player, Bag bag, CharacterCard characterCard, GeneralCoinSupply generalCoinSupply){
        this.student = student;
        this.player = player;
        this.bag = bag;
        this.characterCard = characterCard;
        this.generalCoinSupply = generalCoinSupply;
    }

    /**
     * This method adds a student in the entrance.
     * The chosen student was on the CharacterCard.
     * The school board is passed through the constructor.
     */
    @Override
    public void execute() throws IllegalClientInputException, EmptyGeneralCoinSupplyException {
        player.getSchoolBoard().addStudentIntoDiningRoom(characterCard.drawStudent(student));
        if(!bag.isEmpty())
            characterCard.addStudent(bag.drawStudent());
        if(player.getSchoolBoard().getDiningRoom()[student.ordinal()] % 3 == 0){
            generalCoinSupply.decrement();
            player.getWallet().increaseValue();
        }
    }
}
