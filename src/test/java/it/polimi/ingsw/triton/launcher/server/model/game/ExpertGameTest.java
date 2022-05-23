package it.polimi.ingsw.triton.launcher.server.model.game;

import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.ChangeTurnException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpertGameTest {

    private GameMode game;
    private GameMode expertGame;

    @BeforeEach
    public void setUp() {
        game = Game.instance(3);
        expertGame = new ExpertGame(game);
        Player p1 = new Player("TestPlayer1");
        expertGame.getPlayers().add(p1);
        Player p2 = new Player("TestPlayer2");
        expertGame.getPlayers().add(p2);
        Player p3 = new Player("TestPlayer3");
        expertGame.getPlayers().add(p3);

    }

    @AfterEach
    public void tearDown() {
        expertGame = null;
    }



    @Test
    void checkIfNumberOfCharCardIsThree() {
        try {
            expertGame.setupBag();
            expertGame.drawCharacterCards();
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(3, expertGame.getCharacterCards().size());
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
    }

    }