package it.polimi.ingsw.triton.launcher.server;

import it.polimi.ingsw.triton.launcher.server.Server;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.GameModeReply;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.PlayersNumberReply;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

// Each player must be treated as a thread
public class ServeOneClient implements Runnable {
    private final Socket socket;
    private final Server server;
    private final int id;
    private final Object inputLock;
    private final Object outputLock;
    private ObjectInputStream inSocket; // input stream for receiving messages from the client
    private ObjectOutputStream outSocket; //output stream for sending messages to the client
    //private Controller controller;
    private final boolean connected;
    private boolean active = true;

    public ServeOneClient(Socket socketClient, Server server, int id) {
        this.socket = socketClient;
        this.server = server;
        this.id = id;
        this.connected = true;
        try {
            outSocket = new ObjectOutputStream(socketClient.getOutputStream());
            inSocket = new ObjectInputStream(socketClient.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputLock = new Object();
        outputLock = new Object();
    }

    @Override
    public void run() {
        handleConnection();
    }


    public void handleConnection() {
        try {
            while (isActive()) {
                ClientMessage message = (ClientMessage)inSocket.readObject();
                if (message.getMessageType() == MessageType.LOGIN_REQUEST) {
                    server.lobby(this, message.getSenderUsername());
                    Server.LOGGER.info("Received new login request");
                }
                else if (message.getMessageType() == MessageType.GAMEMODE_REPLY) {
                    server.setGameMode(message.getSenderUsername(),((GameModeReply) message).isExpertMode());
                    Server.LOGGER.info("Received game mode response");
                }
                else if (message.getMessageType() == MessageType.PLAYERSNUMBER_REPLY) {
                    server.activateGame(message.getSenderUsername(),((PlayersNumberReply) message).getPlayersNumber());
                    Server.LOGGER.info("Received number of players response");
                }
                else {
                    VirtualView virtualView = server.getController().getVirtualViewByUsername(message.getSenderUsername());
                    virtualView.notify(message);
                    Server.LOGGER.info("Received new " + message.getMessageType().toString() + " message");
                }
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error! " + e.getMessage());
            Server.LOGGER.severe(e.getMessage());
        }
        close();
    }


    public synchronized void sendMessage(Message message) {
        try {
            outSocket.reset();
            outSocket.writeObject(message);
            outSocket.flush();
        } catch (IOException e) {

        }
    }

    public boolean isActive() {
        return active;
    }

    public void close() {
        active = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
