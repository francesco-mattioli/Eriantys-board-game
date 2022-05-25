package it.polimi.ingsw.triton.launcher.client.gui.scenes;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;


public abstract class SceneController extends Observable<Message> {
    protected String username;
    protected final String[] colorsName = new String[5];

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This abstract method is implemented in every scene controller.
     * It sets up the scene for the user, adding all javafx elements
     *
     * @param clientModel client model
     * @param parameters  a generic parameter which depends, based on specific scene
     * @param <T>         generic parameter
     */
    public <T> void setupScene(ClientModel clientModel, T parameters) {
    }

    /**
     * This method writes elements in choice box, when user has to choose a student color.
     * It is called by many subclassed.
     *
     * @param choiceBox to fill.
     * @param students  the array of students.
     */
    public void setupStudentsChoiceBox(ChoiceBox<String> choiceBox, int[] students) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < students.length; i++) {
            if (students[i] != 0) {
                list.add(Color.values()[i].name());
            }
        }
        choiceBox.getItems().addAll(list);
    }

    public void setUpAllColors() {
        for (int i = 0; i < Color.values().length; i++) {
            colorsName[i] = Color.values()[i].name();
        }
    }

    protected void setAnchorPaneLayout(AnchorPane anchorPane, double x, double y) {
        anchorPane.setLayoutX(x);
        anchorPane.setLayoutY(y);
    }

    protected void setLabelLayout(Label label, int y) {
        label.setLayoutX(5);
        label.setLayoutY(y);
    }

    protected void setCircleLayout(Circle circle, double x, double y) {
        circle.setLayoutX(x);
        circle.setLayoutY(y);
    }


}
