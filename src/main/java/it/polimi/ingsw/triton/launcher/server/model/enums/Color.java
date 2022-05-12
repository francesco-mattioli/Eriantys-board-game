package it.polimi.ingsw.triton.launcher.server.model.enums;

import java.io.Serializable;

public enum Color implements Serializable {
    GREEN("/GreenProfessor.png","/GreenStudent.png"),
    RED("/RedProfessor.png","/RedStudent.png"),
    YELLOW("/YellowProfessor.png","/YellowStudent.png"),
    PINK("/PinkProfessor.png","/PinkStudent.png"),
    BLUE("/BlueProfessor.png","/BlueStudent.png");




    private final String professorImagePath;
    private final String studentImagePath;

    Color(String professorImagePath, String studentImagePath){
        this.professorImagePath = professorImagePath;
        this.studentImagePath = studentImagePath;
    }

    public static int numOfColors(){
        return Color.values().length;
    }

    public String getProfessorImagePath() {
        return professorImagePath;
    }

    public String getStudentImagePath() {
        return studentImagePath;
    }

}
