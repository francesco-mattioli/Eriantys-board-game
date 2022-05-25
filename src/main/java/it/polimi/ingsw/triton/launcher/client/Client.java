package it.polimi.ingsw.triton.launcher.client;

import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.UpdatedServerInfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.DisconnectionMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Client implements Observer<Message> {
    private Socket socket;
    private ObjectInputStream inSocket;
    private ObjectOutputStream outSocket;
    private ExecutorService receiveExecutionQueue = Executors.newSingleThreadExecutor();
    private ExecutorService visitExecutionQueue;
    private final ClientView clientView;
    private static final int PORT = 50535;
    public static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    /**
     * Initializes socket and input, output streams to communicate with the server.
     * Executors.newSingleThreadExecutor() sets the thread for receiving message from server.
     */
    public Client(ClientView clientView) {
        this.clientView = clientView;
    }


    /**
     * Receive message from the server. Create a thread for receiving and manage the message.
     * By creating a thread for each message, we can receive multiple message at the same time.
     * Based on the message class type, it calls the right method in the Client View using the Visitor Pattern.
     * For each message, the visit method is called on a separated thread so when a Disconnection Message arrives,
     * every thread which is using a visit method will be closed, so the disconnection message will be displayed.
     * By this way, the disconnection message has a higher priority over other messages.
     * Consequently, it's fundamental to initialize the visitTaskQueue in order to execute the visit method, associated
     * to the Disconnection Message, using Executors.
     */
    public void receiveMessage() {
        receiveExecutionQueue.execute(() -> {

            while (!receiveExecutionQueue.isShutdown()) {
                try {
                    ServerMessage message = (ServerMessage) inSocket.readObject();
                    if (message instanceof DisconnectionMessage) {
                        visitExecutionQueue.shutdownNow();
                        visitExecutionQueue = Executors.newSingleThreadExecutor();
                    }
                    // Accept the message using Visitor Pattern
                    visitExecutionQueue.execute(() -> message.accept(new ServerMessageVisitor(clientView)));
                } catch (IOException | ClassNotFoundException e) {
                    disconnect();
                    receiveExecutionQueue.shutdownNow();
                    visitExecutionQueue.shutdownNow();
                    clientView.showAbortMessage();
                }
            }
        });
    }


    /**
     * This method is executed when the notify method is called on ClientView.
     * If it's an UpdateServeInfoMessage, it's a notification reserve to the Client class in order to instantiate the Socket.
     * Otherwise, it's a message to send to Server.
     *
     * @param message to send from client to server.
     */
    @Override
    public void update(Message message) {
        if (message instanceof UpdatedServerInfoMessage)
            instantiateSocket((UpdatedServerInfoMessage) message);
        else
            sendMessage(message);
    }

    /**
     * Tries to instantiate a socket between the client and the server.
     * If a server is listening on the IP address written by the user, it instantiates the socket, I/O streams and the executors
     * that manage the receipt of messages.
     * After that, it calls a view method that asks the player to insert the username.
     *
     * @param message the message that contains the IP address of server the user wants to connect.
     */
    private void instantiateSocket(UpdatedServerInfoMessage message) {
        try {
            socket = new Socket(message.getServerInfo(), PORT);
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            inSocket = new ObjectInputStream(socket.getInputStream());
            this.receiveExecutionQueue = Executors.newSingleThreadExecutor();
            this.visitExecutionQueue = Executors.newSingleThreadExecutor();
            receiveMessage();
            clientView.askUsername();
        } catch (IOException | NullPointerException e) {
            clientView.showGenericMessage("ERROR: Cannot connect to this server!");
            clientView.askIpAddress();
        }
    }

    /**
     * @param message to send from client to server.
     */
    public void sendMessage(Message message) {
        try {
            outSocket.writeObject(message);
            outSocket.reset();
        } catch (IOException e) {
            Client.LOGGER.severe("Cannot send the message: " + message.getClass().getCanonicalName());
        }
    }

    /**
     * Disconnects the client socket from the server.
     */
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                receiveExecutionQueue.shutdownNow();
                socket.close();
            }
        } catch (SecurityException e) {
            Client.LOGGER.severe("Cannot shut the receiving queue down correctly");
        } catch (IOException e) {
            Client.LOGGER.severe("Cannot close the socket correctly");
        }
    }
}
