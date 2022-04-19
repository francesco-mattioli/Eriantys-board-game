package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.model.Island;

public class MotherNaturePositionMessage extends Message{
    private Island motherNaturePosition;
    public MotherNaturePositionMessage(Island motherNaturePosition) {
        super(Game.NAME_SERVER, MessageType.MOTHER_NATURE_POSITION);
        this.motherNaturePosition = motherNaturePosition;
    }
}
