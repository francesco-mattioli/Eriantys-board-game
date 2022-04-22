package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.controller.Controller;
import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.network.message.FullLobbyMessage;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;
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

    public Controller getController() {
        return controller;
    }

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
            controller.getVirtualViews().get(0).sendErrorMessage("The number of players must be 2 or 3!");
            controller.getVirtualViews().get(0).askNumOfPlayersAndMode();
        }
        else{
            this.maxNumPlayers = maxNumPlayers;
            Game game = new Game(maxNumPlayers);
            this.controller = new Controller(game);
            controller.addPlayer(username);
            semaphore.release();
            numOfClients++;
            controller.getVirtualViews().get(0).addObserver(controller);
            controller.addGameObserver(controller.getVirtualViews().get(0));
        }

    }

    /**
     * This method adds players to the game
     * We use a semaphore that locks the execution until the first player has chosen the number of players
     * numOfClients is very important to establish which player is the first one, so he has to choose the number of players
     * @param serveOneClient
     * @param username
     */
    public synchronized void lobby(ServeOneClient serveOneClient,String username) {
        semaphore.acquireUninterruptibly();
        //if the player is the first one, we need to wait that he has chosen the number of players
        if(numOfClients == 0 && isUsernameValid(username)){
            controller.getVirtualViews().add(new VirtualView(serveOneClient, username));
            controller.getVirtualViews().get(0).askNumOfPlayersAndMode();
        }
        //in this case, the player can be added to the game. His virtualview cam be created and added to the ArrayList
        else if (numOfClients <= maxNumPlayers && isUsernameValid(username)){
            try{
                controller.addPlayer(username);
                controller.getVirtualViews().add(new VirtualView(serveOneClient, username));
                numOfClients++;
                controller.getVirtualViews().get(numOfClients).addObserver(controller);
                controller.addGameObserver(controller.getVirtualViews().get(maxNumPlayers));
                //in this case, the player added is the last one, so after this the game can be started and next players will be rejected
                if(numOfClients == maxNumPlayers)
                    controller.getVirtualViews().get(0).notify(new FullLobbyMessage(controller.getVirtualViews().get(0).getUsername()));
            }
            catch (IllegalArgumentException e){
                VirtualView virtualView = new VirtualView(serveOneClient, username);
                virtualView.sendErrorMessage("Username already chosen");
            }
            finally {
                semaphore.release();
            }
        }
        //in this case, the username is not valid
        else if (!isUsernameValid(username)){
            VirtualView virtualView = new VirtualView(serveOneClient, username);
            virtualView.sendErrorMessage("Username is not correct");
        }
        //in this case, lobby is already full so an other player cannot be added
        else{
            VirtualView virtualView = new VirtualView(serveOneClient, username);
            virtualView.sendErrorMessage("Lobby is full");
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
