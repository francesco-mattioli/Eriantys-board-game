package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.ExpertMoveStudentIntoDiningRoom;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.UseCharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.EmptyGeneralCoinSupplyMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.GiveAssistantDeckMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.UpdateWalletMessage;

import java.util.ArrayList;
import java.util.Random;

public class ExpertGame extends Game{
    private final GeneralCoinSupply generalCoinSupply;
    private final ArrayList<CharacterCard> characterCards;
    private static ExpertGame instance;

    public static ExpertGame instance(int maxNumberOfPlayers){
        if(instance==null)
            instance=new ExpertGame(maxNumberOfPlayers);
        return instance;
    }

    private ExpertGame(int maxNumberOfPlayers) {
        super(maxNumberOfPlayers);
        this.characterCards = new ArrayList<>();
        this.generalCoinSupply = new GeneralCoinSupply(20);
    }

    /**
     * This method executes the SETUP phase of the game.
     */
    @Override
    public void setup() {
        super.setupMotherNature(); //PHASE 2
        super.setupBag(); //PART 1 OF PHASE 3
        super.setupIslands(); //PART 2 OF PHASE 3
        super.bag.fillBag(); //PHASE 4
        super.createCloudTiles(); //PHASE 5
        super.professorsManager = new ProfessorsManager(); //PHASE 6
        //PHASE 7, 8 & 9 are done when the player logs in for the first time
        super.setupEntrance(); //PHASE 10
        this.setupPlayers(); //PHASE 11
        this.drawCharacterCards(); //(PHASE 12) creates 3 character cards
        for(Player player: players) {
            notify(new GiveAssistantDeckMessage(player.getUsername(), player.getAssistantDeck()));   // to review
            notify(new UpdateWalletMessage(player.getUsername(), player.getWallet().getValue()));
        }
        notify(new ExpertGameInfoMessage(characterCards, islands, motherNature.getPosition(), getAllSchoolBoards(), cloudTiles, new String[professors.length]));
        notify(new ChangeTurnMessage(currentPlayer.getUsername()));
        this.planningPhase();
    }

    /**
     * This method sorts the players ArrayList random, and sets correctly the current player.
     */
    @Override
    protected void setupPlayers(){
        for(Player player: players){
            generalCoinSupply.decrement();
            player.getWallet().increaseValue();
        }
        super.setupPlayers();
    }

    @Override
    public void planningPhase() {
        super.planningPhase();
        // Need to set false the already played  boolean attribute
        for(Player player : players){
            player.resetAlreadyPlayedAnCharacterCard();
        }
    }

    @Override
    public void executeActionMoveStudentToDiningRoom(Color student) throws LastMoveException, IllegalClientInputException {
        boolean empty = generalCoinSupply.isEmpty();
        currentPlayer.executeAction(new ExpertMoveStudentIntoDiningRoom(student, currentPlayer, generalCoinSupply));
        if(currentPlayer.getSchoolBoard().getDiningRoom()[student.ordinal()] % 3 == 0 && !empty)
            notify(new UpdateWalletMessage(currentPlayer.getUsername(), currentPlayer.getWallet().getValue()));
        else if(currentPlayer.getSchoolBoard().getDiningRoom()[student.ordinal()] % 3 == 0 && empty)
            notify(new EmptyGeneralCoinSupplyMessage(currentPlayer.getUsername()));
        professorsManager.updateProfessors(currentPlayer, student, professors);
        String moveDescription = currentPlayer.getUsername() + " has moved a " + student.name().toLowerCase() + " student in his dining room";
        notify(new InfoStudentIntoDiningRoomMessage(currentPlayer.getUsername(), currentPlayer.getSchoolBoard(),professorsWithUsernameOwner(), moveDescription));
        currentPlayer.setMoveCounter(currentPlayer.getMoveCounter() + 1);
        checkNumberMoves();   //checks if the move was the last one throwing lastMoveException
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
                characterCards.add(new CharacterCard(id, 1, 0, this.bag));
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
        if(getPlayerByUsername(username).hasAlreadyPlayedACharacterCard()) {
            throw new IllegalClientInputException(ErrorTypeID.CHARACTER_CARD_ALREADY_PLAYED);
        }
        else {
            currentPlayer.executeAction(new UseCharacterCard(getCharacterCardByID(idCard), currentPlayer, generalCoinSupply));
            notify(new UpdateWalletMessage(currentPlayer.getUsername(), currentPlayer.getWallet().getValue()));
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
        notify(new InfoCharacterCardPlayedMessage(currentPlayer.getUsername(), getCharacterCardByID(characterCardID), islands, getAllSchoolBoards()));
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
