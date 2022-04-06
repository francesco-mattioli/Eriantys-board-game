package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;

public class UseCharacterCard implements Action{
    private CharacterCard characterCard;

    public UseCharacterCard(CharacterCard characterCard){
        this.characterCard = characterCard;
    }

    @Override
    public void execute() {
        // TODO implement here
    }
}
