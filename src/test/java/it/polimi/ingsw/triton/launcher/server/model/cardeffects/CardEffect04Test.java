package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.islands.MotherNature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardEffect04Test {
    private MotherNature motherNature;

    @BeforeEach
    void setUp() {
        motherNature = new MotherNature(new Island(3));
    }

    @AfterEach
    void tearDown() {
        motherNature = null;
    }

    /**
     * Checks if the effect sets correctly the additional steps of mother nature.
     */
    @Test
    void testSetAdditionalSteps() {
        new CardEffect04(motherNature).execute();
        assertEquals(2, motherNature.getAdditionalSteps());
    }
}