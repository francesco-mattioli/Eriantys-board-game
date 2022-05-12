package it.polimi.ingsw.triton.launcher.utils;

public class Utility {
    private static final Utility instance = new Utility();

    private Utility(){
    }

    public static Utility getInstance(){
        return instance;
    }

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
                results += ("students=[ " + ANSI_GREEN + students[i] + ANSI_RESET + ",");
            else if (i == 1)
                results += (" " + ANSI_RED + students[i] + ANSI_RESET + ",");
            else if (i == 2)
                results += (" " + ANSI_YELLOW + students[i] + ANSI_RESET + ",");
            else if (i == 3)
                results += (" " + ANSI_PINK + students[i] + ANSI_RESET + ",");
            else if (i == 4)
                results += (" " + ANSI_BLUE + students[i] + ANSI_RESET + " ]");
        }
        return results;
    }

    public static String printColoredProfessorsOnTable(String[] professors){
        String results = "[";
        for(int i = 0; i < professors.length; i++){
            if (i == 0 && professors[i] == null)
                results += (ANSI_GREEN + "X" + ANSI_RESET + ",");
            else if (i == 1 && professors[i] == null)
                results += (ANSI_RED + "X" + ANSI_RESET + ",");
            else if (i == 2 && professors[i] == null)
                results += (ANSI_YELLOW + "X" + ANSI_RESET + ",");
            else if (i == 3 && professors[i] == null)
                results += (ANSI_PINK + "X" + ANSI_RESET + ",");
            else if (i == 4 && professors[i] == null)
                results += (ANSI_BLUE + "X" + ANSI_RESET + "]");
            else if (i != 4 && professors[i] != null)
                results += ("_,");
            else if (i == 4)
                results += ("_]");
        }
        return results;
    }

    public static String printColoredProfessorsOnSchoolBoard(String[] professors, String owner){
        String results = "[";
        for(int i = 0; i < professors.length; i++){
            if((i != professors.length-1 && professors[i] == null) || (i != professors.length-1 && !professors[i].equals(owner)))
                results += "_,";
            else if((i == professors.length-1 && professors[i] == null) || (i == professors.length-1 && !professors[i].equals(owner)))
                results += "_]";
            else if(i == 0)
                results += ANSI_GREEN + "X" + ANSI_RESET + ",";
            else if(i == 1)
                results += ANSI_RED + "X" + ANSI_RESET + ",";
            else if(i == 2)
                results += ANSI_YELLOW + "X" + ANSI_RESET + ",";
            else if(i == 3)
                results += ANSI_PINK + "X" + ANSI_RESET + ",";
            else if(i == 4)
                results += ANSI_BLUE + "X" + ANSI_RESET + "]";
        }
        return results;
    }
}
