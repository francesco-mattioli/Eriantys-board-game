package it.polimi.ingsw.triton.launcher.model;

public class Bag {
    private int students[];

    public Bag() {
        this.students=new int[5];
    }

    public Color drawStudent() {
        // TODO implement here
        return null;
    }

    public void fillCloudTile(CloudTile ct) {
        // TODO implement here
    }


    public void fillBag() {
        // TODO implement here
    }


    public void addStudent(Color color) {
        students[color.ordinal()]++;
    }



}
