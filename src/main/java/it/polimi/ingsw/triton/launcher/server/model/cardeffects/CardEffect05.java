package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.io.Serializable;

public class CardEffect05 implements CardEffect, Serializable {
    private final Island island;
    private final CharacterCard characterCard;

    public CardEffect05(Island island, CharacterCard characterCard){
        this.island = island;
        this.characterCard=characterCard;
    }

    /**
     * This method add a No Entry Tile onto an island
     * and set the island's character card 05 to the current character card.
     * The island is passed through the constructor.
     */
    @Override
    public void execute() throws IllegalClientInputException {
        if(characterCard.noEntryTiles > 0){
            characterCard.noEntryTiles--;
            island.setNoEntryTiles(island.getNoEntryTiles() + 1);
            island.setCharacterCard05(characterCard);
        }else{
            throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE);
        }
    }

}
