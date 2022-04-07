package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.model.AssistantDeck;
import it.polimi.ingsw.triton.launcher.model.playeractions.Action;

import java.util.ArrayList;

/**
 * Represents the action of playing an assistant card at the start of the turn.
 */
public class PlayCard implements Action {
    private AssistantCard assistantCard;
    private AssistantDeck assistantDeck;
    private ArrayList<Integer> usedCards;
    private AssistantCard currentPlayedCard;

    /**
     * @param assistantCard the assistant card selected by the player.
     * @param assistantDeck the available cards of the player.
     * @param usedCards the cards already played in this turn.
     * @param currentPlayedCard the card played by the user.
     */
    public PlayCard(AssistantCard assistantCard, AssistantDeck assistantDeck, ArrayList<Integer> usedCards, AssistantCard currentPlayedCard){
        this.assistantCard = assistantCard;
        this.assistantDeck = assistantDeck;
        this.usedCards = usedCards;
        this.currentPlayedCard = currentPlayedCard;
    }

    /**
     * @param assistantCard the card to check if it's already used.
     * @param usedCards the cards already played by the others players in this turn.
     * @return if the card is already used.
     */
    public boolean isUsedCard(AssistantCard assistantCard, ArrayList<Integer> usedCards){
        for(Integer value: usedCards){
            if(assistantCard.getAssistantCardType().getValue() == value)
                return true;
        }
        return false;
    }

    /**
     * @param assistantDeck the available cards of the player.
     * @param usedCards the cards already played by the others players in this turn.
     * @return true if the player has only one card in the deck, false otherwise.
     */
    public boolean isUniqueChoice(AssistantDeck assistantDeck, ArrayList<Integer> usedCards){
        if(assistantDeck.getAssistantDeck().size() == 1)
            return true;
        else{
            for(AssistantCard card: assistantDeck.getAssistantDeck()){
                for(Integer value: usedCards){
                    if(card.getAssistantCardType().getValue() == value)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Allows the player to play the assistant card if it's not already played or if it's the only
     * card player can play.
     * @throws RuntimeException when a card can't be used in this turn.
     */
    @Override
    public void execute() {
        if(isUsedCard(assistantCard, usedCards)){
            if(isUniqueChoice(assistantDeck, usedCards))
                currentPlayedCard = assistantCard;
            else
                throw new RuntimeException("The selected card is already used");
        }else{
            currentPlayedCard = assistantCard;
        }
    }
}
