package it.polimi.ingsw.triton.launcher.server;

import it.polimi.ingsw.triton.launcher.server.Server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(50535);
            server.run();
            Server.LOGGER.info("Server listening on port " + Server.PORT);
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
