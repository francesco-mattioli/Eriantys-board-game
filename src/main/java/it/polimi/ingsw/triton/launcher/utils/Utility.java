package it.polimi.ingsw.triton.launcher.utils;

import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import java.util.List;

public class Utility {
    private static final Utility instance = new Utility();

    private Utility(){
    }

    public static Utility getInstance(){
        return instance;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BOLDGREEN = "\u001B[1;32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BOLDYELLOW = "\u001B[1;33m";
    public static final String ANSI_PINK = "\u001B[35m";

    /**
     * @param students the students to print.
     * @return the string of the students with their colors.
     */
    public static String printColoredStudents(int[] students){
        String results = "";
        for (int i = 0; i < students.length; i++){
            if (i == 0)
                results += ("[" + ANSI_YELLOW + students[i] + ANSI_RESET + ",");
            else if (i == 1)
                results += (ANSI_BLUE + students[i] + ANSI_RESET + ",");
            else if (i == 2)
                results += (ANSI_GREEN + students[i] + ANSI_RESET + ",");
            else if (i == 3)
                results += (ANSI_RED + students[i] + ANSI_RESET + ",");
            else if (i == 4)
                results += (ANSI_PINK + students[i] + ANSI_RESET + "]");
        }
        return results;
    }

    /**
     * @param professors the professors to print.
     * @return the string of professors to print with their colors.
     */
    public static String printColoredProfessorsOnTable(String[] professors){
        String results = "[";
        for(int i = 0; i < professors.length; i++){
            if (i == 0 && professors[i] == null)
                results += (ANSI_YELLOW + "X" + ANSI_RESET + ",");
            else if (i == 1 && professors[i] == null)
                results += (ANSI_BLUE + "X" + ANSI_RESET + ",");
            else if (i == 2 && professors[i] == null)
                results += (ANSI_GREEN + "X" + ANSI_RESET + ",");
            else if (i == 3 && professors[i] == null)
                results += (ANSI_RED + "X" + ANSI_RESET + ",");
            else if (i == 4 && professors[i] == null)
                results += (ANSI_PINK + "X" + ANSI_RESET + "]");
            else if (i != 4 && professors[i] != null)
                results += ("_,");
            else if (i == 4)
                results += ("_]");
        }
        return results;
    }

    /**
     * @param professors the professors to print.
     * @param owner the schoolboard owner.
     * @return the string of professors to print with their colors.
     */
    public static String printColoredProfessorsOnSchoolBoard(String[] professors, String owner){
        String results = "[";
        for(int i = 0; i < professors.length; i++){
            if((i != professors.length-1 && professors[i] == null) || (i != professors.length-1 && !professors[i].equals(owner)))
                results += "_,";
            else if((i == professors.length-1 && professors[i] == null) || (i == professors.length-1 && !professors[i].equals(owner)))
                results += "_]";
            else if(i == 0)
                results += ANSI_YELLOW + "X" + ANSI_RESET + ",";
            else if(i == 1)
                results += ANSI_BLUE + "X" + ANSI_RESET + ",";
            else if(i == 2)
                results += ANSI_GREEN + "X" + ANSI_RESET + ",";
            else if(i == 3)
                results += ANSI_RED + "X" + ANSI_RESET + ",";
            else if(i == 4)
                results += ANSI_PINK + "X" + ANSI_RESET + "]";
        }
        return results;
    }

    /**
     * @param wizards the available wizards.
     * @return the string with the available wizards.
     */
    public static String printAvailableWizards(List<Wizard> wizards){
        String results = "[";
        for(int i = 0; i < wizards.size(); i++){
            results += wizards.get(i);
            if(i != wizards.size() - 1)
                results += ", ";
            else
                results += "]";
        }
        return results;
    }
}
