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

    @FXML
    AnchorPane cloudTile0StudentsPane;

    public AnchorPane getCloudTile0StudentsPane() {
        return cloudTile0StudentsPane;
    }

    public AnchorPane getCloudTile1StudentsPane() {
        return cloudTile1StudentsPane;
    }

    public AnchorPane getCloudTile0Pane() {
        return cloudTile0Pane;
    }

    public AnchorPane getCloudTile1Pane() {
        return cloudTile1Pane;
    }

    @FXML
    AnchorPane cloudTile1StudentsPane;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public void selectCloudTile0(MouseEvent event){
        notify(new CloudTileReply(username,0));
    }

    public void selectCloudTile1(MouseEvent event){
        notify(new CloudTileReply(username,1));
    }


}
