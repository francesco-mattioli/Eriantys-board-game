package it.polimi.ingsw.triton.launcher.server.model.game;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.ExpertMoveStudentIntoDiningRoom;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.UseCharacterCard;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.EmptyGeneralCoinSupplyMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.GiveAssistantDeckMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.UpdateWalletMessage;

import java.util.ArrayList;
import java.util.Random;

public class ExpertGame extends GameDecorator {
    private final GeneralCoinSupply generalCoinSupply;
    private final ArrayList<CharacterCard> characterCards;


    public ExpertGame(GameMode game) {
        super(game);
        this.characterCards = new ArrayList<>();
        this.generalCoinSupply = new GeneralCoinSupply(20);
    }

    /**
     * This method is used to manage the turns in preparation and planning phases.
     * When last wizard has been chosen, we call setup method and order randomly the player arraylist
     * @param current the current player.
     * @throws ChangeTurnException if there's not another player that has to play in the current phase.
     */
    public void setNextPlayer(Player current) throws ChangeTurnException{
        int indexOfCurrentPlayer = game.getPlayers().indexOf(current);
        if(indexOfCurrentPlayer < game.getPlayers().size()-1){
            game.setCurrentPlayer(game.getPlayers().get(indexOfCurrentPlayer+1));
        }
        else if(Wizard.values().length - game.getAvailableWizards().size() == game.getMaxNumberOfPlayers()) {
            setup();
            game.getAvailableWizards().clear();
            throw new ChangeTurnException();
        }
        else{
            game.setCurrentPlayer(game.getPlayers().get(0));
            throw new ChangeTurnException();
        }
    }

