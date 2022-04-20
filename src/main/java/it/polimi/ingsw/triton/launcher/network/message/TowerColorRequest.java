package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;

import java.util.ArrayList;

public class TowerColorRequest extends Message{
    private ArrayList<TowerColor> availableTowerColors;
    public TowerColorRequest(ArrayList<TowerColor> availableTowerColors) {
        super(Game.NAME_SERVER, MessageType.TOWER_COLOR_REQUEST);
        this.availableTowerColors = availableTowerColors;
    }
}
