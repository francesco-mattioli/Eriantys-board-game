package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class MainScene2PlayersController extends Observable<Message> {

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
    AnchorPane islandPane;

    @FXML
    AnchorPane studentsIslandPane;


    public AnchorPane getMySchoolBoard() {
        return mySchoolBoard;
    }

    public GridPane getMyDiningRoomGrid() {
        return myDiningRoomGrid;
    }

    public GridPane getMyEntranceGrid() {
        return myEntranceGrid;
    }

    public GridPane getMyProfessorsGrid() {
        return myProfessorsGrid;
    }

    public GridPane getMyTowerGrid() {
        return myTowerGrid;
    }

    public AnchorPane getMySchoolBoardPane() {
        return mySchoolBoardPane;
    }

    public AnchorPane getOtherSchoolBoardPane() {
        return otherSchoolBoardPane;
    }

    public AnchorPane getIslandPane() {
        return islandPane;
    }

    public GridPane getOtherDiningRoomGrid() {
        return otherDiningRoomGrid;
    }

    public GridPane getOtherEntranceGrid() {
        return otherEntranceGrid;
    }

    public GridPane getOtherProfessorsGrid() {
        return otherProfessorsGrid;
    }

    public GridPane getOtherTowerGrid() {
        return otherTowerGrid;
    }
}