    /**
     * This method set the player's school board with the chosen tower color.
     * @param username the username of the player that has chosen the tower color.
     * @param towerColor the color of the tower.
     * @throws IllegalClientInputException when the user's chosen tower color has already been chosen.
     */
    @Override
    public void chooseTowerColor(String username, TowerColor towerColor) throws IllegalClientInputException, ChangeTurnException {
        try{
            if(game.getTowerColorChosen()[towerColor.ordinal()]){
                throw new IllegalClientInputException(ErrorTypeID.TOWER_COLOR_ALREADY_CHOSEN);
            }
            else{
                getPlayerByUsername(username).setSchoolBoard(towerColor, game.getMaxNumberOfPlayers());
                game.getTowerColorChosen()[towerColor.ordinal()] = true;
            }
        } catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalClientInputException(ErrorTypeID.GENERIC_CLIENT_INPUT_ERROR);
        }
        this.setNextPlayer(game.getCurrentPlayer());
    }

    /**
     * Set the wizard to the player and create a new request to the next player if present.
     * @param username the player's username of who sets the wizard.
     * @param wizard the wizard selected by the player.
     * @throws IllegalClientInputException when the user's chosen wizard has already been chosen.
     */
    @Override
    public void chooseWizard(String username, Wizard wizard) throws IllegalClientInputException, ChangeTurnException {
        if(!game.getAvailableWizards().contains(wizard)){
            throw new IllegalClientInputException(ErrorTypeID.WIZARD_ALREADY_CHOSEN);
        }
        else{
            getPlayerByUsername(username).setWizard(wizard);
            game.getAvailableWizards().remove(wizard);
        }
        this.setNextPlayer(game.getCurrentPlayer());
    }


    /**
     * This method executes the SETUP phase of the game.
     */
    @Override
    public void setup() {
        setupMotherNature(); //PHASE 2
        setupBag(); //PART 1 OF PHASE 3
        setupIslands(); //PART 2 OF PHASE 3
        game.getBag().fillBag(); //PHASE 4
        createCloudTiles(); //PHASE 5
        //PHASE 6 in constructor
        //PHASE 7, 8 & 9 are done when the player logs in for the first time
        setupEntrance(); //PHASE 10
        this.setupPlayers(); //PHASE 11
        this.drawCharacterCards(); //(PHASE 12) creates 3 character cards
        for(Player player: game.getPlayers()) {
            game.notify(new GiveAssistantDeckMessage(player.getUsername(), player.getAssistantDeck()));   // to review
            game.notify(new UpdateWalletMessage(player.getUsername(), player.getWallet().getValue()));
        }
        game.notify(new ExpertGameInfoMessage(characterCards, game.getIslands(), game.getMotherNature().getPosition(), game.getAllSchoolBoards(), game.getCloudTiles(), new String[game.getProfessors().length],getAllChosenWizards()));
        game.notify(new ChangeTurnMessage(game.getCurrentPlayer().getUsername()));
        this.planningPhase();
    }

    /**
     * This method sorts the players ArrayList random, and sets correctly the current player.
     */
    @Override
    public void setupPlayers(){
        for(Player player: game.getPlayers()){
            generalCoinSupply.decrement();
            player.getWallet().increaseValue();
        }
        game.setupPlayers();
    }

    /**
     * Executes a part of the planning phase, filling the cloud tiles and resetting the assistant
     * cards played in last round.
     */
    @Override
    public void planningPhase() {
        game.planningPhase();
        // Need to set false the already played  boolean attribute
        for(Player player : game.getPlayers()){
            player.resetAlreadyPlayedAnCharacterCard();
        }
    }

    /**
     * Executes the action of moving a player from entrance to the dining room.
     * Because the game mode is expert, it checks also if the player's wallet needs to be updated.
     * @param student the color of the student to move.
     */
    @Override
    public void executeActionMoveStudentToDiningRoom(Color student) throws LastMoveException, IllegalClientInputException {
        boolean empty = generalCoinSupply.isEmpty();
        game.getCurrentPlayer().executeAction(new ExpertMoveStudentIntoDiningRoom(student, game.getCurrentPlayer(), generalCoinSupply));
        if(game.getCurrentPlayer().getSchoolBoard().getDiningRoom()[student.ordinal()] % 3 == 0 && !empty)
            game.notify(new UpdateWalletMessage(game.getCurrentPlayer().getUsername(), game.getCurrentPlayer().getWallet().getValue()));
        else if(game.getCurrentPlayer().getSchoolBoard().getDiningRoom()[student.ordinal()] % 3 == 0 && empty)
            game.notify(new EmptyGeneralCoinSupplyMessage(game.getCurrentPlayer().getUsername()));
        game.getProfessorsManager().updateProfessors(game.getCurrentPlayer(), student, game.getProfessors());
        String moveDescription = game.getCurrentPlayer().getUsername() + " has moved a " + student.name().toLowerCase() + " student in his dining room";
        game.notify(new InfoStudentIntoDiningRoomMessage(game.getCurrentPlayer().getUsername(), game.getCurrentPlayer().getSchoolBoard(),game.professorsWithUsernameOwner(), moveDescription));
        game.getCurrentPlayer().setMoveCounter(game.getCurrentPlayer().getMoveCounter() + 1);
        game.checkNumberMoves();   //checks if the move was the last one throwing lastMoveException
    }

    /**
     * Creates three character cards.
     * Each one costs 1 coin.
     */
    private void drawCharacterCards() {
        Random randomNumber;
        ArrayList<Integer> idAlreadyChosen = new ArrayList<>();
        int id;
        while(characterCards.size() < 3){
            randomNumber = new Random();
            id = randomNumber.nextInt(12);
            if(!idAlreadyChosen.contains(id)){
                characterCards.add(new CharacterCard(id, 1, 0, game.getBag()));
                idAlreadyChosen.add(id);
            }
        }
    }

    /**
     * Sends messages to the player to ask the parameters for some effects.
     * @param idCard the id of the selected character card.
     */
    @Override
    public void useCharacterCard(String username,int idCard) throws IllegalClientInputException, CharacterCardWithParametersException {
        if(game.getPlayerByUsername(username).hasAlreadyPlayedACharacterCard()) {
            throw new IllegalClientInputException(ErrorTypeID.CHARACTER_CARD_ALREADY_PLAYED);
        }
        else {
            game.getCurrentPlayer().executeAction(new UseCharacterCard(getCharacterCardByID(idCard), game.getCurrentPlayer(), generalCoinSupply));
            game.notify(new UpdateWalletMessage(game.getCurrentPlayer().getUsername(), game.getCurrentPlayer().getWallet().getValue()));
            if (getCharacterCardByID(idCard).hasParameters())
                throw new CharacterCardWithParametersException();
        }
    }

    /**
     * @param characterCardID the character card to play.
     * @param cardEffect the effect to apply.
     */
    @Override
    public void applyCharacterCardEffect(int characterCardID, CardEffect cardEffect) throws IllegalClientInputException, EndGameException {
        getCharacterCardByID(characterCardID).executeEffect(cardEffect);
        game.notify(new InfoCharacterCardPlayedMessage(game.getCurrentPlayer().getUsername(), getCharacterCardByID(characterCardID), game.getIslands(), game.getAllSchoolBoards()));
    }

    @Override
    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    /**
     * @param id the id of the character card.
     * @return the character card with that id.
     * @throws IllegalClientInputException if the character card with that id doesn't exist.
     */
    @Override
    public CharacterCard getCharacterCardByID(int id) throws IllegalClientInputException{
        for(CharacterCard characterCard : characterCards){
            if(characterCard.getId() == id)
                return  characterCard;
        }
        throw new IllegalClientInputException(ErrorTypeID.CHARACTER_CARD_NOT_AVAILABLE);
    }
}
