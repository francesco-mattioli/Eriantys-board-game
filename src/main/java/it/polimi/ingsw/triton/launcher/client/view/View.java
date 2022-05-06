package it.polimi.ingsw.triton.launcher.client.view;


import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.util.ArrayList;

public interface View {

    void askTowerColor(boolean[] towerColorChosen);

    void askNumOfPlayers();

    void askWizard(ArrayList<Wizard> wizards);

    void askAssistantCard();

    void askCloudTile();

    void showLoginReply();

    void askMoveStudentFromEntrance();

    void askNumberStepsMotherNature();

    void askGameMode();

    void askCharacterCardParameters(int id);

    void showErrorMessage(ErrorTypeID fullLobby);
}
