package it.polimi.ingsw.triton.launcher.server.controller;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageExceptionalVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Controller is used to manage the game flow, using visitor pattern to modify the model and to send the correct following message, basing on CLientMessage type
 * Controller is notified by virtual view, when a new message is received
 * Login, number of players and game mode are not managed here, but in server
 * Controller catches all exceptions that model throws
 */

public class Controller implements Observer<Message> {
    private final Game game;
    private final ArrayList<VirtualView> virtualViews = new ArrayList<>();

    public Controller(Game game) {
        this.game = game;
    }

    public ArrayList<VirtualView> getVirtualViews() {
        return virtualViews;
    }

    public void addPlayer(String username) throws IllegalArgumentException {
        game.addPlayer(username);
    }

    public void createLoginReplyMessage(String username){
        getVirtualViewByUsername(username).showLoginReply();
    }

    public void addGameObserver(VirtualView virtualView) {
        game.addObserver(virtualView);
    }

    public VirtualView getVirtualViewByUsername(String username) throws NoSuchElementException {
        for (VirtualView vw : virtualViews) {
            if (vw.getUsername().equals(username))
                return vw;
        }
        throw new NoSuchElementException("The virtualview does not exist");
    }



    /**
     * Calls a method in virtualView to send the request to a player for selecting a tower color.
     *
     * @param username the receiver username of the message
     */
    public void createTowerColorRequestMessage(String username) {
        getVirtualViewByUsername(username).askTowerColor(game.getTowerColorChosen());
    }

    /*
    public void createWizardRequestMessage(String username) {
        getVirtualViewByUsername(username).askWizard(game.getAvailableWizards());
    }

    public void createAssistantCardRequestMessage(String username) {
        getVirtualViewByUsername(username).askAssistantCard();
    }

    private void createMoveStudentRequest(String username){
        getVirtualViewByUsername(username).askMoveStudentFromEntrance();
    }

    private void createMotherNatureStepsRequest(String username){
        getVirtualViewByUsername(username).askNumberStepsMotherNature();
    }

    private void createCloudTileMessage(String username){
        getVirtualViewByUsername(username).askCloudTile(game.getAvailableCloudTiles());
    }*/

    /**
     * In the login phase, it selects the methods in game to setting some properties to the players.
     *
     * @param message the message received.
     */
    /*private void loginPhaseSwitch(Message message) {
        switch (message.getMessageType()) {
            case TOWER_COLOR_REPLY: {
                try {
                    game.chooseTowerColor(senderUsername, ((TowerColorReply) message).getPlayerColor());
                    createTowerColorRequestMessage(game.getNextPlayer(game.getPlayerByUsername(senderUsername)).getUsername());
                    game.setCurrentPlayer(game.getNextPlayer(game.getPlayerByUsername(senderUsername)));
                    break;
                } catch (IllegalClientInputException e) {
                    getVirtualViewByUsername(senderUsername).showErrorMessage(ErrorTypeID.WRONG_COLOR);
                    createTowerColorRequestMessage(senderUsername);
                } catch (NoSuchElementException e) {
                    createWizardRequestMessage(senderUsername);
                    game.setCurrentPlayer(game.getPlayers().get(0));
                    break;
                }
            }
            case WIZARD_REPLY: {
                try {
                    game.chooseWizard(((ClientMessage) message).getSenderUsername(), ((WizardReply) message).getPlayerWizard());
                    createWizardRequestMessage(game.getNextPlayer(game.getPlayerByUsername(senderUsername)).getUsername());
                    game.setCurrentPlayer(game.getNextPlayer(game.getPlayerByUsername(senderUsername)));
                    break;
                } catch (IllegalClientInputException e) {
                    getVirtualViewByUsername(senderUsername).showErrorMessage(ErrorTypeID.WRONG_WIZARD);
                    createWizardRequestMessage(senderUsername);
                    break;
                } catch (NoSuchElementException e) {
                    game.setup();
                    createAssistantCardRequestMessage(game.getCurrentPlayer().getUsername());
                    game.setCurrentPlayer(game.getPlayers().get(0));
                    break;
                }
            }
        }
    }*/

