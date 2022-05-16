package it.polimi.ingsw.triton.launcher;

import it.polimi.ingsw.triton.launcher.server.Server;
import java.io.IOException;


public class ServerApp {
    public static void main(String[] args) {
        Server server;
        int port = 50535;

        /**
         * This for loop read the port number when the user enters a certain port number followed by the command --port.
         * If an invalid input is entered, the program throws a NumberFormatException and starts the server with default port.
         */
        try {
            for(int i = 0; i< args.length; i++){
                if(args.length >= 2 && args[i].equals("--port"))
                    port = Integer.parseInt(args[i + 1]);
            }
        } catch (NumberFormatException e) {
            Server.LOGGER.severe("Invalid port. Using default port");
        }

        /**
         * Instantiates the server using Singleton Pattern, then runs it.
         * If it cannot be run, it throws an exception and the program ends.
         */
        server = Server.instance(port);
        try {
            server.run();
        } catch (IOException e) {
            Server.LOGGER.severe("Impossible to initialize the server: " + e.getMessage() + "!");
        }
        Server.LOGGER.info("Server listening on port " + port);
    }
}
