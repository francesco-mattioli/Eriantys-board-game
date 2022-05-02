package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class TowerColorReply extends ClientMessage {
    private final TowerColor playerColor;

    public TowerColorReply(String username, TowerColor playerColor) {
        super(MessageType.TOWER_COLOR_REPLY, username);
        this.playerColor = playerColor;
    }
    public TowerColor getPlayerColor() {
        return playerColor;
    }

    //normal execution: send same message to next player
    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, NoSuchElementException {
        game.chooseTowerColor(senderUsername, playerColor);
        game.setCurrentPlayer(game.getNextPlayer(game.getPlayerByUsername(senderUsername)));
    }

    @Override
    public void createStandardNextMessage(Game game, VirtualView virtualView) {
        virtualView.askTowerColor(game.getTowerColorChosen());
    }

    @Override
    public void createExceptionalNextMessage(Game game, VirtualView virtualView) {
        virtualView.askWizard(game.getAvailableWizards());
    }

    @Override
    public void createInputErrorMessage(Game game, VirtualView virtualView) {
        virtualView.askTowerColor(game.getTowerColorChosen());
    }

}
