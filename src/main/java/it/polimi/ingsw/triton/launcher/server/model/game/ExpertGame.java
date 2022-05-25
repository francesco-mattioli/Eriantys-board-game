package it.polimi.ingsw.triton.launcher.server.model.game;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.ExpertMoveStudentIntoDiningRoom;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.UseCharacterCard;
import it.polimi.ingsw.triton.launcher.utils.exceptions.*;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.ChangeTurnMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.ExpertGameInfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.InfoCharacterCardPlayedMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.InfoStudentIntoDiningRoomMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.EmptyGeneralCoinSupplyMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.GiveAssistantDeckMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.UpdateWalletMessage;

import java.util.ArrayList;

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
     *
     * @param current the current player.
     * @throws ChangeTurnException if there's not another player that has to play in the current phase.
     */
    public void setNextPlayer(Player current) throws ChangeTurnException {
        int indexOfCurrentPlayer = game.getPlayers().indexOf(current);
        if (indexOfCurrentPlayer < game.getPlayers().size() - 1) {
            game.setCurrentPlayer(game.getPlayers().get(indexOfCurrentPlayer + 1));
        } else if (Wizard.values().length - game.getAvailableWizards().size() == game.getMaxNumberOfPlayers()) {
            setup();
            game.getAvailableWizards().clear();
            throw new ChangeTurnException();
        } else {
            game.setCurrentPlayer(game.getPlayers().get(0));
            throw new ChangeTurnException();
        }
    }

    /**
     * This method set the player's school board with the chosen tower color.
     *
     * @param player     the player that has chosen the tower color.
     * @param towerColor the color of the tower.
     * @throws IllegalClientInputException when the user's chosen tower color has already been chosen.
     */
    @Override
    public void chooseTowerColor(Player player, TowerColor towerColor) throws IllegalClientInputException, ChangeTurnException {
        try {
            if (game.getTowerColorChosen()[towerColor.ordinal()]) {
                throw new IllegalClientInputException(ErrorTypeID.TOWER_COLOR_ALREADY_CHOSEN);
            } else {
                player.setSchoolBoard(towerColor, game.getMaxNumberOfPlayers());
                game.getTowerColorChosen()[towerColor.ordinal()] = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalClientInputException(ErrorTypeID.GENERIC_CLIENT_INPUT_ERROR);
        }
        this.setNextPlayer(game.getCurrentPlayer());
    }

    /**
     * Set the wizard to the player and create a new request to the next player if present.
     *
     * @param player the player who sets the wizard.
     * @param wizard the wizard selected by the player.
     * @throws IllegalClientInputException when the user's chosen wizard has already been chosen.
     */
    @Override
    public void chooseWizard(Player player, Wizard wizard) throws IllegalClientInputException, ChangeTurnException {
        if (!game.getAvailableWizards().contains(wizard)) {
            throw new IllegalClientInputException(ErrorTypeID.WIZARD_ALREADY_CHOSEN);
        } else {
            player.setWizard(wizard);
            game.getAvailableWizards().remove(wizard);
        }
        this.setNextPlayer(game.getCurrentPlayer());
    }


    /**
     * This method executes the SETUP phase of the game.
     */
    @Override
    public void setup() {
        setupBag(); //PART 1 OF PHASE 3
        setupIslands(); //PART 2 OF PHASE 3
        game.getBag().fillBag(); //PHASE 4
        createCloudTiles(); //PHASE 5
        //PHASE 6 in constructor
        //PHASE 7, 8 & 9 are done when the player logs in for the first time
        setupEntrance(); //PHASE 10
        this.setupPlayers(); //PHASE 11
        this.drawCharacterCards(); //(PHASE 12) creates 3 character cards
        for (Player player : game.getPlayers()) {
            game.notify(new GiveAssistantDeckMessage(player.getUsername(), player.getAssistantDeck()));   // to review
            game.notify(new UpdateWalletMessage(player.getUsername(), player.getWallet().getValue()));
        }
        game.notify(new ExpertGameInfoMessage(characterCards, game.getIslandManager().getIslands(), game.getIslandManager().getMotherNature().getPosition(), game.getAllSchoolBoards(), game.getCloudTiles(), new String[game.getProfessors().length], getAllChosenWizards()));
        game.notify(new ChangeTurnMessage(game.getCurrentPlayer().getUsername()));
        game.planningPhase();
    }

    /**
     * This method sorts the players ArrayList random, and sets correctly the current player.
     */
    @Override
    public void setupPlayers() {
        for (Player player : game.getPlayers()) {
            try {
                generalCoinSupply.decrement();
                player.getWallet().increaseValue();
            } catch (EmptyGeneralCoinSupplyException e) {   //In this phase, this exception will never be thrown.
                game.notify(new EmptyGeneralCoinSupplyMessage(player.getUsername()));
            }
        }
        game.setupPlayers();
    }


    /**
     * Executes the action of moving a player from entrance to the dining room.
     * Because the game mode is expert, it checks also if the player's wallet needs to be updated.
     *
     * @param student the color of the student to move.
     */
    @Override
    public void executeActionMoveStudentToDiningRoom(Color student) throws LastMoveException, IllegalClientInputException {
        try {
            game.getCurrentPlayer().executeAction(new ExpertMoveStudentIntoDiningRoom(student, game.getCurrentPlayer(), generalCoinSupply));
            game.notify(new UpdateWalletMessage(game.getCurrentPlayer().getUsername(), game.getCurrentPlayer().getWallet().getValue()));
        } catch (EmptyGeneralCoinSupplyException e) {
            game.notify(new EmptyGeneralCoinSupplyMessage(game.getCurrentPlayer().getUsername()));
        }
        game.getProfessorsManager().updateProfessorsForAddInDiningRoom(game.getCurrentPlayer(), student, game.getProfessors());
        String moveDescription = game.getCurrentPlayer().getUsername() + " has moved a " + student.name().toLowerCase() + " student in his dining room";
        game.notify(new InfoStudentIntoDiningRoomMessage(game.getCurrentPlayer().getUsername(), game.getCurrentPlayer().getSchoolBoard(), game.professorsWithUsernameOwner(), moveDescription));
        game.getCurrentPlayer().setMoveCounter(game.getCurrentPlayer().getMoveCounter() + 1);
        game.checkNumberMoves();   //checks if the move was the last one throwing lastMoveException
    }

    /**
     * Creates three character cards.
     */
    @Override
    public void drawCharacterCards() {
        ArrayList<Integer> idAlreadyChosen = new ArrayList<>();
        int id;
        while (characterCards.size() < 3) {
            id = random.nextInt(12) + 1;
            if (!idAlreadyChosen.contains(id)) {
                characterCards.add(new CharacterCard(id, characterCardCostById(id), 0, game.getBag()));
                idAlreadyChosen.add(id);
            }
        }
    }

    /**
     * This method returns the cost of a character card based on the id.
     *
     * @param id of the Character Card drawn
     * @return the associated cost
     */
    private int characterCardCostById(int id) {
        if (id == 1 || id == 4 || id == 7 || id == 10)
            return 1;
        else if (id == 2 || id == 5 || id == 8 || id == 11)
            return 2;
        else return 3;
    }

    /**
     * Sends messages to the player to ask the parameters for some effects.
     *
     * @param idCard the id of the selected character card.
     */
    @Override
    public void useCharacterCard(Player player, int idCard) throws IllegalClientInputException, CharacterCardWithParametersException {
        if(game.getGameState() != GameState.ACTION_PHASE)
            throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE_FOR_PHASE);
        else{
            if (player.hasAlreadyPlayedACharacterCard()) {
                throw new IllegalClientInputException(ErrorTypeID.CHARACTER_CARD_ALREADY_PLAYED);
            } else {
                player.setTrueHasAlreadyPlayedACharacterCard();
                try {
                    game.getCurrentPlayer().executeAction(new UseCharacterCard(getCharacterCardByID(idCard), game.getCurrentPlayer(), generalCoinSupply));
                } catch (EmptyGeneralCoinSupplyException e) {
                    game.notify(new EmptyGeneralCoinSupplyMessage(game.getCurrentPlayer().getUsername()));
                }
                game.notify(new UpdateWalletMessage(game.getCurrentPlayer().getUsername(), game.getCurrentPlayer().getWallet().getValue()));
                if (getCharacterCardByID(idCard).hasParameters())
                    throw new CharacterCardWithParametersException();
            }
        }

    }

    /**
     * @param characterCardID the character card to play.
     * @param cardEffect      the effect to apply.
     */
    @Override
    public void applyCharacterCardEffect(int characterCardID, CardEffect cardEffect) throws IllegalClientInputException, EndGameException {
        if(game.getGameState() != GameState.ACTION_PHASE)
            throw new IllegalClientInputException(ErrorTypeID.ILLEGAL_MOVE_FOR_PHASE);
        else{
            try{
                getCharacterCardByID(characterCardID).executeEffect(cardEffect);
            } catch (EmptyGeneralCoinSupplyException e){
                game.notify(new EmptyGeneralCoinSupplyMessage(game.getCurrentPlayer().getUsername()));
            }
            game.notify(new InfoCharacterCardPlayedMessage(game.getCurrentPlayer().getUsername(), getCharacterCardByID(characterCardID), game.getIslandManager().getIslands(), game.getAllSchoolBoards(), game.professorsWithUsernameOwner()));
            game.notify(new UpdateWalletMessage(game.getCurrentPlayer().getUsername(), game.getCurrentPlayer().getWallet().getValue()));
        }
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
    public CharacterCard getCharacterCardByID(int id) throws IllegalClientInputException {
        if (id == -1)
            throw new IllegalClientInputException(ErrorTypeID.COMMAND_CANCELLED);
        for (CharacterCard characterCard : characterCards) {
            if (characterCard.getId() == id)
                return characterCard;
        }
        throw new IllegalClientInputException(ErrorTypeID.CHARACTER_CARD_NOT_AVAILABLE);
    }

    @Override
    public GeneralCoinSupply getGeneralCoinSupply(){
        return generalCoinSupply;
    }
}
