package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.Color;

public class CharacterCard {
    protected int id;
    protected int cost;
    protected int[] students;
    protected int noEntryTiles;
    protected Bag bag;
    private CardEffect cardEffect;

    public CharacterCard(int id, int cost, int noEntryTiles, Bag bag) {
        this.id = id;
        this.cost = cost;
        this.students = new int[5];
        this.noEntryTiles = noEntryTiles;

        // if the card has is id 1, it is necessary to add 4 students on it
        if (id == 1) {
            for (int i = 0; i < 4; i++) {
                students[bag.drawStudent().ordinal()]++;
            }
        }
    }

    public void increaseCost() {
        this.cost++;
    }

    public void executeEffect(CardEffect cardEffect) {
        cardEffect.execute();
    }

    /**
     * @param student to draw from the CharacterCard
     * @return the color of the drawn student
     */
    public Color drawStudent(Color student) {
        if (students[student.ordinal()] < 1) {
            throw new IllegalArgumentException("this student is not present on the CharacterCard");
        } else {
            // This array contains the students present on the CharacterCard
            // Decrements the counter in the position corresponding to the drawn student
            students[student.ordinal()]--;
            return student;
        }

    }

    public int getCost() {
        return cost;
    }

    public int[] getStudents() {
        return students;
    }
}
