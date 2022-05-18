package it.polimi.ingsw.triton.launcher.server;

import it.polimi.ingsw.triton.launcher.server.controller.Controller;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * This class utilizes the Singleton pattern; It must be instantiated only once.
 */
public class Server {

    /**
     * The constant LOGGER for printing information on the terminal.
     */
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    /**
     * This variable is necessary to implement the Singleton Pattern.
     */
    private static Server instance;
    /**
     * The port is initialized by ServerApp.
     */
    private final int port;
    private final List<VirtualView> waitingList;
    /**
     * Server need to have a reference to Controller, so it can create Virtual Views and Game.
     */
    private Controller controller;
    /**
     * Listening is true, so the server can accept new connections with clients
     */
    private boolean listening = true;
    private boolean starting = false;
    private boolean started = false;
    private boolean expertMode = false;

    private Server(int port) {
        this.port = port;
        this.controller = new Controller();
        this.waitingList = new ArrayList<>();
        LOGGER.info("Clients connected: " + 0);
    }

    /**
     * This method realizes the Singleton Pattern in order the instantiates the Server Class only once.
     *
     * @param port the port passed by ServerApp, the latter instantiates Server.
     * @return the Server instance
     */
    public static Server instance(int port) {
        if (instance == null)
            instance = new Server(port);
        return instance;

    }

    /**
     * This method is called when the first player decides the max number of players.
     * If the number is not correct, it sends an error message to the first player and re-asks the number of players.
     * Otherwise, it creates the Game, the Controller, and sets the VirtualView of first player as an observer of the Controller.
     *
     * @param username      of the first player
     * @param maxNumPlayers decided by the first player
     * @param expertMode    the expert mode
     */
    public synchronized void activateGame(String username, int maxNumPlayers, boolean expertMode) {
        if (!isNumberOfPlayersValid(maxNumPlayers)) {
            askFirstPlayerGameSettingsAgain();
        } else {
            starting = true;
            this.expertMode = expertMode;
            if (maxNumPlayers <= controller.getVirtualViews().size()) {
                controller.setMaxNumberOfGamePlayers(maxNumPlayers);
                if(maxNumPlayers == controller.getVirtualViews().size() && !started) {
                    started = true;
                    beginGame(expertMode);
                }
                else
                    removePlayer(waitingList.get(waitingList.size() - 1));
            } else
                waitingList.get(0).showGenericMessage("Waiting for " + (maxNumPlayers - waitingList.size()) + " to connect...");

            LOGGER.info("Clients connected: " + waitingList.size());
        }
        }

    private void askFirstPlayerGameSettingsAgain(){
        waitingList.get(0).showErrorMessage(ErrorTypeID.WRONG_PLAYERS_NUMBER);
        LOGGER.severe("Not valid number of players");
        waitingList.get(0).askNumPlayersAndGameMode();

    }

    private void removePlayer(VirtualView virtualView){
        virtualView.showErrorMessage(ErrorTypeID.FULL_LOBBY);
        controller.removeGamePlayer(controller.getCurrentNumberOfGamePlayers() - 1);
        removeObserverRelationships(virtualView);
    }

    private void removeObserverRelationships(VirtualView virtualView){
        controller.removeGameObserver(virtualView);
        virtualView.removeObserver(controller);
    }

    private void addObserverRelationships(VirtualView virtualView){
        controller.addGameObserver(virtualView);
        virtualView.addObserver(controller);
    }





    /**
     * Create the Virtual View for the Client, then it adds it in the waiting list to manage connections.
     * If the number of players has reached the maximum, no more users can be connected, thus it removes the player from Waiting List.
     * IllegalArgumentException is thrown when a user enters a username that has already been chosen.
     * @param serveOneClient the serve one client
     * @param username       the username
     */
    public synchronized void lobby(ServeOneClient serveOneClient, String username) {
        if(started && controller.getGameState() == GameState.END)
            resetServer();
        VirtualView lastVirtualView = new VirtualView(serveOneClient, username);
        waitingList.add(lastVirtualView);
        controller.addVirtualView(lastVirtualView);
        try {
            if (waitingList.size() <= 3 && !started) {
                addObserverRelationships(lastVirtualView);
                addPlayerAndSendSuccessMessage(lastVirtualView,username);
                askSettingsIfMinimumNumberOfPlayersIsReached();
                beginGameIfSettingsAreSet();
            } else
                sendErrorMessageAndRemoveFromWaitingList(lastVirtualView);
        } catch (IllegalArgumentException e) {
            removeFromLobbyBecauseUsernameAlreadyChosen(lastVirtualView);
        }
    }

    /**
     * Sends an error message and removes user's Virtual View from waiting list and Controller.
     * @param virtualView to send the message and remove
     */
    private void removeFromLobbyBecauseUsernameAlreadyChosen(VirtualView virtualView) {
        virtualView.showErrorMessage(ErrorTypeID.USERNAME_ALREADY_CHOSEN);
        removeObserverRelationships(virtualView);
        waitingList.remove(virtualView);
        controller.getVirtualViews().remove(virtualView);
        LOGGER.severe("Player not accepted, username already chosen");
        LOGGER.info("Clients connected: " + waitingList.size());
    }

