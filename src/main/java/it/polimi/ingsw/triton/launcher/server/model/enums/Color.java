package it.polimi.ingsw.triton.launcher.server.model.enums;

import java.io.Serializable;

public enum Color implements Serializable {
    GREEN("/GreenStudent.png"),
    RED("/RedStudent.png"),
    YELLOW("/YellowStudent.png"),
    PINK("/PinkStudent.png"),
    BLUE("/BlueStudent.png");

    private final String studentImagePath;

    Color(String studentImagePath) {
        this.studentImagePath = studentImagePath;
    }

    public static int numOfColors() {
        return Color.values().length;
    }


    public String getStudentImagePath() {
        return studentImagePath;
    }

}
