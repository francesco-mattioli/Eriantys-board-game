package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.MotherNature;

public class CardEffect04 implements CardEffect{
    private MotherNature motherNature;

    public CardEffect04(MotherNature motherNature){
        this.motherNature = motherNature;
    }


    /**
     * This effect allows the player to have two extra steps for mother nature
     */
    @Override
    public void execute() {
        motherNature.setAdditionalSteps(2);
        // remember to set the additional steps to zero when the turn is over
    }
}
