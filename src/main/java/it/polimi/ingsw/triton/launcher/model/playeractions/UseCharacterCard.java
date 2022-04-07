package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.model.playeractions.Action;

public class UseCharacterCard implements Action {
    private CharacterCard characterCard;

    public UseCharacterCard(CharacterCard characterCard){
        this.characterCard = characterCard;
    }

    @Override
    public void execute() {
        // TODO implement here
    }
}
