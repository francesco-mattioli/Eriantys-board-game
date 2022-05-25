package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

public class ChooseCloudTile implements Action {
    private final CloudTile cloudTile;
    private final SchoolBoard schoolBoard;

    /**
     * @param cloudTile   the cloud tile chosen by the player.
     * @param schoolBoard the school board of the player.
     */
    public ChooseCloudTile(CloudTile cloudTile, SchoolBoard schoolBoard) {
        this.cloudTile = cloudTile;
        this.schoolBoard = schoolBoard;
    }

    /**
     * @return true if the cloud tile is not already chosen, false otherwise.
     */
    private boolean isCloudTileAlreadyChosen() {
        return cloudTile.isAlreadyUsed();
    }

    /**
     * Moves the students from the chosen cloud tile to the entrance of the player.
     */
    @Override
    public void execute() throws IllegalClientInputException {
        if (isCloudTileAlreadyChosen()) {
            throw new IllegalClientInputException(ErrorTypeID.CLOUD_TILE_ALREADY_CHOSEN);
        } else {
            for (int i = 0; i < cloudTile.getStudents().length; i++) {
                int repeat = cloudTile.getStudents()[i];
                for (int j = 0; j < repeat; j++) {
                    cloudTile.removeStudentFromCloudTile(Color.values()[i]);
                    schoolBoard.addStudentIntoEntrance(Color.values()[i]);
                }
            }
            cloudTile.setAlreadyUsed(true);
        }
    }
}