    /**
     * In the planning phase, it selects the methods in game to do some actions.
     *
     * @param message the message received.
     */
    /*private void planningPhaseSwitch(Message message) {
        switch (message.getMessageType()) {
            case ASSISTANT_CARD_REPLY: {
                try {
                    game.chooseAssistantCard(((ClientMessage) message).getSenderUsername(), ((AssistantCardReply) message).getChosenAssistantCard());
                    createAssistantCardRequestMessage(game.getNextPlayer(game.getPlayerByUsername(senderUsername)).getUsername());
                    game.setCurrentPlayer(game.getNextPlayer(game.getPlayerByUsername(senderUsername)));
                    break;
                } catch (IllegalClientInputException e) {
                    getVirtualViewByUsername(senderUsername).showErrorMessage(ErrorTypeID.ASSISTANTCARD_ALREADY_CHOSEN);
                    createAssistantCardRequestMessage(senderUsername);
                    break;
                } catch (NoSuchElementException e) {
                    createAssistantCardRequestMessage(game.getCurrentPlayer().getUsername());
                    game.sortPlayerPerTurn();
                    game.setCurrentPlayer(game.getPlayers().get(0));
                    game.actionPhase();
                    createMoveStudentRequest(game.getCurrentPlayer().getUsername());
                    break;
                }
            }
        }
    }

    private void actionPhaseSwitch(Message message){
        switch (message.getMessageType()) {
            case MOVE_STUDENT_ONTO_ISLAND: {
                try {
                    game.executeActionMoveStudentToIsland(((MoveStudentOntoIslandMessage)message).getStudent(), ((MoveStudentOntoIslandMessage)message).getIslandID());
                    createMoveStudentRequest(senderUsername);
                    break;
                } catch (IllegalClientInputException e) {
                    getVirtualViewByUsername(senderUsername).showErrorMessage(ErrorTypeID.ILLEGAL_MOVE);
                    createMoveStudentRequest(senderUsername);
                    break;
                } catch (LastMoveException e) {
                    createMotherNatureStepsRequest(senderUsername);
                    break;
                }
            }
            case MOVE_STUDENT_ONTO_DININGROOM:{
                try {
                    game.executeActionMoveStudentToDiningRoom(((MoveStudentOntoDiningRoomMessage)message).getStudent());
                    createMoveStudentRequest(senderUsername);
                    break;
                } catch (IllegalClientInputException e) {
                    getVirtualViewByUsername(senderUsername).showErrorMessage(ErrorTypeID.ILLEGAL_MOVE);
                    createMoveStudentRequest(senderUsername);
                    break;
                } catch (LastMoveException e) {
                    createMotherNatureStepsRequest(senderUsername);
                    break;
                }
            }
            case NUMBER_STEPS_MOTHER_NATURE_REPLY: {
                try {
                    game.moveMotherNature(((MotherNatureReply) message).getNumSteps());
                    createCloudTileMessage(game.getCurrentPlayer().getUsername());
                } catch (IllegalClientInputException e) {
                    getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).showErrorMessage(ErrorTypeID.TOO_MANY_MOTHERNATURE_STEPS);
                    createMotherNatureStepsRequest(game.getCurrentPlayer().getUsername());
                } catch (EndGameException e) {
                    game.calculateWinner();
                }
                break;
            }
            case CLOUD_TILE_REPLY: {
                try {
                    game.chooseCloudTile(game.getCloudTileById(((CloudTileReply)message).getSelectedCloudTileID()));
                    game.nextGameTurn();
                    createMoveStudentRequest(game.getCurrentPlayer().getUsername());
                } catch (IllegalClientInputException e) {
                    getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).showErrorMessage(ErrorTypeID.ILLEGAL_MOVE);
                    createCloudTileMessage(game.getCurrentPlayer().getUsername());
                } catch (EndGameException e) {
                    //TODO implement
                } catch(NoSuchElementException e){
                    createAssistantCardRequestMessage(game.getCurrentPlayer().getUsername());
                }
                break;
            }
            case CHARACTER_CARD_REQUEST:{
                try {
                    game.useCharacterCard(((UseCharacterCardRequest)message).getCharacterCardID());
                    characterCardsNumberSwitch(((UseCharacterCardRequest)message).getCharacterCardID());
                    if(game.getCharacterCardByID(((UseCharacterCardRequest)message).getCharacterCardID()).hasParameters()){
                        //ask character card parameters
                    }
                    else{
                        //apply character card effect
                    }
                } catch (IllegalClientInputException e) {
                    getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).showErrorMessage(e.getTypeError());
                    getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).reSendLastMessage();
                }
                break;
            }
            default: characterCardsParametersSwitch(message);
        }
    }*/

