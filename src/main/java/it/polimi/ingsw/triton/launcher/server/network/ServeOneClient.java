package it.polimi.ingsw.triton.launcher.server.network;

import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages.LoginRequest;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages.PlayersNumberAndGameModeReply;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

import static it.polimi.ingsw.triton.launcher.server.network.Server.LOGGER;

/**
 * Each player must be treated as a thread
 */
public class ServeOneClient implements Runnable {
    private static final String AT_PORT = " at port: ";
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
            LOGGER.severe("Cannot create input and output stream socket!");
        }
    }

    @Override
    public void run() {
        receiveMessage();
    }

    /**
     * If the message is LoginRequest, GameModeReply or PlayersNumberAndGameModeReply, it is handled as a particular message.
     * This is because the Game has not been instantiated yet.
     * Otherwise, it notifies the VirtualView that manages the message and interact with the Controller.
     * In this case, the Game has already been instantiated.
     */
    public void receiveMessage() {
        try {
            while (isActive()) {
                ClientMessage message = (ClientMessage) inSocket.readObject();
                if (message instanceof LoginRequest) {
                    server.lobby(this, ((LoginRequest) message).getUsername());
                } else if (message instanceof PlayersNumberAndGameModeReply) {
                    server.activateGame(this, ((PlayersNumberAndGameModeReply) message).getPlayersNumber(), ((PlayersNumberAndGameModeReply) message).isExpertMode());
                } else {
                    server.notifyVirtualView(this, message);
                }
                LOGGER.info("Received: " + message.getClass().getSimpleName() + AT_PORT + socket.getPort());
            }
        } catch (SocketTimeoutException e) {
            LOGGER.severe("Cannot receive message at port " + socket.getPort() + " because connection with client dropped! Disconnecting players...");
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            LOGGER.severe(e.getMessage() + " error receiving a message at port: " + socket.getPort());
        } finally {
            server.disconnect(this);
            close();
        }
    }

    /**
     * This method is called by VirtualView for sending messages to Client.
     *
     * @param message the message
     */
    public synchronized void sendMessage(Message message) {
        try {
            outSocket.reset();
            outSocket.writeObject(message);
            LOGGER.info("Sent: " + message.getClass().getSimpleName() + AT_PORT + socket.getPort());
            outSocket.flush();
        } catch (SocketTimeoutException e) {
            LOGGER.severe("Connection dropped due to timeout of socket at port: " + socket.getPort());
            close();
        } catch (SocketException e) {
            LOGGER.severe("Cannot send message " + message.getClass().getSimpleName() + AT_PORT + socket.getPort() + " because connection with client dropped!");
            close();
        } catch (IOException e) {
            LOGGER.severe("Connection dropped due to IOException at port: " + socket.getPort());
            close();
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
            LOGGER.severe("Cannot close the socket!");
        }
    }
}
