package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.io.Serializable;

public class CardEffect11 implements CardEffect, Serializable {
    private final Color student;
    private SchoolBoard schoolBoard;
    private Bag bag;
    private CharacterCard characterCard;

    /**
     * @param student to draw from the Card.
     * @param schoolBoard where the student must be moved.
     * @param bag to draw a student.
     * @param characterCard
     */
    public CardEffect11(Color student, SchoolBoard schoolBoard, Bag bag, CharacterCard characterCard){
        this.student = student;
        this.schoolBoard = schoolBoard;
        this.bag = bag;
        this.characterCard = characterCard;

    }

    /**
     * This method adds a student in the entrance.
     * The chosen student was on the CharacterCard.
     * The schoolboard is passed through the constructor.
     */
    @Override
    public void execute() throws IllegalClientInputException {
        schoolBoard.getDiningRoom()[characterCard.drawStudent(student).ordinal()]++;
        if(!bag.isEmpty())
            characterCard.addStudent(bag.drawStudent());
    }
}
