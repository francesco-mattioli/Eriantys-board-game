package it.polimi.ingsw.triton.launcher.client.gui;

import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.client.gui.scenes.*;
import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.UpdatedServerInfoMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Gui extends Observable<Message> implements ClientView {
    private ClientModel clientModel;
    private Client client;
    private Stage mainStage;
    private Stage activeStage;
    private GameState actualGamePhase = GameState.SETUP;

    public Gui(Stage activeStage) {
        this.activeStage = activeStage;
    }

    @Override
    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public void askIpAddress() {

    }

    @Override
    public void askUsername() {
        client = new Client(this);
        this.addObserver(client);
        this.clientModel=new ClientModel();
        notify(new UpdatedServerInfoMessage("localhost"));
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((LoginSceneController)loader.getController()).addObserver(client);
                Scene scene = new Scene(root);
                activeStage.setScene(scene);
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
    public void showGameInfo(ArrayList<Island> islands, Map<String, SchoolBoard> schoolBoards, ArrayList<CloudTile> cloudTiles, Island motherNaturePosition, String[] professors) {

    }

    @Override
    public void showGameInfo(ArrayList<CharacterCard> availableCharacterCards, ArrayList<Island> islands, Map<String, SchoolBoard> schoolBoards, ArrayList<CloudTile> cloudTiles, Island motherNaturePosition, String[] professors) {

    }


    @Override
    public void showChangePhase(GameState gameState) {
        if(gameState==(GameState.PLANNING_PHASE) && actualGamePhase==(GameState.SETUP)){
            initializeMainStage();
        }
        actualGamePhase = gameState;
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
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/towerColor-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((TowerColorSceneController)loader.getController()).addObserver(client);
                ((TowerColorSceneController)loader.getController()).setUsername(clientModel.getUsername());
                Map<String, TowerColor> towerColorMap = new HashMap<>();
                for (int i = 0; i < towerColorChosen.length; i++){
                    if (!towerColorChosen[i]){
                        towerColorMap.put(TowerColor.values()[i].name(), TowerColor.values()[i]);
                    }
                }
                ((TowerColorSceneController)loader.getController()).setTowerColorMap(towerColorMap);
                ((TowerColorSceneController)loader.getController()).getTowerColorChoice().getItems().addAll(towerColorMap.keySet());
                Scene scene = new Scene(root);
                activeStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void askNumPlayersAndGameMode() {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameModeAndNumOfPlayers-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((GameModeAndNumOfPlayersSceneController)loader.getController()).addObserver(client);
                ((GameModeAndNumOfPlayersSceneController)loader.getController()).setUsername(clientModel.getUsername());
                Scene scene = new Scene(root);
                activeStage.setScene(scene) ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void askWizard(ArrayList<Wizard> wizards) {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/wizard-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((WizardSceneController)loader.getController()).addObserver(client);
                ((WizardSceneController)loader.getController()).setUsername(clientModel.getUsername());
                Map<Image,Wizard> wizardsImages = new HashMap<>();
                String currentPath = new java.io.File("src/main/resources/Images/Wizards").getAbsolutePath().replace('\\','/');
                for (Wizard wizard: wizards) {
                    wizardsImages.put(new Image("file:" + currentPath + wizard.getImagePath()),wizard);

                }
                ((WizardSceneController)loader.getController()).setWizards(wizardsImages);
                ((WizardSceneController)loader.getController()).getWizardImageView().setImage((Image) wizardsImages.keySet().toArray()[0]);
                ((WizardSceneController)loader.getController()).getLeftSwitch().setFill(javafx.scene.paint.Color.GRAY);
                ((WizardSceneController)loader.getController()).getLeftSwitch().setOpacity(0.5);
                if(wizards.size() == 1) {
                    ((WizardSceneController)loader.getController()).getRightSwitch().setFill(javafx.scene.paint.Color.GRAY);
                    ((WizardSceneController)loader.getController()).getRightSwitch().setOpacity(0.5);
                }
                Scene scene = new Scene(root);
                activeStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void askAssistantCard() {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assistantCard-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((AssistantCardSceneController)loader.getController()).addObserver(client);
                ((AssistantCardSceneController)loader.getController()).setUsername(clientModel.getUsername());
                Map<Image,AssistantCard> assistantCardImages = new HashMap<>();
                String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\','/');
                for (AssistantCard assistantCard: clientModel.getAssistantDeck().getAssistantDeck()) {
                    assistantCardImages.put(new Image("file:" + currentPath + assistantCard.getType().getImagePath()),assistantCard);
                }
                ((AssistantCardSceneController)loader.getController()).setAssistantCards(assistantCardImages);
                ((AssistantCardSceneController)loader.getController()).getAssistantCardImageView().setImage((Image) assistantCardImages.keySet().toArray()[0]);
                ((AssistantCardSceneController)loader.getController()).getLeftSwitch().setFill(javafx.scene.paint.Color.GRAY);
                ((AssistantCardSceneController)loader.getController()).getLeftSwitch().setOpacity(0.5);
                if(assistantCardImages.size() == 1) {
                    ((WizardSceneController)loader.getController()).getRightSwitch().setFill(javafx.scene.paint.Color.GRAY);
                    ((WizardSceneController)loader.getController()).getRightSwitch().setOpacity(0.5);
                }
                Scene scene = new Scene(root);
                activeStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
    public void askCharacterCardParameters(int id) {

    }

    @Override
    public void showErrorMessage(ErrorTypeID fullLobby) {

    }


    @Override
    public void showTieMessage() {

    }

    @Override
    public void showAbortMessage() {

    }

    @Override
    public void showWinMessage() {

    }

    @Override
    public void showLoseMessage(String winnerUsername) {

    }

    private void initializeMainStage(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                mainStage = new Stage();
                mainStage.setFullScreen(true);
                List<Node> studentsOnMyDiningRoom = ((MainScene2PlayersController)loader.getController()).getMyDiningRoomGrid().getChildren();
                int offset = 0;
                for(int i = 0; i < clientModel.getMySchoolBoard().getDiningRoom().length; i++){
                    for(int j = 0; j<clientModel.getMySchoolBoard().getDiningRoom()[i]; j++) {
                        ((ImageView)(studentsOnMyDiningRoom.get(offset+j))).setVisible(true);
                    }
                    offset+=10;
                }
                List<Node> studentsOnMyEntrance = ((MainScene2PlayersController)loader.getController()).getMyEntranceGrid().getChildren();
                offset = 0;
                String currentPath = new java.io.File("src/main/resources/Images/Students").getAbsolutePath().replace('\\','/');
                for(int i = 0; i < clientModel.getMySchoolBoard().getEntrance().length; i++){
                    for(int j = 0; j<clientModel.getMySchoolBoard().getEntrance()[i]; j++) {
                        ((ImageView)(studentsOnMyEntrance.get(offset))).setImage(new Image("file:" + currentPath + Color.values()[i].getStudentImagePath()));
                        ((ImageView)(studentsOnMyEntrance.get(offset))).setVisible(true);
                        offset++;
                    }
                }
                currentPath = new java.io.File("src/main/resources/Images/Islands").getAbsolutePath().replace('\\','/');
                List<Node> islands = ((MainScene2PlayersController)loader.getController()).getIslandPane().getChildren();
                int dim1 = clientModel.getIslands().size()/2;
                int dim2 = clientModel.getIslands().size() - dim1;
                TilePane superiorGrid = new TilePane();
                TilePane inferiorGrid = new TilePane();
                superiorGrid.setPrefRows(1);
                superiorGrid.setPrefColumns(dim1);
                inferiorGrid.setPrefRows(1);
                inferiorGrid.setPrefColumns(dim2);
                List<Node> superiorList = new ArrayList<>();
                List<Node> inferiorList = new ArrayList<>();
                for(int i = 0; i<clientModel.getIslands().size(); i++){
                    HBox box = new HBox();
                    for(int j = 0; j < clientModel.getIslands().get(i).getDim(); j++){
                        Image image = new Image("file:" + currentPath + "/Island1.png");
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);
                        box.getChildren().add(imageView);
                        if(i < dim1){
                            superiorList.add(box);
                        }
                        else {
                            inferiorList.add(box);
                        }
                    }
                }
                superiorGrid.getChildren().addAll(superiorList);
                Collections.reverse(inferiorList);
                inferiorGrid.getChildren().addAll(inferiorList);
                superiorGrid.setLayoutX(0);
                superiorGrid.setLayoutY(0);
                superiorGrid.setHgap(100);
                inferiorGrid.setLayoutX(0);
                inferiorGrid.setLayoutY(200);
                inferiorGrid.setHgap(100);
                islands.add(superiorGrid);
                islands.add(inferiorGrid);
                mainStage.setScene(scene);
                mainStage.show();
                activeStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
