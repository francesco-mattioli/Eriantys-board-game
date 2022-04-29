package it.polimi.ingsw.triton.launcher.client;

import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.client.view.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.GenericMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.LobbyMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests.TowerColorRequest;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

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
    private ExecutorService readExecutionQueue;
    private ClientView clientView;
    public static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    public Client(ClientView clientView) {
        try {
            // initializes socket and input, output streams to communicate with the server
            socket = new Socket("127.0.0.1", 50535);
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            inSocket = new ObjectInputStream(socket.getInputStream());
            // sets the thread for receiving message from server
            this.readExecutionQueue = Executors.newSingleThreadExecutor();
            this.receiveMessage();
            this.clientView=clientView;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Receive message from the server.
     * Create a thread for reading and manage the message.
     * Based on the message class type, it calls the right method in the Client View using the Visitor Pattern
     */
    public void receiveMessage() {
        readExecutionQueue.execute(() -> {

            while (!readExecutionQueue.isShutdown()) {
                try {
                    BroadcastServerMessage message = (BroadcastServerMessage) inSocket.readObject();
                    Client.LOGGER.info("Received: " + message.getMessageType());
                    message.accept(new ClientVisitor(clientView));
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error! " + e.getMessage());
                    Client.LOGGER.severe("Connection will be closed");
                    disconnect();
                    readExecutionQueue.shutdownNow();
                }
            }
        });
    }



    /**
     * This method is executed when the notify method is called on ClientView
     *
     * @param message to send from client to server
     */
    @Override
    public void update(Message message) {
        sendMessage(message);
    }

    /**
     * @param message to send from client to server
     */
    public void sendMessage(Message message) {
        try {
            outSocket.writeObject(message);
            Client.LOGGER.info("Sent: " + message.getMessageType());
            outSocket.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects the client socket from the server.
     */
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                readExecutionQueue.shutdownNow();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