    /**
     * If the number of players connected is 3 and the first player chose the mode and the number of players, the game must start.
     * In order to start the game, it is necessary to ask the first player the Tower Color.
     * Eventually, the waiting list has to be cleared up, because there are no more users that can play the game.
     */
    private void beginGameIfSettingsAreSet() {
        if (waitingList.size() == 3 && starting && !started) {
            started = true;
            beginGame(expertMode);
        }
    }

    private void sendErrorMessageAndRemoveFromWaitingList(VirtualView virtualView) {
        //--- Send a Full Lobby Error Message to the last user who tried to connect.
        virtualView.showErrorMessage(ErrorTypeID.FULL_LOBBY);
        //--- Remove the Virtual View from the waiting list and Controller, because the user cannot connect.
        waitingList.remove(virtualView);
        LOGGER.severe("Player not accepted, lobby was already full");
    }

    /**
     *  If the least number of players for starting the Game are connected, ask the first player number of players and game mode.
     *
     */
    private void askSettingsIfMinimumNumberOfPlayersIsReached() {
        if (waitingList.size() == 2)
            waitingList.get(0).askNumPlayersAndGameMode();
    }

    /**
     * If the just accepted player is not the first and the first player has not chosen the number of student,
     * this method sends a message saying that the Game will start soon.
     * @param virtualView for sending a Login Reply Message when the addGamePlayer method did not throw an exception.
     * @param username of the player to add in the Game
     * @throws IllegalArgumentException when the username is already chosen
     */
    private void addPlayerAndSendSuccessMessage(VirtualView virtualView, String username) throws IllegalArgumentException {
        controller.addGamePlayer(username);
        virtualView.showLoginReply();
        LOGGER.info("New player accepted");
        if (waitingList.size() > 1 && !starting)
            virtualView.showGenericMessage("Game will start as soon as the first player chooses number of players...");
    }


    /**
     * If something goes wrong in the try branch, it closes the Server Socket.
     * It creates a Server Socket setting a timeout of 6 minutes (=360'000 secs). In other words, the connection with client is closed after 6 minutes.
     * Then, it instantiates and starts a thread to manage the Server Socket that will be assigned to the Client.
     *
     * @throws IOException the io exception
     */
    public void run() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (listening) {
                try {
                    Socket connectionSocket = serverSocket.accept();
                    connectionSocket.setSoTimeout(360000);
                    new Thread(new ServeOneClient(connectionSocket, this)).start();
                } catch (IOException e) {
                    listening = false;
                }
            }
        }
    }


    /**
     * Disconnect player.
     *
     * @param virtualView the virtual view of the player
     */
    public synchronized void disconnectPlayer(VirtualView virtualView) {
        resetPlayer(virtualView);
        controller.disconnectPlayer(virtualView);
        if (!started && starting)
            beginGame(expertMode);
    }

    /**
     * Reset player.
     *
     * @param virtualView the vv
     */
    public void resetPlayer(VirtualView virtualView) {
        virtualView.removeObserver(controller);
        controller.removeGameObserver(virtualView);
        waitingList.remove(virtualView);
    }

    /**
     * Disconnect.
     *
     * @param soc the soc
     */
    public synchronized void disconnect(ServeOneClient soc) {
        VirtualView virtualView = null;
        if (!controller.getVirtualViews().isEmpty()) {
            for (VirtualView vv : controller.getVirtualViews()) {
                if (vv.getServeOneClient().equals(soc)) {
                    virtualView = vv;
                }
            }
            if ((controller.getGameState() == GameState.LOGIN && controller.getVirtualViews().indexOf(virtualView) != 0 && virtualView != null) || (controller.getGameState() == GameState.END && virtualView != null) || controller.getVirtualViews().indexOf(virtualView) >= controller.getMaxNumberOfGamePlayers()) {
                disconnectPlayer(virtualView);
            } else if (virtualView != null){
                // The first player disconnected or the game is in not in LOGIN STATE
                disconnectAllPlayers();
            }
        } else if (controller.getGameState() != GameState.LOGIN && controller.getGameState() != GameState.END) {
            disconnectAllPlayers();
        }
    }

    /**
     * Disconnect all players.
     */
    public synchronized void disconnectAllPlayers() {
        controller.disconnectAllPlayers();
        resetServer();
    }

    /**
     * Reset server.
     */
    public void resetServer() {
        waitingList.clear();
        started = false;
        starting = false;
        controller = new Controller();
        LOGGER.info("Reset server");
    }

    // PRIVATE HELPER METHODS
    private boolean isNumberOfPlayersValid(int num) {
        return (num == 2 || num == 3);
    }


    // GETTERS

    /**
     * Gets controller.
     *
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Begin game.
     *
     * @param expertMode the expert mode
     */
    public void beginGame(boolean expertMode) {
        if (expertMode)
            controller.makeGameModeExpert();
        controller.setGameState(GameState.SETUP);
        if (starting)
            controller.createTowerColorRequestMessage(controller.getVirtualViews().get(0).getUsername());
        waitingList.clear();
    }
}
