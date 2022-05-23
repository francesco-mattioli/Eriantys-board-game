package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.AssistantCardReply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class AssistantCardSceneController extends SceneController {
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

    private Map<Image, AssistantCard> assistantCardImages;
    private int shownAssistantCard = 0;

    /**
     * The user can scroll through the assistant card images
     * When user clicks on selectButton, the currently selected assistant card is assigned to him
     * To do this, we have a map between Image and AssistantCard
     * @param event
     */
    public void select(ActionEvent event){
        AssistantCard selectedAssistantCard = assistantCardImages.get(assistantCardImageView.getImage());
        notify(new AssistantCardReply(selectedAssistantCard));
        selectButton.setDisable(true);
        ((Stage) assistantCardPane.getScene().getWindow()).close();
    }

    /**
     * This method permits user to scroll through the assistant card images
     * When card is last one, so you can't scroll, darts are different colorized
     * @param event
     */
    public void switchLeft(MouseEvent event){
        if (shownAssistantCard > 0){
            assistantCardImageView.setImage((Image) assistantCardImages.keySet().toArray()[shownAssistantCard-1]);
            shownAssistantCard--;
            rightSwitch.setFill(Color.BLUE);
            rightSwitch.setOpacity(1);
        }
        if (shownAssistantCard == 0){
            leftSwitch.setFill(Color.GRAY);
            leftSwitch.setOpacity(0.5);
        }

    }

    /**
     * This method permits user to scroll through the assistant card images
     * When card is last one, so you can't scroll, darts are different colorized
     * @param event
     */
    public void switchRight(MouseEvent event){
        if (shownAssistantCard < assistantCardImages.size() - 1){
            assistantCardImageView.setImage((Image) assistantCardImages.keySet().toArray()[shownAssistantCard+1]);
            shownAssistantCard++;
            leftSwitch.setFill(Color.BLUE);
            leftSwitch.setOpacity(1);
        }
        if (shownAssistantCard == assistantCardImages.size() - 1){
            rightSwitch.setFill(Color.GRAY);
            rightSwitch.setOpacity(0.5);
        }

    }

    /**
     * In this method we create the map that associates Images and AssistantCard, and we prepare everything for user interaction
     * @param clientModel
     * @param parameters
     * @param <T>
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        assistantCardImages = new HashMap<>();
        String currentPath = new java.io.File("src/main/resources/Images/AssistantCards").getAbsolutePath().replace('\\', '/');
        for (AssistantCard assistantCard : clientModel.getAssistantDeck().getAssistantDeck()) {
            assistantCardImages.put(new Image("file:" + currentPath + assistantCard.getType().getImagePath()), assistantCard);
        }
        assistantCardImageView.setImage((Image) assistantCardImages.keySet().toArray()[0]);
        leftSwitch.setFill(javafx.scene.paint.Color.GRAY);
        leftSwitch.setOpacity(0.5);
        if (assistantCardImages.size() == 1) {
            rightSwitch.setFill(javafx.scene.paint.Color.GRAY);
            rightSwitch.setOpacity(0.5);
        }
    }

}
