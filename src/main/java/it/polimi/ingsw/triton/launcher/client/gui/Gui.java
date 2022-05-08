package it.polimi.ingsw.triton.launcher.client.gui;

import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.client.gui.scenes.LoginSceneController;
import it.polimi.ingsw.triton.launcher.client.gui.scenes.MenuSceneController;
import it.polimi.ingsw.triton.launcher.client.gui.scenes.NumOfPlayersSceneController;
import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Gui extends Observable<Message> implements ClientView {
    private ClientModel clientModel;
    private Client client;
    private Stage stage;

    public void setActiveStage(Stage activeStage) {
        this.activeStage = activeStage;
    }

    private Stage activeStage;

    public Gui(){
        stage = new Stage();
    }


    @Override
    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public void askUsername() {
        client = new Client(this);
        this.addObserver(client);
        this.clientModel=new ClientModel();
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((LoginSceneController)loader.getController()).addObserver(client);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                if (activeStage != null)
                    activeStage.close();
                activeStage = stage;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void showGenericMessage(String genericMessage) {

    }

    @Override
    public void showLobbyMessage(ArrayList<String> onlineNicknames, int maxNumberPlayers) {

    }

    @Override
    public void showGameInfo(ArrayList<CharacterCard> availableCharacterCards, ArrayList<Island> islands, Map<String, SchoolBoard> schoolBoards, ArrayList<CloudTile> cloudTiles, Island motherNaturePosition) {

    }

    @Override
    public void showChangePhase(GameState gameState) {

    }

    @Override
    public void showDisconnectionMessage() {

    }


    @Override
    public void showEmptyBagMessage() {

    }


    @Override
    public void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard) {

    }

    @Override
    public void askTowerColor(boolean[] towerColorChosen) {

    }

    @Override
    public void askNumOfPlayers() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/numOfPlayers-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((NumOfPlayersSceneController)loader.getController()).addObserver(client);
                ((NumOfPlayersSceneController)loader.getController()).setUsername(clientModel.getUsername());
                Scene scene = new Scene(root);
                stage.setScene(scene) ;
                stage.show();
                if (activeStage != null)
                    activeStage.close();
                activeStage = stage;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void askWizard(ArrayList<Wizard> wizards) {

    }

    @Override
    public void askAssistantCard() {

    }

    @Override
    public void askCloudTile() {

    }

    @Override
    public void showLoginReply() {

    }

    @Override
    public void showMoveTowerOntoIsland(int islandId) {

    }

    @Override
    public void showMoveTowerOntoSchoolBoard(String username, SchoolBoard schoolBoard) {

    }

    @Override
    public void askMoveStudentFromEntrance() {

    }

    @Override
    public void askNumberStepsMotherNature() {

    }

    @Override
    public void askGameMode() {

    }

    @Override
    public void askCharacterCardParameters(int id) {

    }

    @Override
    public void showErrorMessage(ErrorTypeID fullLobby) {

    }


    @Override
    public void showTieMessage() {

    }

    @Override
    public void showWinMessage() {

    }

    @Override
    public void showLoseMessage(String winnerUsername) {

    }

    @Override
    public void showYourTurnMessage() {

    }

    @Override
    public void showAvailableCharacterCard() {

    }
}
