package it.polimi.ingsw.triton.launcher.utils;

import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;

import java.util.List;

public class Utility {

    private Utility() {
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BOLDGREEN = "\u001B[1;32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BOLDYELLOW = "\u001B[1;33m";
    public static final String ANSI_PINK = "\u001B[35m";
    public static final String ANSI_CLEAR_CONSOLE = "\033[H\033[2J";

    /**
     * @param students the students to print.
     * @return the string of the students with their colors.
     */
    public static String printColoredStudents(int[] students) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < students.length; i++) {
            if (i == 0)
                result.append("[ " + ANSI_GREEN).append(students[i]).append(ANSI_RESET).append(",");
            else if (i == 1)
                result.append(" " + ANSI_RED).append(students[i]).append(ANSI_RESET).append(",");
            else if (i == 2)
                result.append(" " + ANSI_YELLOW).append(students[i]).append(ANSI_RESET).append(",");
            else if (i == 3)
                result.append(" " + ANSI_PINK).append(students[i]).append(ANSI_RESET).append(",");
            else if (i == 4)
                result.append(" " + ANSI_BLUE).append(students[i]).append(ANSI_RESET).append(" ]");
        }
        return result.toString();
    }

    /**
     * @param professors the professors to print.
     * @return the string of professors to print with their colors.
     */
    public static String printColoredProfessorsOnTable(String[] professors) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < professors.length; i++) {
            if (i == 0 && professors[i] == null)
                result.append(ANSI_GREEN + "X" + ANSI_RESET + ",");
            else if (i == 1 && professors[i] == null)
                result.append(ANSI_RED + "X" + ANSI_RESET + ",");
            else if (i == 2 && professors[i] == null)
                result.append(ANSI_YELLOW + "X" + ANSI_RESET + ",");
            else if (i == 3 && professors[i] == null)
                result.append(ANSI_PINK + "X" + ANSI_RESET + ",");
            else if (i == 4 && professors[i] == null)
                result.append(ANSI_BLUE + "X" + ANSI_RESET + "]");
            else if (i != 4 && professors[i] != null)
                result.append("_,");
            else if (i == 4)
                result.append("_]");
        }
        return result.toString();
    }

    /**
     * @param professors the professors to print.
     * @param owner      the school board owner.
     * @return the string of professors to print with their colors.
     */
    public static String printColoredProfessorsOnSchoolBoard(String[] professors, String owner) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < professors.length; i++) {
            if ((i != professors.length - 1 && professors[i] == null) || (i != professors.length - 1 && !professors[i].equals(owner)))
                result.append("_,");
            else if ((i == professors.length - 1 && professors[i] == null) || (i == professors.length - 1 && !professors[i].equals(owner)))
                result.append("_]");
            else if (i == 0)
                result.append(ANSI_GREEN + "X" + ANSI_RESET + ",");
            else if (i == 1)
                result.append(ANSI_RED + "X" + ANSI_RESET + ",");
            else if (i == 2)
                result.append(ANSI_YELLOW + "X" + ANSI_RESET + ",");
            else if (i == 3)
                result.append(ANSI_PINK + "X" + ANSI_RESET + ",");
            else if (i == 4)
                result.append(ANSI_BLUE + "X" + ANSI_RESET + "]");
        }
        return result.toString();
    }

    /**
     * @param wizards the available wizards.
     * @return the string with the available wizards.
     */
    public static String printAvailableWizards(List<Wizard> wizards) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < wizards.size(); i++) {
            result.append(wizards.get(i));
            if (i != wizards.size() - 1)
                result.append(", ");
            else
                result.append("]");
        }
        return result.toString();
    }

}
