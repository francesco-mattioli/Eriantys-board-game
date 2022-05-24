package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.AssistantCardReply;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
     */
    public void select(){
        AssistantCard selectedAssistantCard = assistantCardImages.get(assistantCardImageView.getImage());
        notify(new AssistantCardReply(selectedAssistantCard));
        selectButton.setDisable(true);
        ((Stage) assistantCardPane.getScene().getWindow()).close();
    }

    /**
     * This method permits user to scroll through the assistant card images
     * When card is last one, so you can't scroll, darts are different colorized
     */
    public void switchLeft(){
        if (shownAssistantCard > 0){
            showAdjacentAssistantCard(-1);
            rightSwitch.setFill(Color.BLUE);
            rightSwitch.setOpacity(1);
        }
        if (shownAssistantCard == 0){
            leftSwitch.setFill(Color.GRAY);
            leftSwitch.setOpacity(0.5);
        }

    }

    /**
     * This method allows user to scroll through the assistant card images
     * When card is last one, so you can't scroll, darts are different colorized
     */
    public void switchRight(){
        if (shownAssistantCard < assistantCardImages.size() - 1){
            showAdjacentAssistantCard(1);
            leftSwitch.setFill(Color.BLUE);
            leftSwitch.setOpacity(1);
        }
        if (shownAssistantCard == assistantCardImages.size() - 1){
            rightSwitch.setFill(Color.GRAY);
            rightSwitch.setOpacity(0.5);
        }

    }

    /**
     * This method shows to the user the next assistant card, allowing him to scroll between assistant cards
     * @param offset position of next card in deck
     */
    private void showAdjacentAssistantCard(int offset) {
        assistantCardImageView.setImage((Image) assistantCardImages.keySet().toArray()[shownAssistantCard+offset]);
        shownAssistantCard+=offset;
    }


    /**
     * In this method we create the map that associates Images and AssistantCard, and we prepare everything for user interaction
     * @param clientModel clientModel
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
