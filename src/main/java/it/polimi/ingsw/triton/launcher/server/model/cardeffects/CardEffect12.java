package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

import java.io.Serializable;
import java.util.List;

public class CardEffect12 implements CardEffect, Serializable {
    private final Color student;
    private final List<Player> players;
    private final Bag bag;

    /**
     *
     * @param student color of the students to delete.
     * @param players the array of players.
     * @param bag to put the removed students.
     */
    public CardEffect12(Color student, List<Player> players, Bag bag){
        this.student = student;
        this.players = players;
        this.bag = bag;
    }

    /**
     * For the chosen color, the method deletes the students of that color from each player's dining room.
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
