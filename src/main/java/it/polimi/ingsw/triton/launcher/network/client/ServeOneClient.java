package it.polimi.ingsw.triton.launcher.network.client;

import it.polimi.ingsw.triton.launcher.network.Server;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;
import it.polimi.ingsw.triton.launcher.network.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.network.message.clientmessage.PlayersNumbersAndModeReply;
import it.polimi.ingsw.triton.launcher.view.VirtualView;

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
                if (message.getMessageType() == MessageType.LOGIN_REQUEST)
                    server.lobby(this, message.getSenderUsername());
                else if (message.getMessageType() == MessageType.PLAYERSNUMBER_REPLY)
                    server.activateGame(((PlayersNumbersAndModeReply) message).getPlayersNumber(), message.getSenderUsername());
                else {
                    VirtualView virtualView = server.getController().getVirtualViewByUsername(message.getSenderUsername());
                    virtualView.notify(message);
                }
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error! " + e.getMessage());
        } finally {
            close();
        }
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
