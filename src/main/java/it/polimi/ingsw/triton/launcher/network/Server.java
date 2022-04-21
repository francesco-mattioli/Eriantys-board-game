package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.controller.Controller;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.view.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    public static int PORT;
    private int numOfClients=0;
    private Controller controller;
    private ArrayList<VirtualView> virtualViews= new ArrayList<>();


    public Server(int PORT) {
        Server.PORT =PORT;
    }

    public boolean isUsernameValid(String username){ // to add check server name if it is useful
        return username.length() != 0;
    }

    public synchronized void lobby(ServeOneClient serveOneClient,String username) {
        if (numOfClients == 0) {
            virtualViews.add(new VirtualView(serveOneClient));
            virtualViews.get(0).askNumOfPlayersAndMode();
        }



    }

    public void run() throws IOException {
        ServerSocket serverSocket = null;
        // if something goes wrong in the try, we close the serverSocket
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket connectionSocket = serverSocket.accept();
                // The following thread will manage the socket that will be assigned to the Client
                new Thread(new ServeOneClient(connectionSocket,this,numOfClients)).start();
                numOfClients++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert serverSocket != null;
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
