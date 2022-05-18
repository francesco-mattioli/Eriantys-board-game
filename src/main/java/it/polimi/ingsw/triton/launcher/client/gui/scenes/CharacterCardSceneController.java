package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.UseCharacterCardRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.w3c.dom.events.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class CharacterCardSceneController extends SceneController {
    @FXML
    AnchorPane characterCardPane;

    @FXML
    BorderPane card1Pane;

    @FXML
    BorderPane card2Pane;

    @FXML
    BorderPane card3Pane;

    @FXML
    Button buttonPlay;

    private int cardID;

    private final Map<BorderPane, Integer> characterCardsMap = new HashMap<>();

    public void playCharacterCard(ActionEvent event){
        notify(new UseCharacterCardRequest(username, cardID));
        buttonPlay.setDisable(true);
    }

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        setCharacterCardImage(card1Pane, 0, clientModel);
        setCharacterCardImage(card2Pane, 1, clientModel);
        setCharacterCardImage(card3Pane, 2, clientModel);
    }

    private void setCharacterCardImage(BorderPane cardPane, int index, ClientModel clientModel){
        characterCardsMap.put(cardPane, clientModel.getAvailableCharacterCards().get(index).getId());
        ImageView imageView = (ImageView) cardPane.getCenter();
        String currentPath = new java.io.File( "src/main/resources/Images/CharacterCards").getAbsolutePath().replace('\\','/');
        Image image = new Image("file:"+currentPath+"/CharacterCard"+characterCardsMap.get(cardPane) + ".jpg");
        imageView.setImage(image);
    }

    public void selectCharacterCard1(javafx.scene.input.MouseEvent event){
        card1Pane.setBorder(new Border(new BorderStroke(Color.GRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        cardID = characterCardsMap.get(card1Pane);
        card2Pane.setBorder(new Border(new BorderStroke(Color.WHITE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        card3Pane.setBorder(new Border(new BorderStroke(Color.WHITE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        buttonPlay.setDisable(false);
    }

    public void selectCharacterCard2(javafx.scene.input.MouseEvent event){
        card2Pane.setBorder(new Border(new BorderStroke(Color.GRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        cardID = characterCardsMap.get(card2Pane);
        card1Pane.setBorder(new Border(new BorderStroke(Color.WHITE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        card3Pane.setBorder(new Border(new BorderStroke(Color.WHITE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        buttonPlay.setDisable(false);
    }

    public void selectCharacterCard3(javafx.scene.input.MouseEvent event){
        card3Pane.setBorder(new Border(new BorderStroke(Color.GRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        cardID = characterCardsMap.get(card3Pane);
        card1Pane.setBorder(new Border(new BorderStroke(Color.WHITE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        card2Pane.setBorder(new Border(new BorderStroke(Color.WHITE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        buttonPlay.setDisable(false);
    }
}
