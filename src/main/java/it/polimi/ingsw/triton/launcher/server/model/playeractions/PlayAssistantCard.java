package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoAssistantCardPlayedMessage;

import java.util.ArrayList;

/**
 * Represents the action of playing an assistant card at the start of the turn.
 */
public class PlayAssistantCard implements Action {
    private final AssistantCard assistantCardToPlay;
    private final ArrayList<AssistantCard> usedAssistantCards;
    private final Player player;


    /**
     * @param assistantCardToPlay     the assistant card selected by the player.
     * @param player                  who plays the card.
     * @param usedAssistantCardsCards the cards already played in this turn.
     */
    public PlayAssistantCard(AssistantCard assistantCardToPlay, Player player, ArrayList<AssistantCard> usedAssistantCardsCards) {
        this.assistantCardToPlay = assistantCardToPlay;
        this.usedAssistantCards = usedAssistantCardsCards;
        this.player = player;
    }

    /**
     * @param assistantCard      the card to check if it's already used.
     * @param usedAssistantCards the cards already played by the others players in this turn.
     * @return if the card is already used.
     */
    private boolean isUsedCard(AssistantCard assistantCard, ArrayList<AssistantCard> usedAssistantCards) {
        for (AssistantCard usedAssistantCard : usedAssistantCards) {
            if (assistantCard.getType().equals(usedAssistantCard.getType()))
                return true;
        }
        return false;
    }

    /**
     * @param assistantDeck      the available cards of the player.
     * @param usedAssistantCards the cards already played by the others players in this turn.
     * @return true if the player has only one card in the deck, false otherwise.
     */
    private boolean isUniqueChoice(AssistantDeck assistantDeck, ArrayList<AssistantCard> usedAssistantCards) {
        if (assistantDeck.getAssistantDeck().size() == 1)
            return true;
        else {
            for (AssistantCard card : assistantDeck.getAssistantDeck()) {
                for (AssistantCard assistantCard : usedAssistantCards) {
                    if (card.getType().equals(assistantCard.getType()))
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
    public void execute() throws RuntimeException {
        if (isUsedCard(assistantCardToPlay, usedAssistantCards)) {
            if (isUniqueChoice(player.getAssistantDeck(), usedAssistantCards)) {
                player.setLastPlayedAssistantCard(assistantCardToPlay);
                player.getAssistantDeck().removeCard(assistantCardToPlay);
            } else
                throw new RuntimeException("The selected card is already used");
        } else {
            player.setLastPlayedAssistantCard(assistantCardToPlay);
            player.getAssistantDeck().removeCard(assistantCardToPlay);
            usedAssistantCards.add(assistantCardToPlay);
        }
    }
}
