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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import java.util.stream.Collectors;

public class Gui extends Observable<Message> implements ClientView {
    private ClientModel clientModel;
    private Client client;
    private Stage mainStage;
    private final Stage activeStage;
    private FXMLLoader mainLoader;
    private GameState actualGamePhase = GameState.SETUP;
    private Map<String, AnchorPane> schoolBoardsMap = new HashMap<>();
    private Map<Integer, AnchorPane> cloudTilesMap = new HashMap<>();
    private Map<BorderPane, Integer> characterCardMap = new HashMap<>();

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
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText(genericMessage);
            alert.showAndWait();
        });
    }

    @Override
    public void showLobbyMessage(ArrayList<String> onlineNicknames) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New player online");
            alert.setHeaderText(null);
            String usernames = "";
            for(String name: onlineNicknames){
                usernames += name + "\n";
            }
            alert.setContentText("A new player has been connected.\nNow players online are:\n" + usernames);
            alert.showAndWait();
        });
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
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game phase info");
            alert.setHeaderText(null);
            alert.setContentText("New game phase:\n" + gameState + " is beginning..");
            alert.showAndWait();
        });
    }

    @Override
    public void showDisconnectionMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Disconnection");
            alert.setHeaderText(null);
            alert.setContentText("A player has been disconnected. The game is over!");
            alert.showAndWait();
            mainStage.close();
            activeStage.close();
        });
    }


    @Override
    public void showEmptyBagMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty bag");
            alert.setHeaderText(null);
            alert.setContentText("The bag in empty! Game will finish at the end of this turn");
            alert.showAndWait();
        });
    }

    @Override
    public void showMyInfoAssistantCardPlayed(AssistantCard assistantCard) {
        String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\', '/');
        ImageView imageView = (ImageView) (((MainScene2PlayersController) mainLoader.getController()).getMySchoolBoardPane().getChildren().get(3));
        imageView.setImage(new Image("file:" + currentPath + assistantCard.getType().getImagePath()));
    }

    @Override
    public void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard) {
        String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\', '/');
        ImageView imageView = (ImageView) (((MainScene2PlayersController) mainLoader.getController()).getOtherSchoolBoardPane().getChildren().get(2));
        imageView.setImage(new Image("file:" + currentPath + getOtherPlayerLastAssistantCard().getType().getImagePath()));
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
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chooseCloudTile-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((ChooseCloudTileSceneController) loader.getController()).addObserver(client);
                ((ChooseCloudTileSceneController) loader.getController()).setUsername(clientModel.getUsername());
                String currentPath = new java.io.File("src/main/resources/Images/Students").getAbsolutePath().replace('\\', '/');
                cloudTilesMap.clear();
                List<Node> cloudTilesPanes = ((ChooseCloudTileSceneController) loader.getController()).getChooseCloudTilePane().getChildren().stream().filter(x->x instanceof AnchorPane).collect(Collectors.toList());
                for(int i = 0; i<3; i++){
                    if(clientModel.getCloudTileById(i) != null)
                        cloudTilesMap.put(i, ((AnchorPane)cloudTilesPanes.get(i)));
                }
                for(int i = 0; i<3; i++){
                    if(clientModel.getCloudTileById(i) != null){
                        AnchorPane studentsPane = (AnchorPane) (cloudTilesMap.get(i).getChildren().stream().filter(x->x instanceof AnchorPane).findFirst().get());
                        List<Node> imagesOnCloudTile = studentsPane.getChildren();
                        List<Color> studentsOnCloudTile = new ArrayList<>();
                        for(int j = 0; j<clientModel.getCloudTileById(i).getStudents().length; j++){
                            for(int k = 0; k<clientModel.getCloudTileById(i).getStudents()[j]; k++){
                                studentsOnCloudTile.add(Color.values()[j]);
                            }
                        }
                        for(int j = 0; j<imagesOnCloudTile.size(); j++){
                            ((ImageView)imagesOnCloudTile.get(j)).setImage(new Image("file:" + currentPath + studentsOnCloudTile.get(j).getStudentImagePath()));
                        }
                        cloudTilesMap.get(i).setVisible(true);
                    }
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
    public void showLoginReply() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Reply");
            alert.setHeaderText(null);
            alert.setContentText("Username Accepted. Your username will be \"" + clientModel.getUsername() + "\"");
            alert.showAndWait();
        });
    }

    @Override
    public void showInfoStudentIntoDiningRoom(String username, String moveDescription) {
        Platform.runLater(() -> {
            AnchorPane schoolBoardPane = schoolBoardsMap.get(username);
            setStudentsOnDiningRoom(((GridPane)schoolBoardPane.getChildren().get(1)).getChildren(), clientModel.getSchoolBoards().get(username));
            setStudentsOnEntrance(((GridPane)schoolBoardPane.getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(username));
            for(int i = 0; i < schoolBoardsMap.keySet().size(); i++){
                schoolBoardPane = schoolBoardsMap.get(schoolBoardsMap.keySet().stream().collect(Collectors.toList()).get(i));
                setProfessors(((GridPane)schoolBoardPane.getChildren().get(3)).getChildren(), schoolBoardsMap.keySet().stream().collect(Collectors.toList()).get(i));
            }
        });
    }

    @Override
    public void showInfoStudentOntoIsland(String username, String moveDescription) {
        Platform.runLater(()->{
            AnchorPane schoolBoardPane = schoolBoardsMap.get(username);
            setStudentsOnEntrance(((GridPane)schoolBoardPane.getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(username));
        });
    }

    @Override
    public void showMotherNaturePosition(int islandId) {
        drawIslands();
    }

    @Override
    public void showChangeInfluenceMessage(String username, int islandId) {
        Platform.runLater(() -> {
            drawIslands();
            for(int i = 0; i < schoolBoardsMap.keySet().size(); i++){
                AnchorPane schoolBoardPane = schoolBoardsMap.get(schoolBoardsMap.keySet().stream().collect(Collectors.toList()).get(i));
                setTowers(((GridPane)schoolBoardPane.getChildren().get(4)).getChildren(), clientModel.getSchoolBoards().get(schoolBoardsMap.keySet().stream().collect(Collectors.toList()).get(i)));
            }
        });
    }

    @Override
    public void showMergeIslandsMessage(int island1Id, int island2Id) {
        Platform.runLater(() -> {
            drawIslands();
        });
    }

    @Override
    public void showMoveTowerOntoIsland(int islandId) {
        Platform.runLater(() -> {
            drawIslands();
        });
    }

    @Override
    public void showMoveTowerOntoSchoolBoard(String username, SchoolBoard schoolBoard) {
        Platform.runLater(() -> {
            for(int i = 0; i < schoolBoardsMap.keySet().size(); i++){
                AnchorPane schoolBoardPane = schoolBoardsMap.get(schoolBoardsMap.keySet().stream().collect(Collectors.toList()).get(i));
                setTowers(((GridPane)schoolBoardPane.getChildren().get(4)).getChildren(), clientModel.getSchoolBoards().get(schoolBoardsMap.keySet().stream().collect(Collectors.toList()).get(i)));
            }
        });
    }
    @Override
    public void showInfoChosenCloudTile(String username, String choiceDescription) {
        Platform.runLater(()->{
            AnchorPane schoolBoardPane = schoolBoardsMap.get(username);
            setStudentsOnEntrance(((GridPane)schoolBoardPane.getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(username));
        });
    }

    @Override
    public void showUpdateWallet() {

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
                for (int i = 0; i <= clientModel.getMyLastAssistantCardPlayed().getType().getMaxSteps(); i++) {
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
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect input");
            alert.setHeaderText(null);
            alert.setContentText(fullLobby.getDescription());
            alert.showAndWait();
        });
    }


    @Override
    public void showTieMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tie");
            alert.setHeaderText(null);
            alert.setContentText("Nobody won the game: it was a tie");
            alert.showAndWait();
            mainStage.close();
            activeStage.close();
        });
    }


    @Override
    public void showAbortMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Disconnection");
            alert.setHeaderText(null);
            alert.setContentText("Server error! You will be disconnected!");
            alert.showAndWait();
            mainStage.close();
            activeStage.close();
        });
    }

    @Override
    public void showWinMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You won");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations!! You won the game");
            alert.showAndWait();
            mainStage.close();
            activeStage.close();
        });
    }

    @Override
    public void showLoseMessage(String winnerUsername) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You lost");
            alert.setHeaderText(null);
            alert.setContentText("You lost!\n" + winnerUsername + " won!");
            alert.showAndWait();
            mainStage.close();
            activeStage.close();
        });
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
                createSchoolBoardsMap();
                for(int i = 0; i<schoolBoardsMap.size(); i++){
                    String username = schoolBoardsMap.keySet().stream().collect(Collectors.toList()).get(i);
                    setStudentsOnDiningRoom(((GridPane)schoolBoardsMap.get(username).getChildren().get(1)).getChildren(), clientModel.getSchoolBoards().get(username));
                    setStudentsOnEntrance(((GridPane)schoolBoardsMap.get(username).getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(username));
                    setTowers(((GridPane)schoolBoardsMap.get(username).getChildren().get(4)).getChildren(), clientModel.getSchoolBoards().get(username));
                }
                drawIslands();
                String currentPath = new java.io.File( "src/main/resources/Images/Wizards").getAbsolutePath().replace('\\','/');
                ImageView imageView = (ImageView)(((MainScene2PlayersController)mainLoader.getController()).getMySchoolBoardPane().getChildren().get(2));
                imageView.setImage(new Image("file:" + currentPath + clientModel.getAssistantDeck().getWizard().getImagePath()));
                imageView = (ImageView) (((MainScene2PlayersController) mainLoader.getController()).getOtherSchoolBoardPane().getChildren().get(1));
                imageView.setImage(new Image("file:" + currentPath + getOtherPlayerWizard().getImagePath()));
                if(clientModel.isExpertMode())
                    handleCharacterCardRequest(((MainScene2PlayersController)mainLoader.getController()).getPlayCharacterCardButton());
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

    public AssistantCard getOtherPlayerLastAssistantCard(){
        Set<String> usernames = clientModel.getLastAssistantCardPlayedPerUsername().keySet();
        for (String username : usernames) {
            if (!username.equals(clientModel.getUsername()))
                return clientModel.getLastAssistantCardPlayedPerUsername().get(username);
        }
        throw new NoSuchElementException();
    }

    private void setStudentsOnDiningRoom(List<Node> studentsOnDiningRoom, SchoolBoard schoolBoard){
        studentsOnDiningRoom.forEach(x -> x.setVisible(false));
        for(int i = 0; i < schoolBoard.getDiningRoom().length; i++){
            for(int j = 0; j<schoolBoard.getDiningRoom()[i]; j++) {
                ((ImageView)(studentsOnDiningRoom.get(10*i + j))).setVisible(true);
            }
        }
    }

    private void setStudentsOnEntrance(List<Node> studentsOnEntrance, SchoolBoard schoolBoard){
        studentsOnEntrance.forEach(x -> x.setVisible(false));
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
        towersOnSchoolBoard.forEach(x -> x.setVisible(false));
        for(int i = 0; i<schoolBoard.getNumTowers(); i++){
            if(schoolBoard.getTowerColor().equals(TowerColor.BLACK)) {
                ((Shape) towersOnSchoolBoard.get(i)).setFill(javafx.scene.paint.Color.BLACK);
                ((Shape)towersOnSchoolBoard.get(i)).setVisible(true);
            }
            else if (schoolBoard.getTowerColor().equals(TowerColor.WHITE)){
                ((Shape) towersOnSchoolBoard.get(i)).setFill(javafx.scene.paint.Color.WHITE);
                ((Shape)towersOnSchoolBoard.get(i)).setVisible(true);
            }
            else{
                ((Shape) towersOnSchoolBoard.get(i)).setFill(javafx.scene.paint.Color.GRAY);
                ((Shape)towersOnSchoolBoard.get(i)).setVisible(true);
            }
        }
    }
    private void setProfessors(List<Node> professorsOnSchoolBoard, String username){
        for(int i=0; i < clientModel.getProfessors().length; i++){
            if(clientModel.getProfessors()[i] != null) {
                if (clientModel.getProfessors()[i].equals(username))
                    professorsOnSchoolBoard.get(i).setVisible(true);
                else professorsOnSchoolBoard.get(i).setVisible(false);
            }
        }
    }


    private void drawIslands(){
        Platform.runLater(() -> {
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
                        tower.setLayoutX(box.getChildren().get(0).getLayoutX() + 60 + 100*(j));
                        tower.setLayoutY(box.getChildren().get(0).getLayoutY() + 60);
                        if(clientModel.getSchoolBoards().get(clientModel.getIslands().get(i).getDominator().getUsername()).getTowerColor() == TowerColor.BLACK)
                            tower.setFill(javafx.scene.paint.Color.BLACK);
                        else
                            tower.setFill(javafx.scene.paint.Color.WHITE);
                    }
                }
                inferiorGrid.setPrefTileWidth(100*clientModel.getIslands().get(i).getDim());
                if(i < dim1){
                    superiorList.add(anchorPane);
                    hanldeMouseEntranceAndExit(box, anchorPane, islands, i, anchorPane.getLayoutY() + 100);
                }
                else {
                    inferiorList.add(anchorPane);
                    hanldeMouseEntranceAndExit(box, anchorPane, islands, i, anchorPane.getLayoutY() + 300);
                }
                if(clientModel.getIslands().get(i).getId() == clientModel.getMotherNaturePosition().getId()) {
                    Circle motherNature = new Circle(11);
                    motherNature.setFill(javafx.scene.paint.Color.ORANGE);
                    anchorPane.getChildren().add(motherNature);
                    motherNature.setLayoutX(box.getChildren().get(0).getLayoutX() + 30);
                    motherNature.setLayoutY(box.getChildren().get(0).getLayoutY() + 30);
                }
            }
            superiorGrid.setPrefTileWidth(100*6/(double)dim1);
            inferiorGrid.setPrefTileWidth(100*6/(double)dim2);
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
        });
    }

    private void hanldeMouseEntranceAndExit(HBox box, AnchorPane anchorPane, List<Node>islands, int i, double y) {
        AnchorPane infoPane = new AnchorPane();
        islands.add(infoPane);
        box.setOnMouseEntered(event -> {
            Label islandLabel = new Label("Number of island:" + clientModel.getIslands().get(i).getId());
            islandLabel.setLayoutX(5);
            infoPane.getChildren().add(islandLabel);

            infoPane.setPrefHeight(105);
            infoPane.setPrefWidth(160);
            infoPane.setVisible(false);
            infoPane.setOpacity(1);
            infoPane.setStyle("-fx-background-color: #C7C7C7; -fx-border-color: black;");

            Label greenLabel = new Label("Number of green students:" + clientModel.getIslands().get(i).getStudents()[Color.GREEN.ordinal()]);
            infoPane.getChildren().add(greenLabel);
            greenLabel.setLayoutX(5);
            greenLabel.setLayoutY(15);
            greenLabel.setStyle("-fx-text-fill: green;");

            Label redLabel = new Label("Number of red students:" + clientModel.getIslands().get(i).getStudents()[Color.RED.ordinal()]);
            infoPane.getChildren().add(redLabel);
            redLabel.setLayoutX(5);
            redLabel.setLayoutY(30);
            redLabel.setStyle("-fx-text-fill: red;");

            Label yellowLabel = new Label("Number of yellow students:" + clientModel.getIslands().get(i).getStudents()[Color.YELLOW.ordinal()]);
            infoPane.getChildren().add(yellowLabel);
            yellowLabel.setLayoutX(5);
            yellowLabel.setLayoutY(45);
            yellowLabel.setStyle("-fx-text-fill: yellow;");

            Label pinkLabel = new Label("Number of pink students:" + clientModel.getIslands().get(i).getStudents()[Color.PINK.ordinal()]);
            infoPane.getChildren().add(pinkLabel);
            pinkLabel.setLayoutX(5);
            pinkLabel.setLayoutY(60);
            pinkLabel.setStyle("-fx-text-fill: #FF34B3;");

            Label blueLabel = new Label("Number of blue students:" + clientModel.getIslands().get(i).getStudents()[Color.BLUE.ordinal()]);
            infoPane.getChildren().add(blueLabel);
            blueLabel.setLayoutX(5);
            blueLabel.setLayoutY(75);
            blueLabel.setStyle("-fx-text-fill: blue;");

            Label noEntryTilesLabel = new Label("Number of no entry tiles:" + clientModel.getIslands().get(i).getNoEntryTiles());
            infoPane.getChildren().add(noEntryTilesLabel);
            noEntryTilesLabel.setLayoutX(5);
            noEntryTilesLabel.setLayoutY(90);
            infoPane.setLayoutX(anchorPane.getLayoutX() + 40);
            infoPane.setLayoutY(y);
            infoPane.setVisible(true);
            event.consume();
        });
        box.setOnMouseExited(event -> {
            infoPane.getChildren().clear();
            infoPane.setVisible(false);
            event.consume();
        });
    }

    private void createSchoolBoardsMap(){
        int cont = 0;
        List<Node> otherPlayersPane = ((MainScene2PlayersController)mainLoader.getController()).getOtherSchoolBoardPane().getChildren().stream().filter(x -> x instanceof AnchorPane).collect(Collectors.toList());
        for(int i=0; i<clientModel.getSchoolBoards().size(); i++){
            if(clientModel.getUsername().equals(clientModel.getSchoolBoards().keySet().stream().collect(Collectors.toList()).get(i)))
                schoolBoardsMap.put(clientModel.getUsername(), ((MainScene2PlayersController)mainLoader.getController()).getMySchoolBoard());
            else{
                schoolBoardsMap.put(clientModel.getSchoolBoards().keySet().stream().collect(Collectors.toList()).get(i), (AnchorPane) otherPlayersPane.get(cont));
                cont++;
            }
        }
    }

    private void handleCharacterCardRequest(Button button){
        button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/characterCard-scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                ((CharacterCardSceneController) loader.getController()).addObserver(client);
                ((CharacterCardSceneController) loader.getController()).setUsername(clientModel.getUsername());
                String currentPath = new java.io.File("src/main/resources/Images/CharacterCards").getAbsolutePath().replace('\\', '/');
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j<12; j++){
                        if(clientModel.getAvailableCharacterCards().get(i).getId() == j){
                            ((ImageView)((BorderPane)((CharacterCardSceneController) loader.getController()).getCharacterCardPane().getChildren().get(i)).getCenter()).setImage(new Image("file:" + currentPath + "/CharacterCard" + j + ".jpg"));
                            characterCardMap.put(((BorderPane)((CharacterCardSceneController) loader.getController()).getCharacterCardPane().getChildren().get(i)), j);
                        }
                    }
                }
                Scene scene = new Scene(root);
                activeStage.setScene(scene);
                activeStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}