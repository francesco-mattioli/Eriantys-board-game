package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

import java.io.Serializable;

public class CardEffect05 implements CardEffect, Serializable {
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
    public void execute() throws IllegalClientInputException {
        if(characterCard.noEntryTiles > 0){
            characterCard.noEntryTiles--;
            island.setNoEntryTiles(island.getNoEntryTiles() + 1);
            island.setCharacterCard05(characterCard);
        }else{
            throw new IllegalClientInputException();
        }
    }

}
