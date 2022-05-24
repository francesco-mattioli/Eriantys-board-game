package it.polimi.ingsw.triton.launcher;

import it.polimi.ingsw.triton.launcher.client.cli.Cli;
import it.polimi.ingsw.triton.launcher.client.gui.GuiApplication;
import javafx.application.Application;

public class ClientApp {

    public static void main(String[] args) {
        /*
          Default mode: GUI
         */
        boolean cli = false;

        /*
         * This method sets the cli boolean true if the client enters as arguments --cli.
         */
        for (String arg : args) {
            if (arg.equals("--cli")) {
                cli = true;
                break;
            }
        }

        if (cli)
            new Cli().start();
        else
            Application.launch(GuiApplication.class);
    }
}
