package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

    @Test
    void costIsOneUnitGraterAfterIncrease() {
        Bag bag = new Bag(2);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        CharacterCard card = new CharacterCard(1,1,3,new Bag(2));

        card.increaseCost();
        assertEquals(2,card.getCost());
    }


    @Test
    void numberOfStudentsIsOneSmallerAfterDraw() {
        Bag bag = new Bag(2);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        CharacterCard card = new CharacterCard(1,1,3,new Bag(2));
        Color color=card.drawStudent(Color.BLUE);
        int[] students = card.getStudents();
        int sum=0;
        for(int i=0;i<students.length;i++){
            sum+=students[i];
        }
        assertEquals(3,sum);
    }
}