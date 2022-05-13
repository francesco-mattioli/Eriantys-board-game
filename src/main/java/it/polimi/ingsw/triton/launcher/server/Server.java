package it.polimi.ingsw.triton.launcher.server;

import it.polimi.ingsw.triton.launcher.client.cli.Cli;
import it.polimi.ingsw.triton.launcher.server.controller.Controller;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
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
    private static Server instance;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static int PORT;
    private Controller controller;
    private GameMode game;
    private final List<VirtualView> waitingList;
    private boolean starting=false;
    private boolean activated=false;
    private boolean setNumPlayers = false;

    public static Server instance(int PORT){
        if(instance == null)
            instance=new Server(PORT);
        return instance;

    }

    private Server(int PORT) {
        Server.PORT=PORT;
        this.game=Game.instance(3);
        this.controller=new Controller(game);
        this.waitingList=new ArrayList<>();
        LOGGER.info("Clients connected: " + 0);
    }


    /**
     * This method is called when the first player decides the max number of players.
     * If the number is not correct, it sends an error message to the first player and re-asks the number of players.
     * Otherwise, it creates the Game, the Controller, and sets the VirtualView of first player as an observer of the Controller.
     *
     * @param maxNumPlayers decided by the first player
     * @param username      of the first player
     */
    public synchronized void activateGame(String username, int maxNumPlayers, boolean expertMode) {
        if (!isNumberOfPlayersValid(maxNumPlayers)) {
            waitingList.get(0).showErrorMessage(ErrorTypeID.WRONG_PLAYERS_NUMBER);
            waitingList.get(0).askNumPlayersAndGameMode();
            LOGGER.severe("Not valid number of players");
        } else {
            if(maxNumPlayers< waitingList.size()) {
                game.setMaxNumberOfPlayers(maxNumPlayers);
                setNumPlayers = true;
                VirtualView virtualViewToRemove = waitingList.get(waitingList.size()-1);
                virtualViewToRemove.showErrorMessage(ErrorTypeID.FULL_LOBBY);
                game.getPlayers().remove(game.getPlayers().size() - 1);
                virtualViewToRemove.removeObserver(controller);
                game.removeObserver(virtualViewToRemove); // DO ALSO FORT ISLANDS!!!!!!!!!!!!!!!!!
                //!!!! controller.getVirtualViews().remove(virtualViewToRemove);
                //waitingList.remove(waitingList.get(waitingList.size()-1));
                //beginGame(expertMode);
            }else if(maxNumPlayers==controller.getVirtualViews().size()){
                game.setMaxNumberOfPlayers(maxNumPlayers);
                setNumPlayers = true;
                if(!activated) {
                    beginGame(expertMode);
                    activated = true;
                }
            }else{
                waitingList.get(0).showGenericMessage("Waiting for "+(maxNumPlayers-waitingList.size())+" to connect...");
                starting=true;
            }

            LOGGER.info("Clients connected: " + waitingList.size());
        }
   }


    /**
     * If it's the first player
     *
     * @param serveOneClient
     * @param username
     */
    public synchronized void lobby(ServeOneClient serveOneClient, String username) {
        waitingList.add(new VirtualView(serveOneClient, username));
        controller.addVirtualView(waitingList.get(waitingList.size()-1));
        try {
            if (waitingList.size() <= 3) {
                // --- Adding observer/observable relations
                controller.addGameObserver(waitingList.get(waitingList.size() - 1));
                waitingList.get(waitingList.size() - 1).addObserver(controller);
                // --- end
                game.addPlayer(username);
                waitingList.get(waitingList.size() - 1).showLoginReply();
                LOGGER.info("New player accepted");
                if(waitingList.size()>1 && !starting)
                    waitingList.get(waitingList.size() - 1).showGenericMessage("Game will start as soon as the first player chooses number of players...");
                if(waitingList.size()==2)
                    waitingList.get(0).askNumPlayersAndGameMode();
                if(waitingList.size()==3 && starting) {
                    controller.createTowerColorRequestMessage(waitingList.get(0).getUsername());
                    waitingList.clear();
                }
            }else{
                waitingList.get(waitingList.size() - 1).showErrorMessage(ErrorTypeID.FULL_LOBBY);
                waitingList.remove(waitingList.size() - 1);
                controller.getVirtualViews().remove(waitingList.get(waitingList.size() - 1));
                LOGGER.severe("Player not accepted, lobby was already full");
                serveOneClient.close();
            }
        } catch (IllegalArgumentException e) {
            // --- Undoing observer/observable relations
            controller.removeGameObserver(waitingList.get(waitingList.size() - 1));
            waitingList.get(waitingList.size() - 1).removeObserver(controller);
            // --- end
            VirtualView virtualView = waitingList.get(waitingList.size() - 1);
            virtualView.showErrorMessage(ErrorTypeID.USERNAME_ALREADY_CHOSEN);
            waitingList.remove(virtualView);
            controller.getVirtualViews().remove(virtualView);
            LOGGER.severe("Player not accepted, username already chosen");
            LOGGER.info("Clients connected: " + this.waitingList.size());
        }
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

    public synchronized void disconnectPlayer(VirtualView vv){
        /*controller.disconnectPlayer(soc);
        if(controller.getGameState() != GameState.LOGIN)
            resetServer();
        else if(controller.getVirtualViews().size() > 0)
            controller.createTowerColorRequestMessage(controller.getVirtualViews().get(0).getUsername());*/
        resetPlayer(vv);
        controller.disconnectPlayer(vv);
        if(!activated && setNumPlayers)
            beginGame(true);
    }

    public void resetPlayer(VirtualView vv){
        vv.removeObserver(controller);
        game.removeObserver(vv); // DO ALSO FORT ISLANDS!!!!!!!!!!!!!!!!!
        waitingList.remove(vv);
    }

    public synchronized void disconnect(ServeOneClient soc){
        VirtualView virtualView = null;
        if(controller.getVirtualViews().size() > 0){
            for(VirtualView vv: controller.getVirtualViews()){
                if(vv.getServeOneClient().equals(soc)){
                    virtualView = vv;
                }
            }
            if(controller.getGameState() == GameState.LOGIN && !(controller.getVirtualViews().indexOf(virtualView) == 0) && virtualView != null){
                disconnectPlayer(virtualView);
                //beginGame(true);
            }else{
                // The first player disconnected or the game is in not in LOGIN STATE
                disconnectAllPlayers();
            }
        }
        else if(controller.getGameState() != GameState.LOGIN){
            disconnectAllPlayers();
        }
    }

    public synchronized void disconnectAllPlayers(){
        controller.disconnectAllPlayers();
        resetServer();
    }

    public void resetServer(){
        waitingList.clear();
        activated = false;
        setNumPlayers = false;
        game=Game.instance(3);
        controller=new Controller(game);
    }

    // PRIVATE HELPER METHODS
    private boolean isNumberOfPlayersValid(int num) {
        return (num == 2 || num == 3);
    }

    // GETTERS
    public Controller getController() {
        return controller;
    }

    public void beginGame(boolean expertMode){
        if (expertMode)
            this.controller.setGame(new ExpertGame(game));
        game.setGameState(GameState.SETUP);
        if(setNumPlayers)
            controller.createTowerColorRequestMessage(controller.getVirtualViews().get(0).getUsername());
        waitingList.clear();
    }
}
