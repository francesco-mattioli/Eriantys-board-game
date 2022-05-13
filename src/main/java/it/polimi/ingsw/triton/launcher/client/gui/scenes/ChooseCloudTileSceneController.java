package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.CloudTileReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Map;

public class ChooseCloudTileSceneController extends Observable<Message> {

    @FXML
    AnchorPane chooseCloudTilePane;

    @FXML
    AnchorPane cloudTile0Pane;

    @FXML
    AnchorPane cloudTile1Pane;

    private String username;
    private int id;
    Stage stage;
    Map<CloudTile,AnchorPane> cloudTileAnchorPaneMap;

    public void setUsername(String username) {
        this.username = username;
    }

    public void select(MouseEvent event){
        notify(new CloudTileReply(username,id));
    }


}
