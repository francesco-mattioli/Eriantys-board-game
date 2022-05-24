package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.gui.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuSceneController extends SceneController {

    @FXML
    Button joinButton;

    @FXML
    AnchorPane menuPane;

    /**
     * Gui is created, and stage is set
     */
    public void join(ActionEvent event) {
        Gui gui = new Gui(((Stage)((Node)event.getSource()).getScene().getWindow()));
        gui.startGui();
    }

}
