package it.polimi.ingsw.triton.launcher.utils;


import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.util.ArrayList;

public interface View {

    public void askTowerColor(boolean[] towerColorChosen);

    public void askWizard(ArrayList<Wizard> wizards);

    public void askAssistantCard();

    public void showChangeInfluenceMessage();

    public void askCloudTile();

    public void showDisconnectionMessage();

    public void showEmptyBagMessage();

    public void showErrorMessage();

    public void showFillCloudTilesMessage();

    public void showFullLobbyMessage();

    public void showGameInfo();

    public void showGenericMessage();

    public void showInfoActionPhase();

    public void showInfoAssistantCardPlayed();

    public void showLobbyMessage();

    public void showLoginReply();

    public void showMergeIslandsMessage();

    public void showMotherNaturePosition();

    public void askMoveStudentFromEntrance();

    public void showMoveTowerOntoIsland();

    public void showMoveTowerOntoSchoolBoard();

    public void askNumberStepsMotherNature();

    public void askGameMode();

    public void askPlayersNumber();

    public void showTieMessage();



    public void showWinMessage();

    public void showLoseMessage();


    public void showYourTurnMessage();

    public void showAvailableCharacterCard();

    public void askStudentsToMoveOntoIslandCharCard01();

    public void askIslandToCalculateInfluenceCharCard03();

    public void askIslandToPutNoEntryTileCharCard05();

    public void askStudentToSwitchFromCardToEntranceCharCard07();

    public void askColorWithNoInfluenceCharCard09();

    public void askStudentsToSwitchCharCard10();

    public void askStudentsToMoveIntoDiningRoomCharCard11();

    public void askColorCharCard12();

    void showErrorMessage(ErrorTypeID fullLobby);
}
