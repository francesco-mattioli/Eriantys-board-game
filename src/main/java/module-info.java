module it.polimi.ingsw.triton.launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.logging;
    requires org.jetbrains.annotations;
    requires commons.validator;

    opens it.polimi.ingsw.triton.launcher.client.gui to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.client.gui;
    exports it.polimi.ingsw.triton.launcher.client.gui.scenes;
    opens it.polimi.ingsw.triton.launcher.client.gui.scenes to javafx.fxml;

    exports it.polimi.ingsw.triton.launcher.client.model to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.server.model.enums to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.utils.message to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.server.model to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.server.model.player to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.server.model.playeractions to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.utils.exceptions to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.server.model.cardeffects to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.server.model.islands to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.server.model.professor to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.server.model.influencestrategy to javafx.fxml;


}