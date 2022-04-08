package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.Player;

import java.util.ArrayList;

public class CardEffect12 implements CardEffect{
    private Color student;
    private ArrayList<Player> players;
    private Bag bag;

    public CardEffect12(Color student, ArrayList<Player> players, Bag bag){
        this.student = student;
        this.players = players;
        this.bag = bag;
    }

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
