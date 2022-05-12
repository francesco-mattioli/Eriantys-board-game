package it.polimi.ingsw.triton.launcher.server.model.enums;

import java.io.Serializable;

/**
 * Each Wizard has reference color
 */
public enum Wizard implements Serializable {
    GREEN("/Green-Wizard.png"),
    YELLOW("/Yellow-Wizard.png"),
    PURPLE("/Purple-Wizard.png"),
    BLUE("/Blue-Wizard.png");

    private final String imagePath;

    Wizard(String imagePath){
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
