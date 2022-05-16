package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

public class UseCharacterCard implements Action {
    private final CharacterCard characterCard;
    private final Player currentPlayer;
    private final GeneralCoinSupply generalCoinSupply;

    /**
     * @param characterCard the character card that player wants to purchase.
     * @param currentPlayer specifies the player who has played the character card.
     * @param generalCoinSupply the general coin supply of the game.
     */
    public UseCharacterCard(CharacterCard characterCard, Player currentPlayer, GeneralCoinSupply generalCoinSupply) {
        this.characterCard = characterCard;
        this.currentPlayer = currentPlayer;
        this.generalCoinSupply = generalCoinSupply;
    }


    /**
     * It decreases the wallet, executes the effect and increases the card's cost.
     */
    @Override
    public void execute() throws IllegalClientInputException{
        if(!canBuyCharacterCard(currentPlayer, characterCard))
            throw new IllegalClientInputException(ErrorTypeID.NOT_ENOUGH_COINS);
        if(!canBeUsed())
            throw new IllegalClientInputException(ErrorTypeID.CHARACTER_CARD_NOT_AVAILABLE);
        currentPlayer.getWallet().decrease(characterCard.getCost());
        generalCoinSupply.increment(characterCard.getCost());
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

    /**
     * Checks if the parameters requested from the character card are available.
     * @return true if the card can be used according to the parameters requested to activate the effect, false otherwise.
     */
    private boolean canBeUsed(){
        return (characterCard.getId() != 7 || !currentPlayer.getSchoolBoard().isEntranceEmpty()) && (characterCard.getId() != 10 || (!currentPlayer.getSchoolBoard().isEntranceEmpty() && !currentPlayer.getSchoolBoard().isDiningRoomEmpty()));
    }
}
