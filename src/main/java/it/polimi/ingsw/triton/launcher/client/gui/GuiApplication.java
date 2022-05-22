package it.polimi.ingsw.triton.launcher.client.gui;

import it.polimi.ingsw.triton.launcher.client.gui.scenes.MenuSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class GuiApplication extends Application {

    private Scene scene;
    private Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu-scene.fxml"));
        root = loader.load();
        String currentPath = new java.io.File("src/main/resources/Images/menuprova.jpg").getAbsolutePath().replace('\\','/');
        Image img = new Image("file:" + currentPath);
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100,100,true,true,false,true));
        Background bGround = new Background(bImg);
        ((MenuSceneController)loader.getController()).getMenuPane().setBackground(bGround);
        currentPath = new java.io.File("src/main/resources/Images/Buttons/playbutton.png").getAbsolutePath().replace('\\','/');
        img = new Image("file:" + currentPath);
        bImg = new BackgroundImage(img,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100,100,true,true,false,true));
        bGround = new Background(bImg);
        ((MenuSceneController)loader.getController()).getJoinButton().setBackground(bGround);
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
