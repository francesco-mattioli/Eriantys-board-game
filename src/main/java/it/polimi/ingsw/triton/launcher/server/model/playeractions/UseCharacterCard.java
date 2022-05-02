package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.player.Wallet;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.util.ArrayList;

public class UseCharacterCard implements Action {
    private final CharacterCard characterCard;
    private Player currentPlayer;

    /**
     * @param characterCard the character card that player wants to purchase.
     * @param currentPlayer specifies the player who has played the character card
     */
    public UseCharacterCard(CharacterCard characterCard, Player currentPlayer) {
        this.characterCard = characterCard;
        this.currentPlayer = currentPlayer;
    }


    /**
     * It decreases the wallet, executes the effect and increases the card's cost.
     */
    @Override
    public void execute() throws IllegalClientInputException{
        if(!canBuyCharacterCard(currentPlayer, characterCard))
            throw new IllegalClientInputException(ErrorTypeID.NOT_ENOUGH_COINS);
        currentPlayer.getWallet().decrease(characterCard.getCost());
        characterCard.increaseCost();
    }

    /**
     * @param player the player who wants to play the character card.
     * @param characterCard the character card to play.
     * @return true if the card can be purchased, false otherwise.
     */
    private boolean canBuyCharacterCard(Player player, CharacterCard characterCard){
        return player.getWallet().getValue() >= characterCard.getCost();
    }
}
