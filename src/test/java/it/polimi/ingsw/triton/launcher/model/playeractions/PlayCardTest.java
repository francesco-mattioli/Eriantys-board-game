package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayCardTest {
    private Player player;
    private ArrayList<Integer> usedCards;

    @BeforeEach
    void setupPlayer(){
        player = new Player("Pippo");
        player.setAssistantDeck(new AssistantDeck(Wizard.BLUE));
        usedCards = new ArrayList<>();
    }

    @AfterEach
    void tearDown(){
        player = null;
        usedCards = null;
    }

    /**
     * Tests if the card can be played by the user.
     */
    @Test
    void cardCanBePlayed() {
        AssistantCard cardToPlay = new AssistantCard(AssistantCardType.CAT);
        usedCards.add(AssistantCardType.SNAKE.getValue());
        usedCards.add(AssistantCardType.TIGER.getValue());
        PlayCard pc = new PlayCard(cardToPlay, player, usedCards);
        player.executeAction(pc);
        assertEquals(cardToPlay, player.getLastPlayedAssistantCard());
    }

    /**
     * Tests if the method launches an exception because player can't play this card
     */
    @Test
    void cannotPlayThisCard(){
        AssistantCard cardToPlay = new AssistantCard(AssistantCardType.CAT);
        usedCards.add(AssistantCardType.SNAKE.getValue());
        usedCards.add(cardToPlay.getAssistantCardType().getValue());
        PlayCard pc = new PlayCard(cardToPlay, player, usedCards);
        assertThrows(RuntimeException.class, ()->player.executeAction(pc));
    }

    /**
     * Tests if the card is accepted because player has only one card.
     */
    @Test
    void playerHasNotOtherChoice(){
        usedCards.add(AssistantCardType.SNAKE.getValue());
        player.getAssistantDeck().getAssistantDeck().clear();
        AssistantCard uniqueCardInTheDeck = new AssistantCard(AssistantCardType.SNAKE);
        player.getAssistantDeck().getAssistantDeck().add(uniqueCardInTheDeck);
        usedCards.add(uniqueCardInTheDeck.getAssistantCardType().getValue());
        PlayCard pc = new PlayCard(uniqueCardInTheDeck, player, usedCards);
        player.executeAction(pc);
        assertEquals(uniqueCardInTheDeck, player.getLastPlayedAssistantCard());
    }

    /**
     * Tests if the dimension of the deck is decreased by 1 once the card is played.
     */
    @Test
    void checkDimensionDeckAfterPlayedCard(){
        AssistantCard cardToPlay = new AssistantCard(AssistantCardType.CAT);
        int initialDimDeck = player.getAssistantDeck().getAssistantDeck().size();
        usedCards.add(AssistantCardType.SNAKE.getValue());
        usedCards.add(AssistantCardType.TIGER.getValue());
        PlayCard pc = new PlayCard(cardToPlay, player, usedCards);
        player.executeAction(pc);
        assertEquals(initialDimDeck-1, player.getAssistantDeck().getAssistantDeck().size());
    }

    /**
     * Tests if the dimension of used cards array is increased after the player played a card.
     */
    @Test
    void testIncreasedUsedCardsDimension(){
        AssistantCard cardToPlay = new AssistantCard(AssistantCardType.CAT);
        usedCards.add(AssistantCardType.SNAKE.getValue());
        usedCards.add(AssistantCardType.TIGER.getValue());
        int initialDimDeck = usedCards.size();
        PlayCard pc = new PlayCard(cardToPlay, player, usedCards);
        player.executeAction(pc);
        assertEquals(initialDimDeck+1, usedCards.size());
    }
}