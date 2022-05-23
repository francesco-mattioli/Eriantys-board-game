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

    public void selectCloudTile0(){
        notifyChosenCloudTileAndClosePane(0);
    }

    public void selectCloudTile1(){
        notifyChosenCloudTileAndClosePane(1);
    }

    public void selectCloudTile2(){
        notifyChosenCloudTileAndClosePane(2);
    }

    private void notifyChosenCloudTileAndClosePane(int cloudTileId){
        notify(new CloudTileReply(cloudTileId));
        ((Stage)chooseCloudTilePane.getScene().getWindow()).close();
    }


    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        String currentPath = new java.io.File("src/main/resources/Images/Students").getAbsolutePath().replace('\\', '/');
        Map<Integer, AnchorPane> cloudTilesMap = new HashMap<>();
        List<Node> cloudTilesPanes = chooseCloudTilePane.getChildren().stream().filter(AnchorPane.class::isInstance).collect(Collectors.toList());
        for(int i = 0; i<3; i++){
            if(clientModel.getCloudTileById(i) != null)
                cloudTilesMap.put(i, ((AnchorPane)cloudTilesPanes.get(i)));
        }
        for(int i = 0; i<3; i++){
            if(clientModel.getCloudTileById(i) != null){
                Optional<Node> result = cloudTilesMap.get(i).getChildren().stream().filter(AnchorPane.class::isInstance).findFirst();
                if(result.isPresent()) {
                    AnchorPane studentsPane = (AnchorPane) result.get();
                    List<Node> imagesOnCloudTile = studentsPane.getChildren();
                    List<Color> studentsOnCloudTile = new ArrayList<>();
                    for (int j = 0; j < clientModel.getCloudTileById(i).getStudents().length; j++) {
                        for (int k = 0; k < clientModel.getCloudTileById(i).getStudents()[j]; k++) {
                            studentsOnCloudTile.add(Color.values()[j]);
                        }
                    }
                    for (int j = 0; j < imagesOnCloudTile.size(); j++) {
                        ((ImageView) imagesOnCloudTile.get(j)).setImage(new Image("file:" + currentPath + studentsOnCloudTile.get(j).getStudentImagePath()));
                    }
                    cloudTilesMap.get(i).setVisible(true);
                }
            }
        }
    }

}
