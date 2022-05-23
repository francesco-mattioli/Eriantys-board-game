package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.*;
import java.util.stream.Collectors;

public class MainSceneController extends SceneController {
    private static final String FILE = "file:";
    private final Map<String, AnchorPane> schoolBoardsMap = new HashMap<>();
    private final Map<String, GridPane> deckMap = new HashMap<>();
    @FXML
    AnchorPane mainPane;
    @FXML
    AnchorPane mySchoolBoardPane;
    @FXML
    GridPane moneyPane;
    @FXML
    AnchorPane mySchoolBoard;
    @FXML
    GridPane myDiningRoomGrid;
    @FXML
    GridPane myEntranceGrid;
    @FXML
    GridPane myProfessorsGrid;
    @FXML
    GridPane myTowerGrid;
    @FXML
    AnchorPane otherSchoolBoardPane;
    @FXML
    GridPane otherDiningRoomGrid;
    @FXML
    GridPane otherEntranceGrid;
    @FXML
    GridPane otherProfessorsGrid;
    @FXML
    GridPane otherTowerGrid;
    @FXML
    GridPane myDeckGrid;
    @FXML
    AnchorPane islandPane;

    /**
     * This method draws the students on the correct dining room
     * For each color, the user can have a max of 10 students in his dining room
     * First 10 children are image views which represents green students, then reds and so on
     *
     * @param studentsOnDiningRoom is an array of int, which contains, for each color, the number of students on dining room
     * @param schoolBoard          schoolBoard
     */
    private void drawStudentsOnDiningRoom(List<Node> studentsOnDiningRoom, SchoolBoard schoolBoard) {
        studentsOnDiningRoom.forEach(x -> x.setVisible(false));
        for (int i = 0; i < schoolBoard.getDiningRoom().length; i++) {
            for (int j = 0; j < schoolBoard.getDiningRoom()[i]; j++) {
                (studentsOnDiningRoom.get(10 * i + j)).setVisible(true);
            }
        }
    }

    /**
     * This method draws students on the correct entrance
     * In the enum of Color, for each color we know the path of the associate image, can be taken easily
     *
     * @param studentsOnEntrance studentsOnEntrance
     * @param schoolBoard        schoolBoard
     */
    private void drawStudentsOnEntrance(List<Node> studentsOnEntrance, SchoolBoard schoolBoard) {
        studentsOnEntrance.forEach(x -> x.setVisible(false));
        int offset = 0;
        String currentPath = new java.io.File("src/main/resources/Images/Students").getAbsolutePath().replace('\\', '/');
        for (int i = 0; i < schoolBoard.getEntrance().length; i++) {
            for (int j = 0; j < schoolBoard.getEntrance()[i]; j++) {
                ((ImageView) (studentsOnEntrance.get(offset))).setImage(new Image(FILE + currentPath + Color.values()[i].getStudentImagePath()));
                (studentsOnEntrance.get(offset)).setVisible(true);
                offset++;
            }
        }
    }

    /**
     * This method draws the correct number of towers on the schoolBoard
     * Towers are colorized circles, which are normally not visible and are made visible if are present in schoolBoard
     *
     * @param towersOnSchoolBoard towersOnSchoolBoard
     * @param schoolBoard         schoolBoard
     */
    private void drawTowersOnSchoolBoard(List<Node> towersOnSchoolBoard, SchoolBoard schoolBoard) {
        towersOnSchoolBoard.forEach(x -> x.setVisible(false));
        for (int i = 0; i < schoolBoard.getNumTowers(); i++) {
            Shape towerShape = (Shape) towersOnSchoolBoard.get(i);
            if (schoolBoard.getTowerColor().equals(TowerColor.BLACK)) {
                towerShape.setFill(javafx.scene.paint.Color.BLACK);
            } else if (schoolBoard.getTowerColor().equals(TowerColor.WHITE)) {
                towerShape.setFill(javafx.scene.paint.Color.WHITE);
            } else {
                towerShape.setFill(javafx.scene.paint.Color.GRAY);
            }
            towerShape.setVisible(true);
        }
    }


