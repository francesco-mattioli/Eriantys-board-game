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
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Gui extends Observable<Message> implements ClientView {
    private ClientModel clientModel;
    private Client client;
    private Stage mainStage;
    private Stage activeStage;
    private FXMLLoader mainLoader;
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
        client = new Client(this);
        this.addObserver(client);
        this.clientModel = new ClientModel();
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ipAddress-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((IpAddressSceneController) loader.getController()).addObserver(client);
                Scene scene = new Scene(root);
                activeStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void askUsername() {

        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((LoginSceneController) loader.getController()).addObserver(client);
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
    public void showLobbyMessage(ArrayList<String> onlineNicknames) {

    }

    @Override
    public void showGameInfo() {

    }

    @Override
    public void showChangePhase(GameState gameState) {
        if (gameState == (GameState.PLANNING_PHASE) && actualGamePhase == (GameState.SETUP)) {
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
        String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\', '/');
        ImageView imageView = (ImageView) (((MainScene2PlayersController) mainLoader.getController()).getMySchoolBoardPane().getChildren().get(3));
        imageView.setImage(new Image("file:" + currentPath + assistantCard.getType().getImagePath()));
    }

    @Override
    public void askTowerColor(boolean[] towerColorChosen) {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/towerColor-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((TowerColorSceneController) loader.getController()).addObserver(client);
                ((TowerColorSceneController) loader.getController()).setUsername(clientModel.getUsername());
                Map<String, TowerColor> towerColorMap = new HashMap<>();
                for (int i = 0; i < towerColorChosen.length; i++) {
                    if (!towerColorChosen[i]) {
                        towerColorMap.put(TowerColor.values()[i].name(), TowerColor.values()[i]);
                    }
                }
                ((TowerColorSceneController) loader.getController()).setTowerColorMap(towerColorMap);
                ((TowerColorSceneController) loader.getController()).getTowerColorChoice().getItems().addAll(towerColorMap.keySet());
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
                ((GameModeAndNumOfPlayersSceneController) loader.getController()).addObserver(client);
                ((GameModeAndNumOfPlayersSceneController) loader.getController()).setUsername(clientModel.getUsername());
                Scene scene = new Scene(root);
                activeStage.setScene(scene);
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
                ((WizardSceneController) loader.getController()).addObserver(client);
                ((WizardSceneController) loader.getController()).setUsername(clientModel.getUsername());
                Map<Image, Wizard> wizardsImages = new HashMap<>();
                String currentPath = new java.io.File("src/main/resources/Images/Wizards").getAbsolutePath().replace('\\', '/');
                for (Wizard wizard : wizards) {
                    wizardsImages.put(new Image("file:" + currentPath + wizard.getImagePath()), wizard);

                }
                ((WizardSceneController) loader.getController()).setWizards(wizardsImages);
                ((WizardSceneController) loader.getController()).getWizardImageView().setImage((Image) wizardsImages.keySet().toArray()[0]);
                ((WizardSceneController) loader.getController()).getLeftSwitch().setFill(javafx.scene.paint.Color.GRAY);
                ((WizardSceneController) loader.getController()).getLeftSwitch().setOpacity(0.5);
                if (wizards.size() == 1) {
                    ((WizardSceneController) loader.getController()).getRightSwitch().setFill(javafx.scene.paint.Color.GRAY);
                    ((WizardSceneController) loader.getController()).getRightSwitch().setOpacity(0.5);
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
                ((AssistantCardSceneController) loader.getController()).addObserver(client);
                ((AssistantCardSceneController) loader.getController()).setUsername(clientModel.getUsername());
                Map<Image, AssistantCard> assistantCardImages = new HashMap<>();
                String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\', '/');
                for (AssistantCard assistantCard : clientModel.getAssistantDeck().getAssistantDeck()) {
                    assistantCardImages.put(new Image("file:" + currentPath + assistantCard.getType().getImagePath()), assistantCard);
                }
                ((AssistantCardSceneController) loader.getController()).setAssistantCards(assistantCardImages);
                ((AssistantCardSceneController) loader.getController()).getAssistantCardImageView().setImage((Image) assistantCardImages.keySet().toArray()[0]);
                ((AssistantCardSceneController) loader.getController()).getLeftSwitch().setFill(javafx.scene.paint.Color.GRAY);
                ((AssistantCardSceneController) loader.getController()).getLeftSwitch().setOpacity(0.5);
                if (assistantCardImages.size() == 1) {
                    ((WizardSceneController) loader.getController()).getRightSwitch().setFill(javafx.scene.paint.Color.GRAY);
                    ((WizardSceneController) loader.getController()).getRightSwitch().setOpacity(0.5);
                }
                Scene scene = new Scene(root);
                activeStage.setScene(scene);
                activeStage.show();
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
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/moveStudentFromEntrance-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((MoveStudentFromEntranceSceneController) loader.getController()).addObserver(client);
                ((MoveStudentFromEntranceSceneController) loader.getController()).setUsername(clientModel.getUsername());
                Map<String, Color> colorMap = new HashMap<>();
                ArrayList<Integer> islandsId = new ArrayList<>();
                String[] whereMove = {"dining room", "island"};
                for (Island island: clientModel.getIslands()) {
                    islandsId.add(island.getId());
                }
                for (int i = 0; i < clientModel.getMySchoolBoard().getEntrance().length; i++) {
                    if (clientModel.getMySchoolBoard().getEntrance()[i] != 0) {
                        colorMap.put(Color.values()[i].name(), Color.values()[i]);
                    }
                }
                ((MoveStudentFromEntranceSceneController) loader.getController()).setColorMap(colorMap);
                ((MoveStudentFromEntranceSceneController) loader.getController()).getColorChoiceBox().getItems().addAll(colorMap.keySet());
                ((MoveStudentFromEntranceSceneController) loader.getController()).getIslandIdChoiceBox().getItems().addAll(islandsId);
                ((MoveStudentFromEntranceSceneController) loader.getController()).getWhereChoiceBox().getItems().addAll(whereMove);
                ((MoveStudentFromEntranceSceneController) loader.getController()).getWhereChoiceBox().setOnAction(((MoveStudentFromEntranceSceneController) loader.getController())::show);

                Scene scene = new Scene(root);
                activeStage.setScene(scene);
                activeStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showInfoStudentOntoIsland(){
        Set<String> usernames = clientModel.getSchoolBoards().keySet();
        setStudentsOnDiningRoom(((MainScene2PlayersController)mainLoader.getController()).getMyDiningRoomGrid().getChildren(), clientModel.getMySchoolBoard());
        setStudentsOnEntrance(((MainScene2PlayersController)mainLoader.getController()).getMyEntranceGrid().getChildren(), clientModel.getMySchoolBoard());
        for(String username: usernames) {
            if(!username.equals(clientModel.getUsername())) {
                setStudentsOnDiningRoom(((MainScene2PlayersController) mainLoader.getController()).getOtherDiningRoomGrid().getChildren(), clientModel.getSchoolBoards().get(username));
                setStudentsOnEntrance(((MainScene2PlayersController) mainLoader.getController()).getOtherEntranceGrid().getChildren(), clientModel.getSchoolBoards().get(username));
            }
        }
    }

    @Override
    public void askNumberStepsMotherNature() {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/motherNatureSteps-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((MotherNatureStepsSceneController) loader.getController()).addObserver(client);
                ((MotherNatureStepsSceneController) loader.getController()).setUsername(clientModel.getUsername());
                ArrayList<Integer> steps = new ArrayList<>();
                for (int i = 0; i <= clientModel.getLastAssistantCardPlayed().getType().getMaxSteps(); i++) {
                    steps.add(i);
                }
                ((MotherNatureStepsSceneController) loader.getController()).getStepsChoiceBox().getItems().addAll(steps);
                Scene scene = new Scene(root);
                activeStage.setScene(scene);
                activeStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
            mainLoader = new FXMLLoader(getClass().getResource("/main-scene.fxml"));
            Parent root = null;
            try {
                root = mainLoader.load();
                Scene scene = new Scene(root);
                mainStage = new Stage();
                mainStage.setResizable(false);
                //mainStage.setFullScreen(true);
                Set<String> usernames = clientModel.getSchoolBoards().keySet();
                setStudentsOnDiningRoom(((MainScene2PlayersController)mainLoader.getController()).getMyDiningRoomGrid().getChildren(), clientModel.getMySchoolBoard());
                setStudentsOnEntrance(((MainScene2PlayersController)mainLoader.getController()).getMyEntranceGrid().getChildren(), clientModel.getMySchoolBoard());
                setTowers(((MainScene2PlayersController)mainLoader.getController()).getMyTowerGrid().getChildren(), clientModel.getMySchoolBoard());
                for(String username: usernames) {
                    if(!username.equals(clientModel.getUsername())) {
                        setStudentsOnDiningRoom(((MainScene2PlayersController) mainLoader.getController()).getOtherDiningRoomGrid().getChildren(), clientModel.getSchoolBoards().get(username));
                        setStudentsOnEntrance(((MainScene2PlayersController) mainLoader.getController()).getOtherEntranceGrid().getChildren(), clientModel.getSchoolBoards().get(username));
                        setTowers(((MainScene2PlayersController)mainLoader.getController()).getOtherTowerGrid().getChildren(), clientModel.getSchoolBoards().get(username));
                    }
                }
                drawIslands();
                String currentPath = new java.io.File( "src/main/resources/Images/Wizards").getAbsolutePath().replace('\\','/');
                ImageView imageView = (ImageView)(((MainScene2PlayersController)mainLoader.getController()).getMySchoolBoardPane().getChildren().get(2));
                imageView.setImage(new Image("file:" + currentPath + clientModel.getAssistantDeck().getWizard().getImagePath()));
                imageView = (ImageView) (((MainScene2PlayersController) mainLoader.getController()).getOtherSchoolBoardPane().getChildren().get(1));
                imageView.setImage(new Image("file:" + currentPath + getOtherPlayerWizard().getImagePath()));
                mainStage.setScene(scene);
                mainStage.show();
                activeStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public Wizard getOtherPlayerWizard() {
        Set<String> usernames = clientModel.getChosenWizardsPerUsername().keySet();
        for (String username : usernames) {
            if (!username.equals(clientModel.getUsername()))
                return clientModel.getChosenWizardsPerUsername().get(username);
        }
        throw new NoSuchElementException();
    }

    private void setStudentsOnDiningRoom(List<Node> studentsOnDiningRoom, SchoolBoard schoolBoard){
        int offset = 0;
        for(int i = 0; i < schoolBoard.getDiningRoom().length; i++){
            for(int j = 0; j<schoolBoard.getDiningRoom()[i]; j++) {
                ((ImageView)(studentsOnDiningRoom.get(offset+j))).setVisible(true);
            }
            offset+=10;
        }
    }

    private void setStudentsOnEntrance(List<Node> studentsOnEntrance, SchoolBoard schoolBoard){
        int offset = 0;
        String currentPath = new java.io.File("src/main/resources/Images/Students").getAbsolutePath().replace('\\','/');
        for(int i = 0; i < schoolBoard.getEntrance().length; i++){
            for(int j = 0; j<schoolBoard.getEntrance()[i]; j++) {
                ((ImageView)(studentsOnEntrance.get(offset))).setImage(new Image("file:" + currentPath + Color.values()[i].getStudentImagePath()));
                ((ImageView)(studentsOnEntrance.get(offset))).setVisible(true);
                offset++;
            }
        }
    }

    private void setTowers(List<Node> towersOnSchoolBoard, SchoolBoard schoolBoard){
        for(int i = 0; i<schoolBoard.getNumTowers(); i++){
            if(schoolBoard.getTowerColor().equals(TowerColor.BLACK)) {
                ((Shape) towersOnSchoolBoard.get(i)).setFill(javafx.scene.paint.Color.BLACK);
                ((Shape)towersOnSchoolBoard.get(i)).setVisible(true);
            }
            else {
                ((Shape) towersOnSchoolBoard.get(i)).setFill(javafx.scene.paint.Color.WHITE);
                ((Shape)towersOnSchoolBoard.get(i)).setVisible(true);
            }
        }
    }

    private void drawIslands(){
        String currentPath = new java.io.File("src/main/resources/Images/Islands").getAbsolutePath().replace('\\','/');
        List<Node> islands = ((MainScene2PlayersController)mainLoader.getController()).getIslandPane().getChildren();
        islands.clear();
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
            AnchorPane anchorPane = new AnchorPane();
            HBox box = new HBox();
            anchorPane.getChildren().add(box);
            for(int j = 0; j < clientModel.getIslands().get(i).getDim(); j++){
                Image image = new Image("file:" + currentPath + "/Island1.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                box.getChildren().add(imageView);
                if(clientModel.getIslands().get(i).getDominator() != null){
                    Circle tower = new Circle(13);
                    anchorPane.getChildren().add(tower);
                    tower.setLayoutX(box.getChildren().get(0).getLayoutX() + 60);
                    tower.setLayoutY(box.getChildren().get(0).getLayoutY() + 60);
                    if(clientModel.getSchoolBoards().get(clientModel.getIslands().get(i).getDominator().getUsername()).getTowerColor() == TowerColor.BLACK)
                        tower.setFill(javafx.scene.paint.Color.BLACK);
                    else
                        tower.setFill(javafx.scene.paint.Color.WHITE);
                }
            }
            if(i < dim1){
                superiorList.add(anchorPane);
                hanldeMouseEntranceAndExit(box, anchorPane, islands, i, anchorPane.getLayoutY() + 100);
            }
            else {
                inferiorList.add(anchorPane);
                hanldeMouseEntranceAndExit(box, anchorPane, islands, i, anchorPane.getLayoutY() + 300);
            }
            if(clientModel.getIslands().get(i).equals(clientModel.getMotherNaturePosition())) {
                Circle motherNature = new Circle(11);
                motherNature.setFill(javafx.scene.paint.Color.ORANGE);
                anchorPane.getChildren().add(motherNature);
                motherNature.setLayoutX(box.getChildren().get(0).getLayoutX() + 30);
                motherNature.setLayoutY(box.getChildren().get(0).getLayoutY() + 30);
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
    }

    private void hanldeMouseEntranceAndExit(HBox box, AnchorPane anchorPane, List<Node>islands, int i, double y) {
        AnchorPane infoPane = new AnchorPane();
        islands.add(infoPane);
        Label islandLabel = new Label("Number of island:" + clientModel.getIslands().get(i).getId());
        islandLabel.setLayoutX(5);
        infoPane.getChildren().add(islandLabel);

        Label greenLabel = new Label("Number of red students:" + clientModel.getIslands().get(i).getStudents()[Color.GREEN.ordinal()]);
        infoPane.getChildren().add(greenLabel);
        greenLabel.setLayoutX(5);
        greenLabel.setLayoutY(16);
        greenLabel.setStyle("-fx-text-fill: green;");

        Label redLabel = new Label("Number of red students:" + clientModel.getIslands().get(i).getStudents()[Color.RED.ordinal()]);
        infoPane.getChildren().add(redLabel);
        redLabel.setLayoutX(5);
        redLabel.setLayoutY(32);
        redLabel.setStyle("-fx-text-fill: red;");

        Label yellowLabel = new Label("Number of red students:" + clientModel.getIslands().get(i).getStudents()[Color.YELLOW.ordinal()]);
        infoPane.getChildren().add(yellowLabel);
        yellowLabel.setLayoutX(5);
        yellowLabel.setLayoutY(48);
        yellowLabel.setStyle("-fx-text-fill: yellow;");

        Label pinkLabel = new Label("Number of pink students:" + clientModel.getIslands().get(i).getStudents()[Color.PINK.ordinal()]);
        infoPane.getChildren().add(pinkLabel);
        pinkLabel.setLayoutX(5);
        pinkLabel.setLayoutY(64);
        pinkLabel.setStyle("-fx-text-fill: #FF34B3;");

        Label blueLabel = new Label("Number of blue students:" + clientModel.getIslands().get(i).getStudents()[Color.BLUE.ordinal()]);
        infoPane.getChildren().add(blueLabel);
        blueLabel.setLayoutX(5);
        blueLabel.setLayoutY(80);
        blueLabel.setStyle("-fx-text-fill: blue;");

        infoPane.setPrefHeight(95);
        infoPane.setPrefWidth(160);
        infoPane.setVisible(false);
        infoPane.setOpacity(1);
        infoPane.setStyle("-fx-background-color: #C7C7C7; -fx-border-color: black;");
        box.setOnMouseEntered(event -> {
            infoPane.setLayoutX(anchorPane.getLayoutX() + 40);
            infoPane.setLayoutY(y);
            infoPane.setVisible(true);
            event.consume();
        });
        box.setOnMouseExited(event -> {
            infoPane.setVisible(false);
            event.consume();
        });
    }

}