package it.polimi.ingsw.triton.launcher.client.view;


import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import java.util.List;

/**
 * This interface contains method prototypes that are called by the virtual view on server.
 * This virtual view uses this method to send ask messages without knowing how the client view is implemented.
 */
public interface View {

    /**
     * Asks the player to choose his tower color.
     * @param towerColorChosen the arrays of tower color, available and not.
     */
    void askTowerColor(boolean[] towerColorChosen);

    /**
     * Asks the player to choose the number of players and the game mode.
     */
    void askNumPlayersAndGameMode();

    /**
     * Asks the player to choose his wizard.
     * @param wizards the list of available wizards.
     */
    void askWizard(List<Wizard> wizards);

    /**
     * Asks the player to play one of his assistant card.
     */
    void askAssistantCard();

    /**
     * Asks the player to choose one of the available cloud tiles.
     */
    void askCloudTile();

    /**
     * Shows to the player his correct login.
     */
    void showLoginReply();

    /**
     * Asks the player to move a student from his entrance.
     */
    void askMoveStudentFromEntrance();

    /**
     * Asks the player to choose the number of steps mother nature has to do.
     */
    void askNumberStepsMotherNature();

    /**
     * Asks the player to insert the parameters of the character card he wants in order to build the effect.
     * @param id the id of the character card the player wants to play.
     */
    void askCharacterCardParameters(int id);

    /**
     * Shows to the player that an error occurs during the execution of a method.
     * @param errorTypeID the error type that occurs during the execution.
     */
    void showErrorMessage(ErrorTypeID errorTypeID);
}
