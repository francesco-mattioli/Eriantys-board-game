package it.polimi.ingsw.triton.launcher.server.model.player;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;

import java.io.Serializable;
import java.util.ArrayList;

public class AssistantDeck implements Serializable {
    /**
     * The wizard associated with the deck
     * An ArrayList containing the cards of the same wizard
     * The maximum number of cards a deck can contain
     */
    private final Wizard wizard;
    private final int CARDS_NUMBER = AssistantCardType.values().length;
    private final ArrayList<AssistantCard> assistantDeck;

    /**
     * Instantiates a new Assistant deck.
     * Fills the deck with all the cards associated with the type of Wizard
     * @param wizard the wizard to assign to the deck
     */
    public AssistantDeck(Wizard wizard) {
        this.assistantDeck = new ArrayList<>();
        this.wizard = wizard;
        for (int i = 0; i < CARDS_NUMBER; i++) {
            assistantDeck.add(new AssistantCard(AssistantCardType.values()[i]));
        }
    }

    /**
     * Gets assistant deck.
     *
     * @return the assistant deck
     */
    public ArrayList<AssistantCard> getAssistantDeck() {
        return assistantDeck;
    }

    /**
     * It is called when a player decided to play a card.
     * This card must be removed from the deck and placed on the
     * personal discard pile in order to be visibile for other players.
     *
     * @param cardToRemove the card to remove
     */
    public void removeCard(AssistantCard cardToRemove) {
        for (int i = 0; i < assistantDeck.size(); i++) {
            if (assistantDeck.get(i).getType().getValue() == cardToRemove.getType().getValue()) {
                assistantDeck.remove(i);
                break;
            }
        }
    }
    public String printAllAssistantCards(){
        String results = " ";
        for (AssistantCard assistantCard: assistantDeck) {
            results += assistantCard.toString();
        }
        return results;
    }

    @Override
    public String toString(){
        return "\n\tAssistantDeck: " + wizard +"\n" + "[" + printAllAssistantCards() + "]";
    }
}
