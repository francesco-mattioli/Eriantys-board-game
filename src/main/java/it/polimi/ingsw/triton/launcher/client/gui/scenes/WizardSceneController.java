package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.WizardReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Map;

public class WizardSceneController extends Observable<Message> {

    @FXML
    AnchorPane wizardPane;

    @FXML
    Button selectButton;

    @FXML
    Polygon leftSwitch;

    @FXML
    Polygon rightSwitch;

    @FXML
    ImageView wizardImageView;

    private String username;
    private Map<Image,Wizard> wizards;
    private int shownWizard = 0;

    public void setWizards(Map<Image, Wizard> wizards) {
        this.wizards = wizards;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public ImageView getWizardImageView(){
        return wizardImageView;
    }

    public void select(ActionEvent event){
        Wizard selectedWizard = wizards.get(wizardImageView.getImage());
        notify(new WizardReply(username, selectedWizard));
        selectButton.setDisable(true);
    }

    public void switchLeft(MouseEvent event){
        if (shownWizard > 0){
            wizardImageView.setImage((Image) wizards.keySet().toArray()[shownWizard-1]);
            shownWizard--;
        }
        if (shownWizard == 0){
            leftSwitch.setFill(Color.GRAY);
            leftSwitch.setOpacity(0.5);
            rightSwitch.setFill(Color.BLUE);
            rightSwitch.setOpacity(1);
        }

    }

    public void switchRight(MouseEvent event){
        if (shownWizard < wizards.size() - 1){
            wizardImageView.setImage((Image) wizards.keySet().toArray()[shownWizard+1]);
            shownWizard++;
        }
        if (shownWizard == wizards.size() - 1){
            rightSwitch.setFill(Color.GRAY);
            rightSwitch.setOpacity(0.5);
            leftSwitch.setFill(Color.BLUE);
            leftSwitch.setOpacity(1);
        }

    }

}
