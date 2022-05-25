package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.CharacterCard09Reply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.CharacterCard11Reply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.CharacterCard12Reply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

public class CharCard091112SceneController extends SceneController {

    @FXML
    AnchorPane charCard091112Pane;

    @FXML
    ChoiceBox<String> selectColorChoiceBox;

    @FXML
    Button confirmButton;

    private int id;

    /**
     * This method prepares the form to ask character card 9,11 and 12 parameters
     * Choice boxes are populated with available colors and islands
     *
     * @param clientModel clientModel
     * @param parameters  a generic parameter which depends, based on specific scene
     * @param <T>generic  parameter
     */
    @Override
    public <T> void setupScene(ClientModel clientModel, T parameters) {
        id = (int) parameters;
        if (id == 11)
            setupStudentsChoiceBox(selectColorChoiceBox, clientModel.getCharacterCardById(11).getStudents());
        else {
            setUpAllColors();
            selectColorChoiceBox.getItems().addAll(colorsName);
        }
        selectColorChoiceBox.setOnAction(this::activeButton);
    }


    /**
     * User has to choose a color
     * On confirm button click, is sent a message to server containing the card id, 9, 11 or 12
     */
    public void confirm() {
        confirmButton.setDisable(true);
        if (id == 9)
            notify(new CharacterCard09Reply(Color.valueOf(selectColorChoiceBox.getValue())));
        if (id == 11)
            notify(new CharacterCard11Reply(Color.valueOf(selectColorChoiceBox.getValue())));
        if (id == 12)
            notify(new CharacterCard12Reply(Color.valueOf(selectColorChoiceBox.getValue())));
    }

    /**
     * At the beginning, button is disabled, because user must choose a color
     * When choice box contains a value, button is activated
     *
     * @param event on choice box action
     */
    private void activeButton(ActionEvent event) {
        confirmButton.setDisable(false);
    }
}
