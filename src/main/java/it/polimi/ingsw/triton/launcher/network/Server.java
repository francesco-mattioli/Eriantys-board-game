package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.controller.Controller;
import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.view.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class Server {
    public static int PORT;
    private int numOfClients=0;
    private Controller controller;
    private ArrayList<VirtualView> virtualViews= new ArrayList<>();
    private Semaphore semaphore = new Semaphore(1);
    private int maxNumPlayers = 0;


    public Server(int PORT) {
        Server.PORT =PORT;
    }

    public boolean isUsernameValid(String username){ // to add check server name if it is useful
        return username.length() != 0;
    }

    private boolean checkMaxNumPlayers(int num){
        return (num == 2 || num == 3);
    }

    public void activateGame(int maxNumPlayers, String username){
        if(!checkMaxNumPlayers(maxNumPlayers)){
            virtualViews.get(0).sendErrorMessage("The number of players must be 2 or 3!");
            virtualViews.get(0).askNumOfPlayersAndMode();
        }
        else{
            this.maxNumPlayers = maxNumPlayers;
            Game game = new Game(maxNumPlayers);
            this.controller = new Controller(game);
            controller.addPlayer(username);
            semaphore.release();
            numOfClients++;
            virtualViews.get(0).addObserver(controller);
            controller.addGameObserver(virtualViews.get(0));
        }

    }

    public synchronized void lobby(ServeOneClient serveOneClient,String username) {
        semaphore.acquireUninterruptibly();
        if(numOfClients == 0 && isUsernameValid(username)){
            virtualViews.add(new VirtualView(serveOneClient, username));
            virtualViews.get(0).askNumOfPlayersAndMode();
        }
        else if (numOfClients <= maxNumPlayers && isUsernameValid(username)){
            virtualViews.add(new VirtualView(serveOneClient, username));
            controller.addPlayer(username);
            semaphore.release();
            numOfClients++;
            virtualViews.get(numOfClients).addObserver(controller);
            controller.addGameObserver(virtualViews.get(maxNumPlayers));
        }
        else if (!isUsernameValid(username)){
            VirtualView virtualView = new VirtualView(serveOneClient, username);
            virtualView.sendErrorMessage("Username is not correct");
        }
        else{
            VirtualView virtualView = new VirtualView(serveOneClient, username);
            virtualView.sendErrorMessage("The connection can't be open!");
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
}
