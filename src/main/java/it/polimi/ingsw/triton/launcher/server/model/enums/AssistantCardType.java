package it.polimi.ingsw.triton.launcher.server.model.enums;

import java.io.Serializable;

public enum AssistantCardType implements Serializable {
    TIGER(1,1),
    DUCK(2,1),
    CAT(3,2),
    EAGLE(4,2),
    FOX(5,3),
    SNAKE(6,3),
    OCTOPUS(7,4),
    DOG(8,4),
    ELEPHANT(9,5),
    TURTLE(10,5);

    private final int value;
    private final int maxSteps;

    AssistantCardType(int value, int maxSteps){
        this.value=value;
        this.maxSteps=maxSteps;
    }

    public int getValue() {
        return value;
    }

    public int getMaxSteps() {
        return maxSteps;
    }
}
