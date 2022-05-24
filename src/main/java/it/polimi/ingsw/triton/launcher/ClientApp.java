package it.polimi.ingsw.triton.launcher;

import it.polimi.ingsw.triton.launcher.client.cli.Cli;
import it.polimi.ingsw.triton.launcher.client.gui.GuiApplication;
import javafx.application.Application;

public class ClientApp {

    public static void main(String[] args) {
        /**
         * Default mode: GUI
         */
        boolean cliParam = false;

        for (String arg : args) {
            if (arg.equals("--cli")) {
                cliParam = true;
                break;
            }
        }

        if (cliParam) {
            Cli cli = new Cli();
            cli.start();
        } else {
            Application.launch(GuiApplication.class);
        }
    }
}