    /**
     * This method draws professors on correct schoolBoards
     * for each professor is given the username of the possessor, so if username is equals to the possessor of that professor,
     * the associated image view is set visible
     *
     * @param professorsOnSchoolBoard professorsOnSchoolBoard
     * @param username                username
     * @param clientModel             clientModel
     */
    private void drawProfessors(List<Node> professorsOnSchoolBoard, String username, ClientModel clientModel) {
        for (int i = 0; i < clientModel.getProfessors().length; i++) {
            if (clientModel.getProfessors()[i] != null) {
                professorsOnSchoolBoard.get(i).setVisible(clientModel.getProfessors()[i].equals(username));
            }
        }
    }

    /**
     * This method draws the correct number of tower on schoolBoards, for each player
     *
     * @param clientModel clientModel
     */
    private void drawAllSchoolBoardsTowers(ClientModel clientModel) {
        for (int i = 0; i < schoolBoardsMap.keySet().size(); i++) {
            AnchorPane schoolBoardPane = schoolBoardsMap.get(new ArrayList<>(schoolBoardsMap.keySet()).get(i));
            drawTowersOnSchoolBoard(((GridPane) schoolBoardPane.getChildren().get(4)).getChildren(), clientModel.getSchoolBoards().get(new ArrayList<>(schoolBoardsMap.keySet()).get(i)));
        }
    }

    /**
     * This method draws the entire island block
     * It is very complex, so uses a lot of helper methods
     * Are used 2 grid panes, to dispose islands on two lines (superior line and inferior line)
     * islandGrids[0] identifies the superior line
     * islandGrids[1] identifies the inferior line
     * Every grid's box contains and anchor pane, which contains an HBox
     * In every HBox there is a number of ImageViews equals to the number of merged islands in that position
     * The number of islands in superior and inferior line is decided dividing by 2 the islands arrayList dimension:
     * The floor will be the superior grid size
     * The selling will be the inferior grid size
     * When we pass the mouse on and island's HBox, user can see a new pane with island's information
     *
     * @param clientModel clientModel
     */
    private void drawIslands(ClientModel clientModel) {
        List<Node> islands = islandPane.getChildren();
        islands.clear();
        int[] dimensions = new int[2];
        GridPane[] islandGrids = new GridPane[2];
        List<Node>[] nodeList = new List[2];
        setupIslandGrid(clientModel, dimensions, islandGrids, nodeList);
        for (int i = 0; i < clientModel.getIslands().size(); i++) {
            AnchorPane anchorPane = new AnchorPane();
            HBox box = drawSingleIsland(clientModel.getIslands().get(i), anchorPane, clientModel);
            setupNodeList(i, dimensions, nodeList, anchorPane);
            startListenForMouseEvent(i, dimensions, clientModel, box, anchorPane, islands);
            drawMotherNatureOnIsland(clientModel, anchorPane, box, i);
        }
        Collections.reverse(nodeList[1]);
        addIslandsAsChildren(islandGrids, nodeList, islands);
    }

    /**
     * @param clientModel clientModel
     * @param dimensions  array that contains the size of superior and inferior grid
     * @param islandGrids islandGrids
     * @param nodeList    array of lists that will contain, for each list, anchorPanes associated to islands
     */
    private void setupIslandGrid(ClientModel clientModel, int[] dimensions, GridPane[] islandGrids, List<Node>[] nodeList) {
        dimensions[0] = clientModel.getIslands().size() / 2;
        dimensions[1] = clientModel.getIslands().size() - dimensions[0];
        for (int i = 0; i < dimensions.length; i++) {
            islandGrids[i] = new GridPane();
            islandGrids[i].setHgap(100);
            islandGrids[i].setLayoutX(100);
            islandGrids[i].setLayoutY(200 * (double) i);
            nodeList[i] = new ArrayList<>();
        }
    }

