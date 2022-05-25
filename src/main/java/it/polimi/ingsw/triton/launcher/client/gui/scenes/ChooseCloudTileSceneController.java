package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.CloudTileReply;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class ChooseCloudTileSceneController extends ActionPhaseSceneControllers {

    @FXML
    AnchorPane chooseCloudTilePane;

    public void selectCloudTile0() {
        notifyChosenCloudTileAndClosePane(0);
    }

    public void selectCloudTile1() {
        notifyChosenCloudTileAndClosePane(1);
    }

    public void selectCloudTile2() {
        notifyChosenCloudTileAndClosePane(2);
    }

    /**
     * This method sends a message to server containing the cloud tile id that user has chosen
     *
     * @param cloudTileId the id of cloud tile that user has chosen
     */
    private void notifyChosenCloudTileAndClosePane(int cloudTileId) {
        notify(new CloudTileReply(cloudTileId));
        ((Stage) chooseCloudTilePane.getScene().getWindow()).close();
    }

    /**
     * This method prepares the cloud tile scene, setting correct image views containing the correct students colors
     *
     * @param clientModel clientModel
     * @param parameters  a generic parameter which depends, based on specific scene
     * @param <T>         generic parameter
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        Map<Integer, AnchorPane> cloudTilesMap = new HashMap<>();
        List<Node> cloudTilesPanes = chooseCloudTilePane.getChildren().stream().filter(AnchorPane.class::isInstance).collect(Collectors.toList());
        for (int i = 0; i < 3; i++) {
            if (clientModel.getCloudTileById(i) != null)
                cloudTilesMap.put(i, ((AnchorPane) cloudTilesPanes.get(i)));
        }
        prepareImages(clientModel, cloudTilesMap);
    }

    /**
     * This method graphically adds students on correct cloud tile
     *
     * @param clientModel   clientModel
     * @param cloudTilesMap association between a CloudTileID and his anchorPane
     */
    private void prepareImages(ClientModel clientModel, Map<Integer, AnchorPane> cloudTilesMap) {
        for (int i = 0; i < 3; i++) {
            if (clientModel.getCloudTileById(i) != null) {
                Optional<Node> result = cloudTilesMap.get(i).getChildren().stream().filter(AnchorPane.class::isInstance).findFirst();
                if (result.isPresent()) {
                    AnchorPane studentsPane = (AnchorPane) result.get();
                    List<Node> imagesOnCloudTile = studentsPane.getChildren();
                    List<Color> studentsOnCloudTile = arrayToList(clientModel, i);
                    for (int j = 0; j < imagesOnCloudTile.size(); j++) {
                        ((ImageView) imagesOnCloudTile.get(j)).setImage(new Image(Objects.requireNonNull(ChooseCloudTileSceneController.class.getResource("/Images/Students" + studentsOnCloudTile.get(j).getStudentImagePath())).toString()));
                    }
                    cloudTilesMap.get(i).setVisible(true);
                }
            }
        }
    }

    /**
     * This method creates a List of Color
     * Students are stored as an array of 5 elements: every element counts number of students with that color
     * Is necessary to create a list to take correct students on cloud tile
     *
     * @param clientModel clientModel
     * @param i           cloud tile index in arrayList of cloud tiles
     * @return the list of colors
     */
    private List<Color> arrayToList(ClientModel clientModel, int i) {
        List<Color> studentsOnCloudTile = new ArrayList<>();
        for (int j = 0; j < clientModel.getCloudTileById(i).getStudents().length; j++) {
            for (int k = 0; k < clientModel.getCloudTileById(i).getStudents()[j]; k++) {
                studentsOnCloudTile.add(Color.values()[j]);
            }
        }
        return studentsOnCloudTile;
    }
}
