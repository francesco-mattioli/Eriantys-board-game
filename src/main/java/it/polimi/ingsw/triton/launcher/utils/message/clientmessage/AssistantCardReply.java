package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class AssistantCardReply extends ClientMessage {
    private final AssistantCard chosenAssistantCard;

    public AssistantCardReply(String username, AssistantCard chosenAssistantCard) {
        super(MessageType.ASSISTANT_CARD_REPLY, username);
        this.chosenAssistantCard = chosenAssistantCard;
    }

    public AssistantCard getChosenAssistantCard() {
        return chosenAssistantCard;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, NoSuchElementException {
        game.chooseAssistantCard(senderUsername, chosenAssistantCard);
        game.setCurrentPlayer(game.getNextPlayer(game.getPlayerByUsername(senderUsername)));
    }

    @Override
    public void createStandardNextMessage(Game game, VirtualView virtualView) {
        virtualView.askAssistantCard();
    }

    @Override
    public void createExceptionalNextMessage(Game game, VirtualView virtualView) {
        game.setGameState(GameState.ACTION_PHASE);
        virtualView.askMoveStudentFromEntrance();
    }

    @Override
    public void createInputErrorMessage(Game game, VirtualView virtualView) {
        virtualView.askAssistantCard();
    }

}
