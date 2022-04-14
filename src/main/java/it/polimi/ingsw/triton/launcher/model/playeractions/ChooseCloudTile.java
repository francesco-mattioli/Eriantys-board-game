package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.CloudTile;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.player.SchoolBoard;

public class ChooseCloudTile implements Action {
    private final CloudTile cloudTile;
    private final SchoolBoard schoolBoard;

    /**
     * @param cloudTile the cloud tile chosen by the player.
     * @param schoolBoard the school board of the player.
     */
    public ChooseCloudTile(CloudTile cloudTile, SchoolBoard schoolBoard){
        this.cloudTile = cloudTile;
        this.schoolBoard = schoolBoard;
    }

    private boolean isCloudTileAlreadyChosen(){
        int sumStudents = 0;
        for(int i = 0; i < cloudTile.getStudents().length; i++){
            sumStudents += cloudTile.getStudents()[i];
        }
        return sumStudents == 0;
    }

    /**
     * Moves the students from the chosen cloud tile to the entrance of the player.
     */
    @Override
    public void execute() {
        if(isCloudTileAlreadyChosen()){
            throw new RuntimeException("This cloud tile is already chosen");
        }else{
            for(int i = 0; i < cloudTile.getStudents().length; i++){
                for(int j = 0; j < cloudTile.getStudents()[i]; j++){
                    cloudTile.removeStudentFromCloudTile(Color.values()[i]);
                    schoolBoard.addStudentIntoEntrance(Color.values()[i]);
                }
            }
        }
    }
}
