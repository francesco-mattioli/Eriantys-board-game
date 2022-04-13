package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.model.player.Wallet;

public class UseCharacterCard implements Action {
    private final CharacterCard characterCard;
    private final CardEffect cardEffect;
    private final Wallet wallet;

    /**
     * @param characterCard the character card that player wants to purchase.
     * @param wallet        contains the amount of coins of the player
     */
    public UseCharacterCard(CharacterCard characterCard, CardEffect cardEffect, Wallet wallet) {
        this.characterCard = characterCard;
        this.cardEffect = cardEffect;
        this.wallet = wallet;
    }

    /**
     * @return true if the player has enough coins to buy the character card, false otherwise.
     */
    private boolean canBePurchased() {
        return characterCard.getCost() <= wallet.getValue();
    }


    /**
     * This method checks if the characterCard can be purchased.
     * If it is possible to purchase, it decreases the wallet,
     * executes the effect and increases the card's cost.
     */
    @Override
    public void execute() {
        if (canBePurchased()) {
            wallet.decrease(characterCard.getCost());
            characterCard.executeEffect(cardEffect);
            characterCard.increaseCost();
        } else {
            throw new RuntimeException("You don't have enough coins");
        }
    }
}
