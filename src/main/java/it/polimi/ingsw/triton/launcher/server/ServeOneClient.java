package it.polimi.ingsw.triton.launcher.server;

import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.GameModeReply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.LoginRequest;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.PlayersNumberReply;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;

import static it.polimi.ingsw.triton.launcher.server.Server.LOGGER;

/**
 * Each player must be treated as a thread
 */
public class  ServeOneClient implements Runnable {
    /**
     * input stream for receiving messages from the client
     * output stream for sending messages to the client
     */
    private final Socket socket;
    private final Server server;
    private ObjectInputStream inSocket;
    private ObjectOutputStream outSocket;
    private boolean active = true;

    public ServeOneClient(Socket socketClient, Server server) {
        this.socket = socketClient;
        this.server = server;
        try {
            outSocket = new ObjectOutputStream(socketClient.getOutputStream());
            inSocket = new ObjectInputStream(socketClient.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        handleConnection();
    }

    /**
     * If the message is LoginRequest, GameModeReply or PlayersNumberReply, it is handled as a particular message.
     * This is because the Game has not been instantiated yet.
     * Otherwise, it notifies the VirtualView that manages the message and interact with the Controller.
     * In this case, the Game has already been instantiated.
     */
    public void handleConnection() {
        try {
            while (isActive()) {
                ClientMessage message = (ClientMessage)inSocket.readObject();
                if (message instanceof LoginRequest) {
                    server.lobby(this, message.getSenderUsername());
                }
                else if (message instanceof GameModeReply) {
                    server.setGameMode(message.getSenderUsername(),((GameModeReply) message).isExpertMode());
                }
                else if (message instanceof PlayersNumberReply) {
                    server.activateGame(message.getSenderUsername(),((PlayersNumberReply) message).getPlayersNumber());
                }
                else {
                    server.getController().getVirtualViewByUsername(message.getSenderUsername()).notify(message);
                }
                LOGGER.info("Received: " + message.getClass().getSimpleName());
            }
        }catch (SocketException e){
            LOGGER.severe("Cannot receive message because closed");
            server.removeDisconnectedPlayers();
            close();
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error! " + e.getMessage());
            LOGGER.severe(e.getMessage());
        } finally {
            close();
        }
    }

    /**
     * This method is called by VirtualView to send messages to the Client.
     *
     * @param message the message
     */
    public synchronized void sendMessage(Message message) {
        try {
            outSocket.reset();
            outSocket.writeObject(message);
            LOGGER.info("Sent: "+message.getClass().getSimpleName());
            outSocket.flush();
        } catch (SocketException e) {
            LOGGER.severe("Cannot send message because disconnected");
            close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return active;
    }

    /**
     * If the connection crashes, it closes the socket.
     */
    public void close() {
        active = false;
        try {
            socket.close();
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
