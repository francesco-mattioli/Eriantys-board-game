package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.CloudTile;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.SchoolBoard;

/**
 * Represents the choice of the cloud tile made by the player.
 */
public class ChooseCloudTile implements Action {
    private CloudTile cloudTile;
    private SchoolBoard schoolBoard;

    /**
     * @param cloudTile the cloud tile chosen by the player.
     */
    public ChooseCloudTile(CloudTile cloudTile, SchoolBoard schoolBoard){
        this.cloudTile = cloudTile;
        this.schoolBoard = schoolBoard;
    }

    /**
     * Moves the students from the chosen cloud tile to the entrance of the player.
     */
    @Override
    public void execute() {
        // TODO implement here
        for(int i = 0; i < cloudTile.getStudents().length; i++){
            for(int j = 0; j < cloudTile.getStudents()[i]; j++){
                cloudTile.removeStudentFromCloudTile(Color.values()[i]);
                schoolBoard.addStudentIntoEntrance(Color.values()[i]);
            }
        }
    }
}
