package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.UpdatedServerInfoMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class IpAddressSceneController extends SceneController {

    @FXML
    Button connectButton;

    @FXML
    AnchorPane ipAddressPane;

    @FXML
    TextField ipTextField;

    public void connect(ActionEvent event){
        String ip = ipTextField.getText();
        notify(new UpdatedServerInfoMessage(ip));
        ipAddressPane.setDisable(true);
    }

    @Override
    public AnchorPane getAnchorPane() {
        return ipAddressPane;
    }
}
