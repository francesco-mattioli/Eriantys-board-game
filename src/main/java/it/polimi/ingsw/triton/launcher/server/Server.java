package it.polimi.ingsw.triton.launcher.server;

import it.polimi.ingsw.triton.launcher.server.controller.Controller;
import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.FullLobbyMessage;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;


public class Server {
    public static int PORT;
    private int numOfClients;
    private Controller controller;
    private final Semaphore semaphore = new Semaphore(1);
    private int maxNumPlayers = 0;
    private boolean expertMode;
    private VirtualView firstPlayerVirtualView;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server(int PORT) {
        Server.PORT = PORT;
        this.numOfClients=0;
        LOGGER.info("Clients connected: " + this.numOfClients);
    }

    public Controller getController() {
        return controller;
    }

    public boolean isUsernameValid(String username) { // to add check server name if it is useful
        return username.length() != 0;
    }

    private boolean checkMaxNumPlayers(int num) {
        return (num == 2 || num == 3);
    }

    public void setGameMode(String username, boolean expertMode){
        this.expertMode=expertMode;
        LOGGER.severe("Game mode was set");
    }



    /**
     * This method is called when the first player decides the max number of players.
     * If the number is not correct, it sends an error message to the first player and re-asks the number of players.
     * Otherwise, it creates the Game, the Controller, and sets the VirtualView of first player as an observer of the Controller.
     *
     * @param maxNumPlayers decided by the first player
     * @param username      of the first player
     */
    public void activateGame(String username,int maxNumPlayers) {
        if (!checkMaxNumPlayers(maxNumPlayers)) {
            firstPlayerVirtualView.showErrorMessage(ErrorTypeID.WRONG_PLAYERS_NUMBER);
            firstPlayerVirtualView.askNumOfPlayers();
            LOGGER.severe("Not valid number of players");
        } else {
            this.maxNumPlayers = maxNumPlayers;
            this.controller = new Controller(new Game(maxNumPlayers));
            controller.getVirtualViews().add(firstPlayerVirtualView);
            controller.addPlayer(username);
            numOfClients++;
            controller.getVirtualViewByUsername(username).addObserver(controller);
            controller.addGameObserver(controller.getVirtualViewByUsername(username));
            semaphore.release();
            LOGGER.info("The game was instanced for " + maxNumPlayers + " players");
            LOGGER.info("Clients connected: " + this.numOfClients);
        }

    }

    /**
     * This method adds players to the game
     * We use a semaphore that locks the execution until the first player has chosen the number of players
     * numOfClients is very important to establish which player is the first one, so he has to choose the number of players
     *
     * @param serveOneClient
     * @param username
     */
    public synchronized void lobby(ServeOneClient serveOneClient, String username) {
        semaphore.acquireUninterruptibly();
        //if the player is the first one, we need to wait that he has chosen the number of players
        if (numOfClients == 0 && isUsernameValid(username)) {
            firstPlayerVirtualView=new VirtualView(serveOneClient, username);
            firstPlayerVirtualView.askGameMode();
            firstPlayerVirtualView.askNumOfPlayers();
            LOGGER.info("First player has logged. Waiting for game mode and number of players...");
        }
        //in this case, the player can be added to the game. His virtualview cam be created and added to the ArrayList
        else if (numOfClients <= maxNumPlayers && isUsernameValid(username)) {
            try {
                controller.addPlayer(username);
                controller.getVirtualViews().add(new VirtualView(serveOneClient, username));
                controller.getVirtualViewByUsername(username).addObserver(controller);
                controller.addGameObserver(controller.getVirtualViewByUsername(username));
                numOfClients++;
                LOGGER.info("New player accepted");
                LOGGER.info("Clients connected: " + this.numOfClients);
                //in this case, the player added is the last one, so after this the game can be started and next players will be rejected
                if (numOfClients == maxNumPlayers) {
                    controller.createTowerColorRequestMessage(controller.getVirtualViews().get(0).getUsername());
                    LOGGER.info("Last player accepted. Lobby is now full");
                }
            } catch (IllegalArgumentException e) {
                VirtualView virtualView = new VirtualView(serveOneClient, username);
                virtualView.showErrorMessage(ErrorTypeID.USERNAME_ALREADY_CHOSEN);
                LOGGER.severe("Player not accepted, username already chosen");
                LOGGER.info("Clients connected: " + this.numOfClients);
            } finally {
                semaphore.release();
            }
        }
        //in this case, the username is not valid
        else if (!isUsernameValid(username)) {
            VirtualView virtualView = new VirtualView(serveOneClient, username);
            virtualView.showErrorMessage(ErrorTypeID.FORBIDDEN_USERNAME);
            LOGGER.severe("Player not accepted, username not available");
            LOGGER.info("Clients connected: " + this.numOfClients);
        }
        //in this case, lobby is already full so an other player cannot be added
        else {
            VirtualView virtualView = new VirtualView(serveOneClient, username);
            virtualView.showErrorMessage(ErrorTypeID.FULL_LOBBY);
            LOGGER.severe("Player not accepted, lobby was already full");
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
                new Thread(new ServeOneClient(connectionSocket, this, numOfClients)).start();
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
