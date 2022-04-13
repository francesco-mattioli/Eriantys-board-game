package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.model.player.Player;

import java.util.ArrayList;

/**
 * Represents the action of playing an assistant card at the start of the turn.
 */
public class PlayCard implements Action {
    private final AssistantCard assistantCardToPlay;
    private final ArrayList<Integer> usedCards;
    private Player player;


    /**
     * @param assistantCardToPlay the assistant card selected by the player.
     * @param player the player who plays the card.
     * @param usedCards the cards already played in this turn.
     */
    public PlayCard(AssistantCard assistantCardToPlay, Player player, ArrayList<Integer> usedCards) {
        this.assistantCardToPlay = assistantCardToPlay;
        this.usedCards = usedCards;
        this.player=player;
    }

    /**
     * @param assistantCard the card to check if it's already used.
     * @param usedCards     the cards already played by the others players in this turn.
     * @return if the card is already used.
     */
    private boolean isUsedCard(AssistantCard assistantCard, ArrayList<Integer> usedCards) {
        for (Integer value : usedCards) {
            if (assistantCard.getAssistantCardType().getValue() == value)
                return true;
        }
        return false;
    }

    /**
     * @param assistantDeck the available cards of the player.
     * @param usedCards     the cards already played by the others players in this turn.
     * @return true if the player has only one card in the deck, false otherwise.
     */
    private boolean isUniqueChoice(AssistantDeck assistantDeck, ArrayList<Integer> usedCards) {
        if (assistantDeck.getAssistantDeck().size() == 1)
            return true;
        else {
            for (AssistantCard card : assistantDeck.getAssistantDeck()) {
                for (Integer value : usedCards) {
                    if (card.getAssistantCardType().getValue() == value)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Allows the player to play the assistant card if it's not already played or if it's the only
     * card player can play.
     *
     * @throws RuntimeException when a card can't be used in this turn.
     */
    @Override
    public void execute() {
        if (isUsedCard(assistantCardToPlay, usedCards)) {
            if (isUniqueChoice(player.getAssistantDeck(), usedCards)) {
                player.setLastPlayedAssistantCard(assistantCardToPlay);
            } else
                throw new RuntimeException("The selected card is already used");
        } else {
            player.setLastPlayedAssistantCard(assistantCardToPlay);
            player.getAssistantDeck().removeCard(assistantCardToPlay);
            usedCards.add(assistantCardToPlay.getAssistantCardType().getValue());
        }
    }
}
