package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.Wallet;

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
     * It decreases the wallet, executes the effect and increases the card's cost.
     */
    @Override
    public void execute() {
        wallet.decrease(characterCard.getCost());
        characterCard.executeEffect(cardEffect);
        characterCard.increaseCost();
    }
}
