package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Island;

public class CardEffect05 implements CardEffect{
    private Island island;
    private CharacterCard characterCard;

    public CardEffect05(Island island, CharacterCard characterCard){
        this.island = island;
        this.characterCard=characterCard;
    }

    /**
     * This method add a No Entry Tile onto an island
     * and set the island's charactercard05 to the current charactercard.
     * The island is passed through the constructor.
     */
    @Override
    public void execute() {
        island.setNoEntryTiles(1);
        island.setCharacterCard05(characterCard);
    }

}