    /**
     * It manages the creation of the effects and calls a method in game to execute them.
     *
     * @param message the message received
     */
    /*private void characterCardsParametersSwitch(Message message) {
        CardEffect cardEffect;
        switch (message.getMessageType()) {
            case CHARACTER_CARD_01_PARAMETER:
                try {
                    cardEffect = new CardEffect01(game.getCharacterCardByID(1), ((CharacterCard01Reply) message).getStudent(), ((CharacterCard01Reply) message).getIsland(), game.getBag());
                    game.useCharacterCard(1, cardEffect);
                } catch (IllegalClientInputException e) {
                    e.printStackTrace();
                }
                break;
            case CHARACTER_CARD_03_PARAMETER:
                try {
                    cardEffect = new CardEffect03(((CharacterCard03Reply) message).getIsland(), game.getPlayers(), game.getProfessors());
                    game.useCharacterCard(3, cardEffect);
                } catch (IllegalClientInputException e) {
                    e.printStackTrace();
                }
                break;
            case CHARACTER_CARD_05_PARAMETER:
                try {
                    cardEffect = new CardEffect05(((CharacterCard05Reply) message).getIsland(), game.getCharacterCardByID(5));
                    game.useCharacterCard(5, cardEffect);
                } catch (IllegalClientInputException e) {
                    e.printStackTrace();
                }
                break;
            case CHARACTER_CARD_09_PARAMETER:
                break;
            case CHARACTER_CARD_10_PARAMETER:
                break;
            case CHARACTER_CARD_11_PARAMETER:
                break;
            case CHARACTER_CARD_12_PARAMETER:
                break;
        }
    }

    private void characterCardsNumberSwitch(int characterCardID){
        CardEffect cardEffect;
        switch (characterCardID){
            case 1:
                getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).askStudentsToMoveOntoIslandCharCard01();
                break;
            case 2:
                try {
                    cardEffect = new CardEffect02(game.getCurrentPlayer(), game.getProfessorsManager());
                    game.useCharacterCard(characterCardID, cardEffect);
                } catch (IllegalClientInputException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).askIslandToCalculateInfluenceCharCard03();
            case 4:
                try {
                    cardEffect = new CardEffect04(game.getMotherNature());
                    game.useCharacterCard(characterCardID, cardEffect);
                } catch (IllegalClientInputException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).askIslandToPutNoEntryTileCharCard05();
                break;
            case 6:
                try {
                    cardEffect = new CardEffect06(game.getIslands());
                    game.useCharacterCard(characterCardID, cardEffect);
                } catch (IllegalClientInputException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).askStudentToSwitchFromCardToEntranceCharCard07();
                break;
            case 8:
                try {
                    cardEffect = new CardEffect08(game.getIslands());
                    game.useCharacterCard(characterCardID, cardEffect);
                } catch (IllegalClientInputException e) {
                    e.printStackTrace();
                }
                break;
            case 9:
                getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).askColorWithNoInfluenceCharCard09();
                break;
            case 10:
                getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).askStudentsToSwitchCharCard10();
                break;
            case 11:
                getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).askStudentsToMoveIntoDiningRoomCharCard11();
                break;
            case 12:
                getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).askColorCharCard12();
                break;
        }
    }*/


    /**
     * This method is called by a virtual view notify
     * @param message the message that was received from the client
     * Received message is used to modify the model, using override ti establish what modifies I have to do
     * modifyModel throws some exceptions, to manage the game flow correctly also in exceptional situations:
     * 4 situations can be verified:
     * Standard situation, the game is following the regular flow
     * Error situation, the user gave a wrong input, so the message must be re-sent
     * Exceptional situation, this game micro-phase is ended, so we need to start the next one. For example turn is changed.
     * End game situations, the game is over and winner must be calculated
     */
    @Override
    public void update(Message message) {
        try {
            ((ClientMessage)message).modifyModel(new ClientMessageModifierVisitor(game));
            ((ClientMessage)message).createStandardNextMessage(new ClientMessageStandardVisitor(game, getVirtualViewByUsername(game.getCurrentPlayer().getUsername())));
        }catch (IllegalClientInputException e) {
            getVirtualViewByUsername(game.getCurrentPlayer().getUsername()).showErrorMessage(e.getTypeError());
            ((ClientMessage)message).createInputErrorMessage(new ClientMessageErrorVisitor(game, getVirtualViewByUsername(game.getCurrentPlayer().getUsername())));
        }catch(LastMoveException | CharacterCardWithParametersException | ChangeTurnException e){
            ((ClientMessage)message).createExceptionalNextMessage(new ClientMessageExceptionalVisitor(game, getVirtualViewByUsername(game.getCurrentPlayer().getUsername())));
        }catch(EndGameException e) {
            game.calculateWinner();
        }
    }

}






    /*private Game game;


    public void addPlayer(String username){
        if(game.isUsernameChosen(username)){
            //return an error message
        }
        else
            game.addPlayer(username);
    }
     */


// ---------------------------------------------------------
// TO DELETE: some examples to check that the server works
// example, just to test if the server works
    /*private String username;


    // this is called by ServeOneClient
    public void setUsername(String username) {
        this.username = username;
    }

    public void print() {
        System.out.println("bro:" + username);
    }*/
