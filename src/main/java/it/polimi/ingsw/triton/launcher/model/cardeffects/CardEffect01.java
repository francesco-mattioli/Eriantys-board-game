package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.Island;

public class CardEffect01 implements CardEffect {
    private final Island island;
    private final Color student;
    private final CharacterCard characterCard;

    /**
     * @param student to draw from the Card.
     * @param island  where the student must be moved.
     */
    public CardEffect01(CharacterCard characterCard, Color student, Island island) {
        this.characterCard = characterCard;
        this.island = island;
        this.student = student;
    }

    /**
     * This method adds a student onto an island.
     * The chosen student was on the CharacterCard.
     * The island is passed through the constructor.
     */
    @Override
    public void execute() {
        island.addStudent(characterCard.drawStudent(student));
    }
}
