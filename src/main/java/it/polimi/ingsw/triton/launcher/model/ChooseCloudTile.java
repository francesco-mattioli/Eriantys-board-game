package it.polimi.ingsw.triton.launcher.model;

/**
 * Represents the choice of the cloud tile made by the player
 */
public class ChooseCloudTile implements Action{
    private CloudTile cloudTile;

    /**
     * @param cloudTile the cloud tile chosen by the player
     */
    public ChooseCloudTile(CloudTile cloudTile){
        this.cloudTile = cloudTile;
    }

    /**
     * Move the students from the chosen cloud tile to the entrance of the player
     */
    @Override
    public void execute() {
        // TODO implement here
    }
}
