package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Island;

public class CardEffect05 implements CardEffect{
    private Island island;
    private CharacterCard characterCard;

    public CardEffect05(Island island, CharacterCard characterCard){
        this.island = island;
        this.characterCard=characterCard;
    }

    @Override
    public void execute() {
        island.setNoEntryTiles(1);
        island.setCharacterCard05(characterCard);
    }

}
