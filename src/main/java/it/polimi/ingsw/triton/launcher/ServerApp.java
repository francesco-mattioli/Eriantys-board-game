package it.polimi.ingsw.triton.launcher;

import it.polimi.ingsw.triton.launcher.network.Server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(3000);
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
