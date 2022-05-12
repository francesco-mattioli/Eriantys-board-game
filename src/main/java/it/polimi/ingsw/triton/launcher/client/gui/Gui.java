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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.GridBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    if (wizard.equals(Wizard.YELLOW))
                        wizardsImages.put(new Image("file:" + currentPath + "/Yellow-Wizard.png"),wizard);
                    if (wizard.equals(Wizard.GREEN))
                        wizardsImages.put(new Image("file:" + currentPath + "/Green-Wizard.png"),wizard);
                    if (wizard.equals(Wizard.BLUE))
                        wizardsImages.put(new Image("file:" + currentPath + "/Blue-Wizard.png"),wizard);
                    if (wizard.equals(Wizard.PURPLE))
                        wizardsImages.put(new Image("file:" + currentPath + "/Purple-Wizard.png"),wizard);
                }
                ((WizardSceneController)loader.getController()).setWizards(wizardsImages);
                ((WizardSceneController)loader.getController()).getWizardImageView().setImage((Image) wizardsImages.keySet().toArray()[0]);
                ((WizardSceneController)loader.getController()).getLeftSwitch().setFill(Color.GRAY);
                ((WizardSceneController)loader.getController()).getLeftSwitch().setOpacity(0.5);
                if(wizards.size() == 1) {
                    ((WizardSceneController)loader.getController()).getRightSwitch().setFill(Color.GRAY);
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
                    if (assistantCard.getType().equals(AssistantCardType.CAT))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Cat.png"),assistantCard);
                    if (assistantCard.getType().equals(AssistantCardType.DOG))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Dog.png"),assistantCard);
                    if (assistantCard.getType().equals(AssistantCardType.SNAKE))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Snake.png"),assistantCard);
                    if (assistantCard.getType().equals(AssistantCardType.TIGER))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Tiger.png"),assistantCard);
                    if (assistantCard.getType().equals(AssistantCardType.EAGLE))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Eagle.png"),assistantCard);
                    if (assistantCard.getType().equals(AssistantCardType.TURTLE))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Turtle.png"),assistantCard);
                    if (assistantCard.getType().equals(AssistantCardType.DUCK))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Duck.png"),assistantCard);
                    if (assistantCard.getType().equals(AssistantCardType.ELEPHANT))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Elephant.png"),assistantCard);
                    if (assistantCard.getType().equals(AssistantCardType.OCTOPUS))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Octopus.png"),assistantCard);
                    if (assistantCard.getType().equals(AssistantCardType.FOX))
                        assistantCardImages.put(new Image("file:" + currentPath + "/Fox.png"),assistantCard);
                }
                ((AssistantCardSceneController)loader.getController()).setAssistantCards(assistantCardImages);
                ((AssistantCardSceneController)loader.getController()).getAssistantCardImageView().setImage((Image) assistantCardImages.keySet().toArray()[0]);
                ((AssistantCardSceneController)loader.getController()).getLeftSwitch().setFill(Color.GRAY);
                ((AssistantCardSceneController)loader.getController()).getLeftSwitch().setOpacity(0.5);
                if(assistantCardImages.size() == 1) {
                    ((WizardSceneController)loader.getController()).getRightSwitch().setFill(Color.GRAY);
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
                mainStage  = new Stage();
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
                        if(i == 0)
                            ((ImageView)(studentsOnMyEntrance.get(offset))).setImage(new Image("file:"+currentPath+"/YellowStudent.png"));
                        if(i==1)
                            ((ImageView)(studentsOnMyEntrance.get(offset))).setImage(new Image("file:"+currentPath+"/BlueStudent.png"));
                        if(i==2)
                            ((ImageView)(studentsOnMyEntrance.get(offset))).setImage(new Image("file:"+currentPath+"/GreenStudent.png"));
                        if(i==3)
                            ((ImageView)(studentsOnMyEntrance.get(offset))).setImage(new Image("file:"+currentPath+"/RedStudent.png"));
                        if(i==4)
                            ((ImageView)(studentsOnMyEntrance.get(offset))).setImage(new Image("file:"+currentPath+"/PinkStudent.png"));
                        ((ImageView)(studentsOnMyEntrance.get(offset))).setVisible(true);
                        offset++;
                    }
                }
                currentPath = new java.io.File("src/main/resources/Images/Islands").getAbsolutePath().replace('\\','/');
                List<Node> islands = ((MainScene2PlayersController)loader.getController()).getIslandPane().getChildren();
                int dim1 = clientModel.getIslands().size()/2;
                GridPane superiorGrid = new GridPane();
                GridPane inferiorGrid = new GridPane();
                for(int i = 0; i<clientModel.getIslands().size(); i++){
                    if(i<dim1){
                        HBox box = new HBox();
                        for(int j=0; j<clientModel.getIslands().get(i).getDim(); j++){
                            box.getChildren().add(new ImageView(new Image("file:" + currentPath + "Island1.png")));
                        }
                        superiorGrid.add(box, 0,i);
                    }
                    else{
                        HBox box = new HBox();
                        for(int j=0; j<clientModel.getIslands().get(i).getDim(); j++){
                            box.getChildren().add(new ImageView(new Image("file:" + currentPath + "Island1.png")));
                        }
                        inferiorGrid.add(box, 0,i-dim1);
                    }
                }
                islands.add(superiorGrid);
                islands.add(inferiorGrid);
                inferiorGrid.setLayoutY(200);
                mainStage.setScene(scene);
                mainStage.show();
                activeStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
