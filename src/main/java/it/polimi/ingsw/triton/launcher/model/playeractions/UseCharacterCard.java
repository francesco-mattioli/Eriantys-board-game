package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.player.Wallet;
import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;

public class UseCharacterCard implements Action {
    private final CharacterCard characterCard;
    private final Wallet wallet;

    /**
     * @param characterCard the character card that player wants to purchase.
     * @param wallet        contains the amount of coins of the player
     */
    public UseCharacterCard(CharacterCard characterCard, Wallet wallet) {
        this.characterCard = characterCard;
        this.wallet = wallet;
    }

    /**
     * @return true if the player has enough coins to buy the character card, false otherwise.
     */
    private boolean canBePurchased() {
        return characterCard.getCost() <= wallet.getValue();
    }

    @Override
    public void execute() {
        if (canBePurchased()) {
            characterCard.increaseCost();
            //characterCard.executeEffect();  TODO implement here
        } else {
            throw new RuntimeException("You don't have enough coins");
        }
    }
}
