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

    opens it.polimi.ingsw.triton.launcher.client.gui to javafx.fxml;
    exports it.polimi.ingsw.triton.launcher.client.gui;
    exports it.polimi.ingsw.triton.launcher.client.gui.scenes;
    opens it.polimi.ingsw.triton.launcher.client.gui.scenes to javafx.fxml;
}