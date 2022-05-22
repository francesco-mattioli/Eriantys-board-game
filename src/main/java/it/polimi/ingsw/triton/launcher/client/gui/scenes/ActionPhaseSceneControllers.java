package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class ActionPhaseSceneControllers extends SceneController{
    @FXML
    Button playCCButton;

    public Button getPlayCCButton() {
        return playCCButton;
    }
}
