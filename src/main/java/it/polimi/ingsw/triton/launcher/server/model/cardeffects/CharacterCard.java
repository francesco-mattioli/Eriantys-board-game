package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.Utility;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.io.Serializable;

public class CharacterCard implements Serializable {
    protected int id;
    protected int cost;
    protected int[] students;
    protected int noEntryTiles;
    protected Bag bag;
    private static final String CHARACTER_CARD = "\n\tCharacterCard ";
    private static final String COST_OF_CHARACTER_CARD = ": { Cost: ";


    /**
     * This constructor creates the card and places students on it if necessary.
     *
     * @param id           the identifier of the card.
     * @param cost         the cost of the card.
     * @param noEntryTiles the number of NoEntryTiles on the card.
     * @param bag          the bag useful to drawStudents and place them on the card.
     */
    public CharacterCard(int id, int cost, int noEntryTiles, Bag bag) {
        this.id = id;
        this.cost = cost;
        this.students = new int[5];
        this.noEntryTiles = noEntryTiles;
        this.bag = bag;
        setupCharacterCard(id);
    }

    /**
     * This method sets the card up: places students or noEntryTiles on it.
     *
     * @param id the id of the card.
     */
    private void setupCharacterCard(int id) {
        if (id == 1 || id == 11) {
            for (int i = 0; i < 4; i++)
                students[bag.drawStudent().ordinal()]++;
        }
        if (id == 7) {
            for (int i = 0; i < 6; i++)
                students[bag.drawStudent().ordinal()]++;
        }
        if (id == 5)
            noEntryTiles = 4;
    }

    /**
     * This method increases the cost of the card each time a player chooses it.
     */
    public void increaseCost() {
        this.cost++;
    }

    /**
     * @param cardEffect is the effect that will be executed when the method is called.
     * @throws EndGameException when the game ends.
     * @throws IllegalClientInputException
     */
    public void executeEffect(CardEffect cardEffect) throws EndGameException, IllegalClientInputException, EmptyGeneralCoinSupplyException {
        cardEffect.execute();
    }

    /**
     * @param student the color of the student to draw from the CharacterCard.
     * @return the color of the drawn student.
     */
    public Color drawStudent(Color student) throws IllegalClientInputException {
        if (student == null)
            throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE);
        if (students[student.ordinal()] < 1)
            throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE);
        else {
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

    public int getId() {
        return id;
    }

    public int getNoEntryTiles() {
        return noEntryTiles;
    }

    public void addStudent(Color color) {
        students[color.ordinal()]++;
    }

    public void addNoEntryTile() {
        noEntryTiles++;
    }

    /**
     * @return true if the card needs some parameters (id 1,3,5,7,9,10,11,12), false otherwise.
     */
    public boolean hasParameters() {
        return id != 2 && id != 4 && id != 6 && id != 8;
    }

    public String toString() {
        if (id == 1 || id == 7 || id == 11) {
            return CHARACTER_CARD + id + COST_OF_CHARACTER_CARD + cost + ", Students -> " + Utility.printColoredStudents(students) + " }";
        }
        if (id == 5) {
            return CHARACTER_CARD + id + COST_OF_CHARACTER_CARD + cost + ", No Entry Tiles: " + noEntryTiles + " }";
        } else return CHARACTER_CARD + id + COST_OF_CHARACTER_CARD + cost + " }";
    }


    public String studentsToString() {
        return "students on the card = " + Utility.printColoredStudents(students);
    }
}
