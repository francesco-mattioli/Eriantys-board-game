package it.polimi.ingsw.triton.launcher;

import it.polimi.ingsw.triton.launcher.view.cli.Cli;

public class ClientApp {

    public static void main(String[] args) {

        /**
         * Default mode: GUI
         */
        boolean cliParam = true;

        /*for (String arg : args) {
            if (arg.equals("--cli")) {
                cliParam = true;
                break;
            }
        }*/

        if (cliParam) {
            Cli cli = new Cli();
            cli.start();
        } else {
            System.out.println("Qui va il metodo per lanciare GUI");
            //LAUNCH GUI
        }
    }
}
