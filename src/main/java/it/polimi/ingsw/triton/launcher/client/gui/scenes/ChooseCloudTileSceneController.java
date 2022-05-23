package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.CloudTileReply;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChooseCloudTileSceneController extends ActionPhaseSceneControllers {

    @FXML
    AnchorPane chooseCloudTilePane;

    private Map<Integer, AnchorPane> cloudTilesMap;

    public void selectCloudTile0(MouseEvent event){
        notifyChosenCloudTileAndClosePane(0);
    }

    public void selectCloudTile1(MouseEvent event){
        notifyChosenCloudTileAndClosePane(1);
    }

    public void selectCloudTile2(MouseEvent event){
        notifyChosenCloudTileAndClosePane(2);
    }

    private void notifyChosenCloudTileAndClosePane(int cloudTileId){
        notify(new CloudTileReply(cloudTileId));
        ((Stage) chooseCloudTilePane.getScene().getWindow()).close();
    }


    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        String currentPath = new java.io.File("src/main/resources/Images/Students").getAbsolutePath().replace('\\', '/');
        cloudTilesMap = new HashMap<>();
        List<Node> cloudTilesPanes = chooseCloudTilePane.getChildren().stream().filter(x->x instanceof AnchorPane).collect(Collectors.toList());
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
                for(int j = 0; j< imagesOnCloudTile.size(); j++){
                    ((ImageView)imagesOnCloudTile.get(j)).setImage(new Image("file:" + currentPath + studentsOnCloudTile.get(j).getStudentImagePath()));
                }
                cloudTilesMap.get(i).setVisible(true);
            }
        }
    }

}
