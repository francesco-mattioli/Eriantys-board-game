package it.polimi.ingsw.triton.launcher.model;

public class SchoolBoard {
    private TowerColor towerColor;
    private int numTowers;
    private int[] entrance;
    private int[] diningRoom;

    private final int INITIAL_NUM_TOWERS = 8;

    public SchoolBoard(TowerColor towerColor){
        this.towerColor = towerColor;
        this.numTowers = INITIAL_NUM_TOWERS;
        this.entrance = new int[5];
        this.diningRoom = new int[5];
    }

    public TowerColor moveTowerOntoIsland(int dim){
        // TODO implement here
        return null;
    }

    public void moveTowerOntoSchoolBoard(int dim){
        // TODO implement here
    }

    public void addStudentIntoDiningRoom(Color color){
        // TODO implement here
    }

    public void addStudentIntoEntrance(Color color){
        entrance[color.ordinal()]++;
    }

    public int getStudentsNumber(Color color){
        // TODO implement here
        return 0;
    }

}
