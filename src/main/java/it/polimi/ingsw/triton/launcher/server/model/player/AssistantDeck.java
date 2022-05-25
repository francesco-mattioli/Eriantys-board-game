package it.polimi.ingsw.triton.launcher.server.model.player;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssistantDeck implements Serializable {
    /**
     * The wizard associated with the deck
     * An ArrayList containing the cards of the same wizard
     * The maximum number of cards a deck can contain
     */
    private final Wizard wizard;
    private final List<AssistantCard> assistantCards;

    /**
     * Instantiates a new Assistant deck.
     * Fills the deck with all the cards associated with the type of Wizard
     *
     * @param wizard the wizard to assign to the deck
     */
    public AssistantDeck(Wizard wizard) {
        int cardsNumber = AssistantCardType.values().length;
        this.assistantCards = new ArrayList<>();
        this.wizard = wizard;
        for (int i = 0; i < cardsNumber; i++) {
            assistantCards.add(new AssistantCard(AssistantCardType.values()[i]));
        }
    }

    /**
     * Gets assistant deck.
     *
     * @return the assistant deck
     */
    public List<AssistantCard> getAssistantDeck() {
        return assistantCards;
    }

    /**
     * It is called when a player decided to play a card.
     * This card must be removed from the deck and placed on the
     * personal discard pile in order to be visible for other players.
     *
     * @param cardToRemove the card to remove
     */
    public void removeCard(AssistantCard cardToRemove) {
        for (int i = 0; i < assistantCards.size(); i++) {
            if (assistantCards.get(i).getType().getValue() == cardToRemove.getType().getValue()) {
                assistantCards.remove(i);
                break;
            }
        }
    }

    /**
     * @return the string with all the assistant cards.
     */
    public String printAllAssistantCards() {
        StringBuilder result = new StringBuilder(" ");
        for (AssistantCard assistantCard : assistantCards) {
            result.append("\t").append(assistantCard.toString());
        }
        return result.toString();
    }

    public Wizard getWizard() {
        return wizard;
    }

    @Override
    public String toString() {
        return "AssistantDeck: " + wizard + "\n" + printAllAssistantCards();
    }
}
