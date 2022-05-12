package it.polimi.ingsw.triton.launcher.server.model.enums;

import java.io.Serializable;

public enum AssistantCardType implements Serializable {
    TIGER(1,1,"/Tiger.png"),
    DUCK(2,1,"/Duck.png"),
    CAT(3,2,"/Cat.png"),
    EAGLE(4,2,"/Eagle.png"),
    FOX(5,3,"/Fox.png"),
    SNAKE(6,3,"/Snake.png"),
    OCTOPUS(7,4,"/Octopus.png"),
    DOG(8,4,"/Dog.png"),
    ELEPHANT(9,5,"/Elephant.png"),
    TURTLE(10,5,"/Turtle.png");

    private final int value;
    private final int maxSteps;
    private final String imagePath;



    AssistantCardType(int value, int maxSteps, String imagePath){
        this.value=value;
        this.maxSteps=maxSteps;
        this.imagePath = imagePath;
    }

    public int getValue() {
        return value;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public String getImagePath() {
        return imagePath;
    }
}
