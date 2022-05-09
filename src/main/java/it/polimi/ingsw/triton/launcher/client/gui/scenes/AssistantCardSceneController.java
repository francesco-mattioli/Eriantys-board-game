package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.AssistantCardReply;
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

public class AssistantCardSceneController extends Observable<Message> {
    @FXML
    AnchorPane assistantCardPane;

    @FXML
    Button selectButton;

    @FXML
    Polygon leftSwitch;

    @FXML
    Polygon rightSwitch;

    @FXML
    ImageView assistantCardImageView;

    private String username;
    private Map<Image, AssistantCard> assistantCards;
    private int shownAssistantCard = 0;

    public void setAssistantCards(Map<Image, AssistantCard> assistantCards) {
        this.assistantCards = assistantCards;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public Polygon getLeftSwitch() {
        return leftSwitch;
    }

    public Polygon getRightSwitch() {
        return rightSwitch;
    }

    public ImageView getAssistantCardImageView() {
        return assistantCardImageView;
    }

    public void select(ActionEvent event){
        AssistantCard selectedAssistantCard = assistantCards.get(assistantCardImageView.getImage());
        notify(new AssistantCardReply(username, selectedAssistantCard));
        selectButton.setDisable(true);
    }

    public void switchLeft(MouseEvent event){
        if (shownAssistantCard > 0){
            assistantCardImageView.setImage((Image) assistantCards.keySet().toArray()[shownAssistantCard-1]);
            shownAssistantCard--;
            rightSwitch.setFill(Color.BLUE);
            rightSwitch.setOpacity(1);
        }
        if (shownAssistantCard == 0){
            leftSwitch.setFill(Color.GRAY);
            leftSwitch.setOpacity(0.5);
        }

    }

    public void switchRight(MouseEvent event){
        if (shownAssistantCard < assistantCards.size() - 1){
            assistantCardImageView.setImage((Image) assistantCards.keySet().toArray()[shownAssistantCard+1]);
            shownAssistantCard++;
            leftSwitch.setFill(Color.BLUE);
            leftSwitch.setOpacity(1);
        }
        if (shownAssistantCard == assistantCards.size() - 1){
            rightSwitch.setFill(Color.GRAY);
            rightSwitch.setOpacity(0.5);
        }

    }
}
