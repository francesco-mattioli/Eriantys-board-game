package it.polimi.ingsw.triton.launcher.client.cli;

public class Utility {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PINK = "\u001B[35m";

        public static String printColoredStudents(int[] students){
        String results = "";
        for (int i = 0; i < students.length; i++){
            if (i == 0)
                results += ("students=[ " + ANSI_YELLOW + students[i] + ANSI_RESET + ",");
            else if (i == 1)
                results += (" " + ANSI_BLUE + students[i] + ANSI_RESET + ",");
            else if (i == 2)
                results += (" " + ANSI_GREEN + students[i] + ANSI_RESET + ",");
            else if (i == 3)
                results += (" " + ANSI_RED + students[i] + ANSI_RESET + ",");
            else if (i == 4)
                results += (" " + ANSI_PINK + students[i] + ANSI_RESET + " ]");
        }
        return results;

    }
}
