package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.utils.message.UpdatedServerInfoMessage;
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


    /**
     * User chooses destination ip address to connect
     */
    public void connect() {
        String ip = ipTextField.getText();
        notify(new UpdatedServerInfoMessage(ip));
        ipAddressPane.setDisable(true);
    }

}
