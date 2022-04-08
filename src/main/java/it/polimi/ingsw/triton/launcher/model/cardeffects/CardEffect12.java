package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.Player;

import java.util.ArrayList;

public class CardEffect12 implements CardEffect{
    private Color student;
    private ArrayList<Player> players;
    private Bag bag;

    /**
     *
     * @param student color of the students to eliminate
     * @param players
     * @param bag to put the removed students
     */
    public CardEffect12(Color student, ArrayList<Player> players, Bag bag){
        this.student = student;
        this.players = players;
        this.bag = bag;
    }

    /**
     * for the chosen color the method eliminates the students of that color from each player's dining room.
     * if a player has less than three students in his dining room, the method remove all the students.
     */
    @Override
    public void execute() {
        for (Player player: players) {
            if (player.getSchoolBoard().getDiningRoom()[student.ordinal()] >= 3){
                for (int i = 0; i < 3; i++)
                    bag.addStudent(student);
                player.getSchoolBoard().getDiningRoom()[student.ordinal()] = player.getSchoolBoard().getDiningRoom()[student.ordinal()]-3;
            }
            else {
                for (int i = 0; i < player.getSchoolBoard().getDiningRoom()[student.ordinal()]; i++)
                    bag.addStudent(student);
                player.getSchoolBoard().getDiningRoom()[student.ordinal()] = 0;
            }

        }
    }
}
