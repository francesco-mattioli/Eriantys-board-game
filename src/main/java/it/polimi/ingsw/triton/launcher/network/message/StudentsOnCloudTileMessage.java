package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.CloudTile;
import it.polimi.ingsw.triton.launcher.model.enums.Color;

public class StudentsOnCloudTileMessage {
    private int[] students;
    private CloudTile cloudTile;

    public StudentsOnCloudTileMessage(int[] students, CloudTile cloudTile) {
        this.students = new int[Color.values().length];
        this.students=students;
        this.cloudTile = cloudTile;
    }

    public int[] students() {
        return students;
    }
    public CloudTile cloudTile(){
        return cloudTile;
    }
}
