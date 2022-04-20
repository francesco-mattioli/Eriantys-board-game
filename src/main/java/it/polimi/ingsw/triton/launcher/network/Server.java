package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.controller.Controller;
import it.polimi.ingsw.triton.launcher.network.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 2000;   //public or private?
    private Controller controller;

    public Server(Controller controller) throws IOException {
        ServerSocket serverSocket = null;
        this.controller=controller;
        // if something goes wrong in the try, we close the serverSocket
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket connectionSocket = serverSocket.accept();
                // The following thread will manage the socket that will be assigned to the Client
                new Thread(new ServeOneClient(connectionSocket,this)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void addClient(String nickname, ServeOneClient clientHandler){
        // TODO IMPLEMENT
    }

    public void removeClient(String nickname){
        // TODO IMPLEMENT
    }

    public void onReceive(Message message){
        // TODO IMPLEMENT
    }

    public void onDisconnect(ServeOneClient clientHandler){
        // TODO IMPLEMENT
    }
}
