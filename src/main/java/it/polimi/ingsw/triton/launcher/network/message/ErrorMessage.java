package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class ErrorMessage extends Message{
    //private String error;
    private ErrorTypeID errorTypeID;
    public ErrorMessage(ErrorTypeID errorTypeID) {
        super(Game.NAME_SERVER, MessageType.ERROR);
        this.errorTypeID = errorTypeID;
    }

    @Override
    public String toString(){
        return errorTypeID.getDescription();
    }
}
