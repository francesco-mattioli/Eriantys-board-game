package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.MotherNature;

import java.io.Serializable;

public class CardEffect04 implements CardEffect, Serializable {
    private final MotherNature motherNature;

    public CardEffect04(MotherNature motherNature){
        this.motherNature = motherNature;
    }


    /**
     * This effect allows the player to have two extra steps for mother nature
     */
    @Override
    public void execute() {
        int additionalSteps = 2;
        motherNature.setAdditionalSteps(additionalSteps);
        // remember to set the additional steps to zero when the turn is over
    }
}
