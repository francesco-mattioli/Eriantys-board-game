package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.model.AssistantDeck;
import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayCardTest {

    /**
     * Tests when the card is not used.
     */
    @Test
    void cardNotUsed() {
        AssistantCard assistantCard = new AssistantCard(AssistantCardType.DOG);
        assertNotNull(assistantCard);
        ArrayList<Integer> usedCards = new ArrayList<>();
        usedCards.add(2);
        PlayCard pc = new PlayCard(assistantCard, null, usedCards, null);
        assertFalse(pc.isUsedCard(assistantCard, usedCards));
    }

    /**
     * Tests if the card is already used.
     */
    @Test
    void cardUsed() {
        AssistantCard assistantCard = new AssistantCard(AssistantCardType.DOG);
        assertNotNull(assistantCard);
        ArrayList<Integer> usedCards = new ArrayList<>();
        usedCards.add(AssistantCardType.DOG.getValue());
        PlayCard pc = new PlayCard(assistantCard, null, usedCards, null);
        assertTrue(pc.isUsedCard(assistantCard, usedCards));
    }

    /**
     * Tests when a player has different options of choice.
     */
    @Test
    void canNotPlayThisCard() {
        AssistantCard assistantCard = new AssistantCard(AssistantCardType.DOG);
        assertNotNull(assistantCard);
        AssistantDeck assistantDeck = new AssistantDeck(Wizard.BLUE);
        ArrayList<Integer> usedCards = new ArrayList<>();
        usedCards.add(1);
        usedCards.add(2);
        usedCards.add(AssistantCardType.DOG.getValue());
        PlayCard pc = new PlayCard(assistantCard, assistantDeck, usedCards, null);
        assertFalse(pc.isUniqueChoice(assistantDeck, usedCards));
    }


    /**
     * Tests if the method launches the exception when the player can't play the card.
     */
    @Test
    void cardCanNotBePlayed() {
        AssistantCard assistantCard = new AssistantCard(AssistantCardType.DOG);
        assertNotNull(assistantCard);
        AssistantDeck assistantDeck = new AssistantDeck(Wizard.BLUE);
        ArrayList<Integer> usedCards = new ArrayList<>();
        usedCards.add(1);
        usedCards.add(2);
        usedCards.add(AssistantCardType.DOG.getValue());
        PlayCard pc = new PlayCard(assistantCard, assistantDeck, usedCards, null);
        assertThrows(RuntimeException.class, ()->pc.execute());
    }

    /**
     * Tests if the card can be played by the user.
     */
    @Test
    void cardCanBePlayed() {
        AssistantCard assistantCard = new AssistantCard(AssistantCardType.DOG);
        assertNotNull(assistantCard);
        AssistantDeck assistantDeck = new AssistantDeck(Wizard.BLUE);
        ArrayList<Integer> usedCards = new ArrayList<>();
        ArrayList<AssistantCard> currentCard = new ArrayList<>();
        currentCard.add(new AssistantCard(AssistantCardType.CAT));
        usedCards.add(AssistantCardType.TIGER.getValue());
        usedCards.add(AssistantCardType.ELEPHANT.getValue());
        PlayCard pc = new PlayCard(assistantCard, assistantDeck, usedCards, currentCard);
        pc.execute();
        assertEquals(assistantCard, currentCard.get(0));
    }
}