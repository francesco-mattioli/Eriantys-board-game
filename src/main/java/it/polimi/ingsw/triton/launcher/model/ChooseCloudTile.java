package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;

public class ChooseCloudTile implements Action{
    private ArrayList<CloudTile> cloudTiles;

    public ChooseCloudTile(ArrayList<CloudTile> cloudTiles){
        this.cloudTiles = cloudTiles;
    }

    @Override
    public void execute() {
        // TODO implement here
    }
}
