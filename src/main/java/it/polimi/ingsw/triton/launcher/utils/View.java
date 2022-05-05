package it.polimi.ingsw.triton.launcher.utils;


import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.util.ArrayList;

public interface View {

    void askTowerColor(boolean[] towerColorChosen);

    void askWizard(ArrayList<Wizard> wizards);

    void askAssistantCard();

    void askCloudTile();

    void showLoginReply();

    void askMoveStudentFromEntrance();

    void askNumberStepsMotherNature();

    void askGameMode();

    void askPlayersNumber();

    void askStudentsToMoveOntoIslandCharCard01();

    void askIslandToCalculateInfluenceCharCard03();

    void askIslandToPutNoEntryTileCharCard05();

    void askStudentToSwitchFromCardToEntranceCharCard07();

    void askColorWithNoInfluenceCharCard09();

    void askStudentsToSwitchCharCard10();

    void askStudentsToMoveIntoDiningRoomCharCard11();

    void askColorCharCard12();

    void showErrorMessage(ErrorTypeID fullLobby);
}
