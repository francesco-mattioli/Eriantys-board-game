package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.Wallet;
import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.model.playeractions.Action;

public class UseCharacterCard implements Action {
    private CharacterCard characterCard;
    private Wallet wallet;

    /**
     * @param characterCard the character card that player wants to purchase.
     * @param wallet the amount of coins of the player
     */
    public UseCharacterCard(CharacterCard characterCard, Wallet wallet){
        this.characterCard = characterCard;
        this.wallet = wallet;
    }

    /**
     * @return true if the player has enough coins to buy the character card, false otherwise.
     */
    private boolean canBePurchased(){
        return characterCard.getCost() <= wallet.getValue();
    }

    @Override
    public void execute() {
        if(canBePurchased()){
            characterCard.increaseCost();
            //characterCard.executeEffect();  TODO implement here
        }else{
            throw new RuntimeException("You don't have enough coins");
        }
    }
}
