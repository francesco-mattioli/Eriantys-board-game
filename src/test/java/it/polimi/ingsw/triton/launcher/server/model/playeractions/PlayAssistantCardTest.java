package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayAssistantCardTest {
    private Player player;
    private ArrayList<AssistantCard> usedAssistantCards;

    @BeforeEach
    void setupPlayer(){
        player = new Player("Pippo");
        player.setAssistantDeck(new AssistantDeck(Wizard.BLUE));
        usedAssistantCards = new ArrayList<>();
    }

    @AfterEach
    void tearDown(){
        player = null;
        usedAssistantCards = null;
    }

    /**
     * Tests if the card can be played by the user.
     */
    @Test
    void cardCanBePlayed() {
        AssistantCard cardToPlay = new AssistantCard(AssistantCardType.CAT);
        usedAssistantCards.add(new AssistantCard(AssistantCardType.SNAKE));
        usedAssistantCards.add(new AssistantCard(AssistantCardType.TIGER));
        PlayAssistantCard pc = new PlayAssistantCard(cardToPlay, player, usedAssistantCards);
        player.executeAction(pc);
        assertEquals(cardToPlay, player.getLastPlayedAssistantCard());
    }

    /**
     * Tests if the method launches an exception because player can't play this card
     */
    @Test
    void cannotPlayThisCard(){
        AssistantCard cardToPlay = new AssistantCard(AssistantCardType.CAT);
        usedAssistantCards.add(new AssistantCard(AssistantCardType.SNAKE));
        usedAssistantCards.add(cardToPlay);
        PlayAssistantCard pc = new PlayAssistantCard(cardToPlay, player, usedAssistantCards);
        assertThrows(RuntimeException.class, ()->player.executeAction(pc));
    }

    /**
     * Tests if the card is accepted because player has only one card.
     */
    @Test
    void playerHasNotOtherChoice(){
        usedAssistantCards.add(new AssistantCard(AssistantCardType.SNAKE));
        player.getAssistantDeck().getAssistantDeck().clear();
        AssistantCard uniqueCardInTheDeck = new AssistantCard(AssistantCardType.SNAKE);
        player.getAssistantDeck().getAssistantDeck().add(uniqueCardInTheDeck);
        usedAssistantCards.add(uniqueCardInTheDeck);
        PlayAssistantCard pc = new PlayAssistantCard(uniqueCardInTheDeck, player, usedAssistantCards);
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
        usedAssistantCards.add(new AssistantCard(AssistantCardType.SNAKE));
        usedAssistantCards.add(new AssistantCard(AssistantCardType.TIGER));
        PlayAssistantCard pc = new PlayAssistantCard(cardToPlay, player, usedAssistantCards);
        player.executeAction(pc);
        assertEquals(initialDimDeck-1, player.getAssistantDeck().getAssistantDeck().size());
    }

    /**
     * Tests if the dimension of used cards array is increased after the player played a card.
     */
    @Test
    void testIncreasedUsedCardsDimension(){
        AssistantCard cardToPlay = new AssistantCard(AssistantCardType.CAT);
        usedAssistantCards.add(new AssistantCard(AssistantCardType.SNAKE));
        usedAssistantCards.add(new AssistantCard(AssistantCardType.TIGER));
        int initialDimDeck = usedAssistantCards.size();
        PlayAssistantCard pc = new PlayAssistantCard(cardToPlay, player, usedAssistantCards);
        player.executeAction(pc);
        assertEquals(initialDimDeck+1, usedAssistantCards.size());
    }
}