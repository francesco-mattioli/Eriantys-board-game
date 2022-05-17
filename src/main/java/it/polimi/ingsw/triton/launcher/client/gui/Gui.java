package it.polimi.ingsw.triton.launcher.client.gui;

import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.client.gui.scenes.*;
import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.server.controller.Controller;
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
    private Map<String,GridPane> deckMap = new HashMap<>();
    private Map<BorderPane, Integer> characterCardMap = new HashMap<>();


    public Gui(Stage activeStage) {
        this.activeStage = activeStage;
    }

    @Override
    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        showAlert(Alert.AlertType.INFORMATION, "Information Message", genericMessage) ;
    }

    @Override
    public void showLobbyMessage(List<String> onlineNicknames) {
        String usernames = "";
        for(String name: onlineNicknames){
            usernames += name + "\n";
        }
        usernames = "A new player has been connected.\nNow players online are:\n" + usernames;
        showAlert(Alert.AlertType.INFORMATION, "New player online", usernames);
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
        showAlert(Alert.AlertType.INFORMATION, "Game phase info", "New game phase:\n" + gameState + " is beginning..");
    }

    @Override
    public void showDisconnectionMessage() {
        showAlert(Alert.AlertType.ERROR, "Disconnection" , "A player has been disconnected. The game is over!");
        closeGui();
    }


    @Override
    public void showEmptyBagMessage() {
        showAlert(Alert.AlertType.WARNING, "Empty bag", "The bag in empty! Game will finish at the end of this turn");
    }

    @Override
    public void showLoginReply() {
        showAlert(Alert.AlertType.INFORMATION, "Login Reply", "Username Accepted. Your username will be \"" + clientModel.getUsername() + "\"");
    }

    @Override
    public void showErrorMessage(ErrorTypeID fullLobby) {
        showAlert(Alert.AlertType.WARNING, "Incorrect input", fullLobby.getDescription());
    }


    @Override
    public void showTieMessage() {
        showAlert(Alert.AlertType.INFORMATION, "Tie", "Nobody won the game: it was a tie");
        closeGui();
    }


    @Override
    public void showAbortMessage() {
        showAlert(Alert.AlertType.ERROR, "Disconnection", "Server error! You will be disconnected!");
        closeGui();
    }

    @Override
    public void showWinMessage() {
        showAlert(Alert.AlertType.INFORMATION, "You won", "Congratulations!! You won the game");
        closeGui();
    }

    @Override
    public void showLoseMessage(String winnerUsername) {
        showAlert(Alert.AlertType.INFORMATION, "You lost", "You lost!\n" + winnerUsername + " won!");
        closeGui();
    }

    private void showAlert(Alert.AlertType alertType, String title, String contentText){
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(contentText);
            alert.showAndWait();
        });
    }

    private void closeGui(){
        Platform.runLater(() -> {
            activeStage.close();
            mainStage.close();
        });
    }

    public void startGui(){
        client = new Client(this);
        this.addObserver(client);
        this.clientModel = new ClientModel();
        askIpAddress();
    }

    private <T> void prepareController(String path, T parameters){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            try {
                Parent root = loader.load();
                SceneController controller = loader.getController();
                controller.addObserver(client);
                controller.setUsername(clientModel.getUsername());
                controller.setupScene(clientModel, parameters);
                activeStage.setScene(new Scene(root));
                activeStage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void askIpAddress() {
        prepareController("/ipAddress-scene.fxml", null);
    }

    @Override
    public void askUsername() {
        prepareController("/login-scene.fxml", null);
    }

    @Override
    public void askTowerColor(boolean[] towerColorChosen) {
        prepareController("/towerColor-scene.fxml", towerColorChosen);
    }

    @Override
    public void askNumPlayersAndGameMode() {
        prepareController("/gameModeAndNumOfPlayers-scene.fxml", null);
    }


    @Override
    public void askWizard(List<Wizard> wizards) {
        prepareController("/wizard-scene.fxml", wizards);
    }

    @Override
    public void askAssistantCard() {
        prepareController("/assistantCard-scene.fxml", null);
    }

    @Override
    public void askCloudTile() {
        prepareController("/chooseCloudTile-scene.fxml", null);
    }

    @Override
    public void askMoveStudentFromEntrance() {
        prepareController("/moveStudentFromEntrance-scene.fxml", null);
    }

    @Override
    public void askNumberStepsMotherNature() {
        int additionalSteps = 0;
        prepareController("/motherNatureSteps-scene.fxml", additionalSteps);
    }


    @Override
    public void askCharacterCardParameters(int id) {

    }


    @Override
    public void showMyInfoAssistantCardPlayed(AssistantCard assistantCard) {
        String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\', '/');
        ImageView imageView = (ImageView) deckMap.get(clientModel.getUsername()).getChildren().get(1);
        imageView.setImage(new Image("file:" + currentPath + assistantCard.getType().getImagePath()));
    }

    @Override
    public void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard) {
        String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\', '/');
        ImageView imageView = (ImageView) deckMap.get(username).getChildren().get(1);
        imageView.setImage(new Image("file:" + currentPath +assistantCard.getType().getImagePath()));
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
                createDeckMap();
                for(int i = 0; i<schoolBoardsMap.size(); i++){
                    String username = schoolBoardsMap.keySet().stream().collect(Collectors.toList()).get(i);
                    setStudentsOnDiningRoom(((GridPane)schoolBoardsMap.get(username).getChildren().get(1)).getChildren(), clientModel.getSchoolBoards().get(username));
                    setStudentsOnEntrance(((GridPane)schoolBoardsMap.get(username).getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(username));
                    setTowers(((GridPane)schoolBoardsMap.get(username).getChildren().get(4)).getChildren(), clientModel.getSchoolBoards().get(username));
                }
                drawIslands();
                String currentPath = new java.io.File( "src/main/resources/Images/Wizards").getAbsolutePath().replace('\\','/');
                ImageView imageView = (ImageView)deckMap.get(clientModel.getUsername()).getChildren().get(0);
                imageView.setImage(new Image("file:" + currentPath + clientModel.getAssistantDeck().getWizard().getImagePath()));
                Set<String> usernames = clientModel.getChosenWizardsPerUsername().keySet();
                for (String username : usernames) {
                    if (!username.equals(clientModel.getUsername())){
                        imageView = (ImageView) deckMap.get(username).getChildren().get(0);
                        imageView.setImage(new Image("file:" + currentPath + clientModel.getChosenWizardsPerUsername().get(username).getImagePath()));
                    }

                }

                if(clientModel.isExpertMode()){
                    handleCharacterCardRequest(((MainScene2PlayersController)mainLoader.getController()).getPlayCharacterCardButton());
                    ((Button)((MainScene2PlayersController)mainLoader.getController()).getPlayCharacterCardButton()).setVisible(true);
                }
                mainStage.setScene(scene);
                mainStage.show();
                activeStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        for(int i = 0; i < clientModel.getSchoolBoards().size(); i++){
            if(clientModel.getUsername().equals(clientModel.getSchoolBoards().keySet().stream().collect(Collectors.toList()).get(i)))
                schoolBoardsMap.put(clientModel.getUsername(), ((MainScene2PlayersController)mainLoader.getController()).getMySchoolBoard());
            else{
                schoolBoardsMap.put(clientModel.getSchoolBoards().keySet().stream().collect(Collectors.toList()).get(i), (AnchorPane) otherPlayersPane.get(cont));
                cont++;
            }
        }
    }

    public void createDeckMap(){
        int cont = 0;
        List<Node> otherPlayersGrid = ((MainScene2PlayersController)mainLoader.getController()).getOtherSchoolBoardPane().getChildren().stream().filter(x -> x instanceof GridPane).collect(Collectors.toList());
        for (int i = 0; i < clientModel.getChosenWizardsPerUsername().size(); i++){
            if (clientModel.getUsername().equals(clientModel.getChosenWizardsPerUsername().keySet().stream().collect(Collectors.toList()).get(i)))
                deckMap.put(clientModel.getUsername(), ((MainScene2PlayersController)mainLoader.getController()).getMyDeckGrid());
            else {
                deckMap.put(clientModel.getChosenWizardsPerUsername().keySet().stream().collect(Collectors.toList()).get(i), (GridPane) otherPlayersGrid.get(cont));
                cont ++;
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