    /**
     * This method physically draws the island: creates the HBox, adds image views in that, and then adds the HBox as anchor pane's child
     *
     * @param island      island to draw
     * @param anchorPane  anchor pane where island has to be drawn
     * @param clientModel clientModel
     * @return HBox
     */
    private HBox drawSingleIsland(Island island, AnchorPane anchorPane, ClientModel clientModel) {
        String currentPath = new java.io.File("src/main/resources/Images/Islands").getAbsolutePath().replace('\\', '/');
        HBox box = new HBox();
        anchorPane.getChildren().add(box);
        for (int j = 0; j < island.getDim(); j++) {
            Image image = new Image(FILE + currentPath + "/Island1.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            box.getChildren().add(imageView);
            drawTowersOnIsland(island, box.getChildren().get(0).getLayoutX() + 60 + 100 * (j), box.getChildren().get(0).getLayoutY() + 60, anchorPane, clientModel);
        }
        box.setPrefWidth((double) island.getDim() * 100);
        box.setPrefHeight(100);
        return box;
    }

    /**
     * This method draws towers on an island or an islands group
     *
     * @param island      island
     * @param x           x coordinate where draw the tower
     * @param y           y coordinate where draw the tower
     * @param anchorPane  anchor pane
     * @param clientModel client model
     */
    private void drawTowersOnIsland(Island island, double x, double y, AnchorPane anchorPane, ClientModel clientModel) {
        if (island.getDominator() != null) {
            Circle tower = new Circle(13);
            anchorPane.getChildren().add(tower);
            tower.setLayoutX(x);
            tower.setLayoutY(y);
            SchoolBoard schoolBoard = clientModel.getSchoolBoards().get(island.getDominator().getUsername());
            if (schoolBoard.getTowerColor() == TowerColor.BLACK)
                tower.setFill(javafx.scene.paint.Color.BLACK);
            else if (schoolBoard.getTowerColor() == TowerColor.WHITE)
                tower.setFill(javafx.scene.paint.Color.WHITE);
            else
                tower.setFill(javafx.scene.paint.Color.GRAY);
        }
    }

    /**
     * This method draws mother nature on the correct island
     *
     * @param clientModel clientModel
     * @param anchorPane  anchor pane
     * @param box         HBox
     * @param i           index of island, we have to draw mother nature only if he is on that island
     */
    private void drawMotherNatureOnIsland(ClientModel clientModel, AnchorPane anchorPane, HBox box, int i) {
        if (clientModel.getIslands().get(i).getId() == clientModel.getMotherNaturePosition().getId()) {
            Circle motherNature = new Circle(11);
            motherNature.setFill(javafx.scene.paint.Color.ORANGE);
            anchorPane.getChildren().add(motherNature);
            motherNature.setLayoutX(box.getChildren().get(0).getLayoutX() + 30);
            motherNature.setLayoutY(box.getChildren().get(0).getLayoutY() + 30);
        }
    }

    /**
     * This method chooses if and Island is drawn in superior grid or inferior grid
     *
     * @param i          index of island
     * @param dimensions dimensions
     * @param nodeList   nodeList
     * @param anchorPane anchor pane
     */
    private void setupNodeList(int i, int[] dimensions, List<Node>[] nodeList, AnchorPane anchorPane) {
        int dimTot = 0;
        for (int j = 0; j < dimensions.length; j++) {
            if (i < dimensions[j] + dimTot) {
                nodeList[j].add(anchorPane);
                break;
            }
            dimTot += dimensions[j];
        }
    }

    /**
     * This method permits starts listen for mouseEvent on HBox panes
     *
     * @param i           island index
     * @param dimensions  dimensions
     * @param clientModel clientModel
     * @param box         HBox
     * @param anchorPane  anchor pane
     * @param islands     all the islandPane's children
     */
    private void startListenForMouseEvent(int i, int[] dimensions, ClientModel clientModel, HBox box, AnchorPane anchorPane, List<Node> islands) {
        int dimTot = 0;
        for (int j = 0; j < dimensions.length; j++) {
            if (i < dimensions[j] + dimTot) {
                handleMouseEntranceAndExit(i, box, islands, clientModel, anchorPane, 100 + 200 * (double) j);
                break;
            }
            dimTot += dimensions[j];
        }

    }

    /**
     * This method physically adds drawn islands with all the structure to the island Pane
     *
     * @param islandGrids islandGrids
     * @param nodeList    nodeList
     * @param islands     islands
     */
    private void addIslandsAsChildren(GridPane[] islandGrids, List<Node>[] nodeList, List<Node> islands) {
        for (int i = 0; i < islandGrids.length; i++) {
            for (int j = 0; j < nodeList[i].size(); j++) {
                islandGrids[i].add(nodeList[i].get(j), j, 0);
            }
            islands.add(islandGrids[i]);
        }
    }

    /**
     * This method prepares everything to listen for mouseEvents on islands
     * onMouseEntered -> is shown island's information
     * onMouseExited -> is shown island's information
     * It uses 3 helper methods, that are defined under this
     *
     * @param i           island index
     * @param box         HBox
     * @param islands     all island pane's children
     * @param clientModel clientModel
     * @param anchorPane  anchorPane
     * @param y           y coordinate of form that should be shown
     */
    private void handleMouseEntranceAndExit(int i, HBox box, List<Node> islands, ClientModel clientModel, AnchorPane anchorPane, double y) {
        AnchorPane infoPane = new AnchorPane();
        islands.add(infoPane);
        infoPane.setVisible(false);
        box.setOnMouseEntered(event -> {
            Island island = clientModel.getIslands().get(i);
            setupInfoPane(infoPane, island.getId(), anchorPane.getLayoutX() + 40, y);
            int labelY = 15;
            for (int j = 0; j < Color.numOfColors(); j++) {
                setupLabel(Color.values()[j].name(), island.getStudents()[j], labelY, infoPane);
                labelY += 15;
            }
            setupNoEntryTiles(infoPane, island.getNoEntryTiles());
            event.consume();
        });
        box.setOnMouseExited(event -> {
            infoPane.getChildren().clear();
            infoPane.setVisible(false);
            event.consume();
        });
    }

    private void setupLabel(String color, int numberOfStudents, int y, AnchorPane anchorPane) {
        Label label = new Label("Number of " + color.toLowerCase() + " students:" + numberOfStudents);
        anchorPane.getChildren().add(label);
        label.setLayoutX(5);
        label.setLayoutY(y);
        label.setStyle("-fx-text-fill: " + color.toLowerCase() + ";");
    }

    private void setupInfoPane(AnchorPane infoPane, int number, double x, double y) {
        Label islandLabel = new Label("Number of island:" + number);
        islandLabel.setLayoutX(5);
        infoPane.getChildren().add(islandLabel);
        infoPane.setPrefHeight(105);
        infoPane.setPrefWidth(160);
        infoPane.setOpacity(1);
        infoPane.setStyle("-fx-background-color: #C7C7C7; -fx-border-color: black;");
        infoPane.setLayoutX(x);
        infoPane.setLayoutY(y);
        infoPane.setVisible(true);
    }

    private void setupNoEntryTiles(AnchorPane infoPane, int number) {
        Label noEntryTilesLabel = new Label("Number of no entry tiles:" + number);
        infoPane.getChildren().add(noEntryTilesLabel);
        noEntryTilesLabel.setLayoutX(5);
        noEntryTilesLabel.setLayoutY(90);
    }

    /**
     * This method starts everything, drawing students and towers on schoolBoards and also islands
     * It uses helper methods, that are defined under this
     *
     * @param clientModel clientModel
     */
    public void initializeMainScene(ClientModel clientModel) {
        createSchoolBoardsMap(clientModel);
        createDeckMap(clientModel);
        initialSchoolBoardsSetup(clientModel);
        drawIslands(clientModel);
        initializeDeckInformation(clientModel);
        if (clientModel.isExpertMode()) {
            showUpdatedWallet(clientModel);
        }
    }

    private void initialSchoolBoardsSetup(ClientModel clientModel) {
        for (int i = 0; i < schoolBoardsMap.size(); i++) {
            String username = new ArrayList<>(schoolBoardsMap.keySet()).get(i);
            drawStudentsOnDiningRoom(((GridPane) schoolBoardsMap.get(username).getChildren().get(1)).getChildren(), clientModel.getSchoolBoards().get(username));
            drawStudentsOnEntrance(((GridPane) schoolBoardsMap.get(username).getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(username));
            drawTowersOnSchoolBoard(((GridPane) schoolBoardsMap.get(username).getChildren().get(4)).getChildren(), clientModel.getSchoolBoards().get(username));
        }
    }

    private void initializeDeckInformation(ClientModel clientModel) {
        String currentPath = new java.io.File("src/main/resources/Images/Wizards").getAbsolutePath().replace('\\', '/');
        ImageView imageView = (ImageView) deckMap.get(clientModel.getUsername()).getChildren().get(0);
        imageView.setImage(new Image(FILE + currentPath + clientModel.getAssistantDeck().getWizard().getImagePath()));
        Set<String> usernames = clientModel.getChosenWizardsPerUsername().keySet();
        for (String username : usernames) {
            if (!username.equals(clientModel.getUsername())) {
                imageView = (ImageView) deckMap.get(username).getChildren().get(0);
                imageView.setImage(new Image(FILE + currentPath + clientModel.getChosenWizardsPerUsername().get(username).getImagePath()));
            }
        }
    }

    private void createSchoolBoardsMap(ClientModel clientModel) {
        int cont = 0;
        List<Node> otherPlayersPane = otherSchoolBoardPane.getChildren().stream().filter(AnchorPane.class::isInstance).collect(Collectors.toList());
        for (int i = 0; i < clientModel.getSchoolBoards().size(); i++) {
            if (clientModel.getUsername().equals(new ArrayList<>(clientModel.getSchoolBoards().keySet()).get(i)))
                schoolBoardsMap.put(clientModel.getUsername(), mySchoolBoard);
            else {
                schoolBoardsMap.put(new ArrayList<>(clientModel.getSchoolBoards().keySet()).get(i), (AnchorPane) otherPlayersPane.get(cont));
                cont++;
            }
        }
    }

    public void createDeckMap(ClientModel clientModel) {
        int cont = 0;
        List<Node> otherPlayersGrid = otherSchoolBoardPane.getChildren().stream().filter(GridPane.class::isInstance).collect(Collectors.toList());
        for (int i = 0; i < clientModel.getChosenWizardsPerUsername().size(); i++) {
            if (clientModel.getUsername().equals(new ArrayList<>(clientModel.getChosenWizardsPerUsername().keySet()).get(i)))
                deckMap.put(clientModel.getUsername(), myDeckGrid);
            else {
                deckMap.put(new ArrayList<>(clientModel.getChosenWizardsPerUsername().keySet()).get(i), (GridPane) otherPlayersGrid.get(cont));
                cont++;
            }
        }
    }

    /**
     * This method modifies the graphic interface setting in imageview the last assistant card played
     *
     * @param assistantCard assistantCard
     * @param clientModel   clientModel
     */
    public void showMyInfoAssistantCardPlayed(AssistantCard assistantCard, ClientModel clientModel) {
        String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\', '/');
        ImageView imageView = (ImageView) deckMap.get(clientModel.getUsername()).getChildren().get(1);
        imageView.setImage(new Image(FILE + currentPath + assistantCard.getType().getImagePath()));
    }

    public void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard) {
        String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\', '/');
        ImageView imageView = (ImageView) deckMap.get(username).getChildren().get(1);
        imageView.setImage(new Image(FILE + currentPath + assistantCard.getType().getImagePath()));
    }

    /**
     * This method is called by gui when a student has been moved into diningRoom
     * It re-draws dining room, entrance and professors grid of the specified player
     *
     * @param username    the player whose SchoolBoard has been changed
     * @param clientModel clientModel
     */
    public void showInfoStudentIntoDiningRoom(String username, ClientModel clientModel) {
        AnchorPane schoolBoardPane = schoolBoardsMap.get(username);
        drawStudentsOnDiningRoom(((GridPane) schoolBoardPane.getChildren().get(1)).getChildren(), clientModel.getSchoolBoards().get(username));
        drawStudentsOnEntrance(((GridPane) schoolBoardPane.getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(username));
        for (int i = 0; i < schoolBoardsMap.keySet().size(); i++) {
            schoolBoardPane = schoolBoardsMap.get(new ArrayList<>(schoolBoardsMap.keySet()).get(i));
            drawProfessors(((GridPane) schoolBoardPane.getChildren().get(3)).getChildren(), new ArrayList<>(schoolBoardsMap.keySet()).get(i), clientModel);
        }
    }

    /**
     * This method is called by gui when a student has been moved from entrance to an island
     * It re-draws only the entrance grid of the specified player
     *
     * @param username    the player whose SchoolBoard has been changed
     * @param clientModel clientModel
     */
    public void showInfoStudentOntoIsland(String username, ClientModel clientModel) {
        updateSchoolBoard(username, clientModel);
    }


    private void updateSchoolBoard(String username, ClientModel clientModel) {
        AnchorPane schoolBoardPane = schoolBoardsMap.get(username);
        drawStudentsOnEntrance(((GridPane) schoolBoardPane.getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(username));
    }

    /**
     * This method is called by gui when mother nature is moved
     * The entire island block is re-drawn
     *
     * @param clientModel clientModel
     */
    public void showMotherNaturePosition(ClientModel clientModel) {
        drawIslands(clientModel);
    }


    /**
     * This method is called by gui when influence changes on an island
     * The entire island block and the tower grid on each schoolBoard are re-drawn
     *
     * @param clientModel clientModel
     */
    public void showChangeInfluenceMessage(ClientModel clientModel) {
        drawIslands(clientModel);
        drawAllSchoolBoardsTowers(clientModel);
    }

    /**
     * This method is called by gui when 2 or more islands are merged
     * The entire island block is re-drawn
     *
     * @param clientModel clientModel
     */
    public void showMergeIslandsMessage(ClientModel clientModel) {
        drawIslands(clientModel);
    }

    /**
     * This method is called when a tower is moved onto an island
     * The entire island block is re-drawn
     *
     * @param clientModel clientModel
     */
    public void showMoveTowerOntoIsland(ClientModel clientModel) {
        drawIslands(clientModel);
    }

    /**
     * This method is called by gui when a tower is moved into a schoolBoard
     * The tower grid on each schoolBoard is re-drawn
     *
     * @param clientModel clientModel
     */
    public void showMoveTowerOntoSchoolBoard(ClientModel clientModel) {
        drawAllSchoolBoardsTowers(clientModel);
    }


    /**
     * This method is called by gui when a player chooses his cloud tile
     * The entrance of the player is re-drawn
     *
     * @param username    username
     * @param clientModel clientModel
     */
    public void showInfoChosenCloudTile(String username, ClientModel clientModel) {
        updateSchoolBoard(username, clientModel);
    }

    /**
     * This method is called when the character card 1 has been executed
     * The entire island block is re-drawn
     *
     * @param clientModel clientModel
     */
    public void showCCModifies01(ClientModel clientModel) {
        drawIslands(clientModel);
    }

    /**
     * This method is called when the character card 7 has been executed
     * Everyone's entrance grid is re-drawn
     *
     * @param clientModel clientModel
     */
    public void showCCModifies07(ClientModel clientModel) {
        for (int i = 0; i < schoolBoardsMap.size(); i++) {
            drawStudentsOnEntrance(((GridPane) schoolBoardsMap.get(new ArrayList<>(schoolBoardsMap.keySet()).get(i)).getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(new ArrayList<>(schoolBoardsMap.keySet()).get(i)));
        }
    }

    /**
     * This method is called when the character card 10 has been executed
     * Everyone's entrance and dining room grid are re-drawn
     *
     * @param clientModel clientModel
     */
    public void showCCModifies10(ClientModel clientModel) {
        for (int i = 0; i < schoolBoardsMap.size(); i++) {
            drawStudentsOnDiningRoom(((GridPane) schoolBoardsMap.get(new ArrayList<>(schoolBoardsMap.keySet()).get(i)).getChildren().get(1)).getChildren(), clientModel.getSchoolBoards().get(new ArrayList<>(schoolBoardsMap.keySet()).get(i)));
            drawStudentsOnEntrance(((GridPane) schoolBoardsMap.get(new ArrayList<>(schoolBoardsMap.keySet()).get(i)).getChildren().get(2)).getChildren(), clientModel.getSchoolBoards().get(new ArrayList<>(schoolBoardsMap.keySet()).get(i)));
        }
    }

    /**
     * This method is called when the character card 11 has been executed
     * Everyone's dining room grid are re-drawn
     *
     * @param clientModel clientModel
     */
    public void showCCModifies11(ClientModel clientModel) {
        for (int i = 0; i < schoolBoardsMap.size(); i++) {
            drawStudentsOnDiningRoom(((GridPane) schoolBoardsMap.get(new ArrayList<>(schoolBoardsMap.keySet()).get(i)).getChildren().get(1)).getChildren(), clientModel.getSchoolBoards().get(new ArrayList<>(schoolBoardsMap.keySet()).get(i)));
        }
    }

    /**
     * This method is called when the character card 12 has been executed
     * Everyone's dining room grid is re-drawn
     *
     * @param clientModel clientModel
     */
    public void showCCModifies12(ClientModel clientModel) {
        for (int i = 0; i < schoolBoardsMap.size(); i++) {
            String username = new ArrayList<>(schoolBoardsMap.keySet()).get(i);
            drawStudentsOnDiningRoom(((GridPane) schoolBoardsMap.get(username).getChildren().get(1)).getChildren(), clientModel.getSchoolBoards().get(username));
        }
    }

    /**
     * This method is called by gui when wallet is updated
     * The coin's grid is re-drawn
     *
     * @param clientModel clientModel
     */
    public void showUpdatedWallet(ClientModel clientModel) {
        moneyPane.getChildren().forEach(x -> x.setVisible(false));
        for (int i = 0; i < clientModel.getWallet(); i++) {
            moneyPane.getChildren().get(i).setVisible(true);
        }
    }
}