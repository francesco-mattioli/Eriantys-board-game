package it.polimi.ingsw.triton.launcher.server;

import it.polimi.ingsw.triton.launcher.client.cli.Cli;
import it.polimi.ingsw.triton.launcher.server.controller.Controller;
import it.polimi.ingsw.triton.launcher.server.model.game.ExpertGame;
import it.polimi.ingsw.triton.launcher.server.model.game.Game;
import it.polimi.ingsw.triton.launcher.server.model.game.GameMode;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;


public class Server {
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static int PORT;
    private Controller controller;
    private GameMode game;
    private List<VirtualView> waitingList;

    public Server(int PORT) {
        Server.PORT = PORT;
        this.game=new Game(2);
        this.controller=new Controller(game);
        this.waitingList=new ArrayList<>();
        LOGGER.info("Clients connected: " + 0);
    }

    public Controller getController() {
        return controller;
    }

    //MOVE IN GAME
    public boolean isUsernameValid(String username) {
        return username.length() != 0 && !username.equals(" ") && !username.equals(Cli.commandForCharacterCard);
    }



    /**
     * This method is called when the first player decides the max number of players.
     * If the number is not correct, it sends an error message to the first player and re-asks the number of players.
     * Otherwise, it creates the Game, the Controller, and sets the VirtualView of first player as an observer of the Controller.
     *
     * @param maxNumPlayers decided by the first player
     * @param username      of the first player
     */
    public void activateGame(String username, int maxNumPlayers, boolean expertMode) {
        if (!checkMaxNumPlayers(maxNumPlayers)) {
            waitingList.get(0).showErrorMessage(ErrorTypeID.WRONG_PLAYERS_NUMBER);
            waitingList.get(0).askNumPlayersAndGameMode();
            LOGGER.severe("Not valid number of players");
        } else {
            if(maxNumPlayers< waitingList.size()) {
                waitingList.get(waitingList.size() - 1).showErrorMessage(ErrorTypeID.FULL_LOBBY);
                game.getPlayers().remove(game.getPlayers().size() - 1);
                waitingList.get(waitingList.size()-1).removeObserver(controller);
                game.removeObserver(waitingList.get(waitingList.size()-1)); // DO ALSO FORT ISLANDS!!!!!!!!!!!!!!!!!
                waitingList.remove(waitingList.get(waitingList.size()-1));
            }
            if (expertMode)
                this.controller.setGame(new ExpertGame(game));
            controller.createTowerColorRequestMessage(waitingList.get(0).getUsername());
            LOGGER.info("Clients connected: " + waitingList.size());
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
        waitingList.add(new VirtualView(serveOneClient, username));
        controller.addVirtualView(waitingList.get(waitingList.size()-1));
        try {
            if (waitingList.size() == 1) {
                controller.addPlayer(username);
                waitingList.get(0).showLoginReply();
                waitingList.get(0).addObserver(controller);
                controller.addGameObserver(waitingList.get(0));
                LOGGER.info("First player has logged. Waiting for game mode and number of players...");
            } else if (waitingList.size() <= 3) {
                controller.addPlayer(username);
                waitingList.get(waitingList.size() - 1).showLoginReply();
                waitingList.get(waitingList.size() - 1).addObserver(controller);
                controller.addGameObserver(waitingList.get(waitingList.size() - 1));
                LOGGER.info("New player accepted");
                waitingList.get(waitingList.size() - 1).showGenericMessage("Game will start as soon as the first players chooses number of players...");
                if(waitingList.size()==2)
                    waitingList.get(0).askNumPlayersAndGameMode();
            }else{
                waitingList.get(waitingList.size() - 1).showErrorMessage(ErrorTypeID.FULL_LOBBY);
                waitingList.remove(waitingList.size() - 1);
                controller.getVirtualViews().remove(waitingList.get(waitingList.size() - 1));
                LOGGER.severe("Player not accepted, lobby was already full");
                serveOneClient.close();
            }
        } catch (IllegalArgumentException e) {
            VirtualView virtualView = waitingList.get(waitingList.size() - 1);
            virtualView.showErrorMessage(ErrorTypeID.USERNAME_ALREADY_CHOSEN);
            waitingList.remove(virtualView);
            controller.getVirtualViews().remove(waitingList.get(waitingList.size() - 1));
            LOGGER.severe("Player not accepted, username already chosen");
            LOGGER.info("Clients connected: " + this.waitingList.size());
        }
    }


    private boolean checkMaxNumPlayers(int num) {
        return (num == 2 || num == 3);
    }


    public void run() throws IOException {
        ServerSocket serverSocket = null;
        // if something goes wrong in the try, we close the serverSocket
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket connectionSocket = serverSocket.accept();
                // After 6 minutes (=360'000 secs) the connection with client is closed
                connectionSocket.setSoTimeout(360000);
                // The following thread will manage the socket that will be assigned to the Client
                new Thread(new ServeOneClient(connectionSocket, this)).start();
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

    public void disconnectPlayers() {
        waitingList.clear();

        controller.disconnectPlayers();
    }

    public synchronized void disconnect(ServeOneClient soc){
        Iterator<VirtualView> iterator = controller.getVirtualViews().iterator();
        while (iterator.hasNext()){
            VirtualView vv = iterator.next();
            if(vv.getServeOneClient() == soc && controller.getPlayersUsernames().contains(vv.getUsername())){
                disconnectPlayers();
                break;
            }
        }
    }
}
