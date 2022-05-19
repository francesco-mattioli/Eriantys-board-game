package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.UseCharacterCardRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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

    private AnchorPane infoPane;

    private final Map<BorderPane, Integer> characterCardsMap = new HashMap<>();

    private ClientModel clientModel;

    public void playCharacterCard(ActionEvent event){
        notify(new UseCharacterCardRequest(username, cardID));
        buttonPlay.setDisable(true);
    }

    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        this.clientModel = clientModel;
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

    public void showInfo(javafx.scene.input.MouseEvent event){
        characterCardPane.getChildren().removeIf(x-> x instanceof AnchorPane);
        BorderPane actualPane = (BorderPane) event.getSource();
        infoPane = new AnchorPane();
        characterCardPane.getChildren().add(infoPane);
        CharacterCard characterCard = clientModel.getCharacterCardById(characterCardsMap.get(actualPane));
        double x = actualPane.getLayoutX();
        double y = actualPane.getLayoutY() + actualPane.getHeight();
        setupInfoPane(infoPane, characterCard.getId(), characterCard.getCost() ,x, y);
        int labelY = 30;
        for(int j = 0; j< it.polimi.ingsw.triton.launcher.server.model.enums.Color.numOfColors(); j++){
            setupLabel(it.polimi.ingsw.triton.launcher.server.model.enums.Color.values()[j].name(), characterCard.getStudents()[j], 5, labelY, infoPane);
            labelY+= 15;
        }
        setupNoEntryTiles(infoPane, characterCard.getNoEntryTiles());
    }

    private void setupLabel(String color, int numberOfStudents, int x, int y, AnchorPane anchorPane){
        Label label = new Label("Number of " + color.toLowerCase() + " students:" + numberOfStudents);
        anchorPane.getChildren().add(label);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setStyle("-fx-text-fill: " + color.toLowerCase() + ";");
    }

    private void setupInfoPane(AnchorPane infoPane, int ID, int cost, double x, double y){
        Label idLabel = new Label("Card ID:" + ID);
        idLabel.setLayoutX(5);
        infoPane.getChildren().add(idLabel);
        Label costLabel = new Label("Card cost:" + cost);
        costLabel.setLayoutX(5);
        costLabel.setLayoutY(15);
        infoPane.getChildren().add(costLabel);
        infoPane.setOpacity(1);
        infoPane.setStyle("-fx-background-color: #C7C7C7; -fx-border-color: black;");
        infoPane.setLayoutX(x);
        infoPane.setLayoutY(y);
        infoPane.setVisible(true);
    }

    private void setupNoEntryTiles(AnchorPane infoPane, int number){
        Label noEntryTilesLabel = new Label("Number of no entry tiles:" + number);
        infoPane.getChildren().add(noEntryTilesLabel);
        noEntryTilesLabel.setLayoutX(5);
        noEntryTilesLabel.setLayoutY(105);
    }


    public void hideInfo(MouseEvent event){
        infoPane.setVisible(false);
    }
}
