package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.MotherNature;

public class CardEffect04 implements CardEffect{
    private MotherNature motherNature;
    private final int ADDITIONAL_STEPS = 2;

    public CardEffect04(MotherNature motherNature){
        this.motherNature = motherNature;
    }


    /**
     * This effect allows the player to have two extra steps for mother nature
     */
    @Override
    public void execute() {
        motherNature.setAdditionalSteps(ADDITIONAL_STEPS);
        // remember to set the additional steps to zero when the turn is over
    }
}
