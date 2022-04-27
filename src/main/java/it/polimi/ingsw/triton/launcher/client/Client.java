package it.polimi.ingsw.triton.launcher.client;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Observer<Message> {
    private Socket socket;
    private ObjectInputStream inSocket;
    private ObjectOutputStream outSocket;
    private ExecutorService readExecutionQueue;
    private ClientModel clientModel;

    public Client(ClientView view) {
        try {
            // initializes socket and input, output streams to communicate with the server
            socket = new Socket("localhost", 3000);
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            inSocket = new ObjectInputStream(socket.getInputStream());

            // sets the thread for receiving message from server
            this.readExecutionQueue = Executors.newSingleThreadExecutor();
            this.clientModel = new ClientModel(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Receive message from the server.
     * Create a thread for reading and manage the message.
     */
    public void receiveMessage() {
        readExecutionQueue.execute(() -> {

            while (!readExecutionQueue.isShutdown()) {
                try {
                    ServerMessage message = (ServerMessage) inSocket.readObject();
                    manageReceivedMessage(message);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error! " + e.getMessage());
                    disconnect();
                    readExecutionQueue.shutdownNow();
                }
            }
        });
    }

    /**
     * Based on the message received, it calls all methods on the clientModel
     *
     * @param message from the server
     */
    public void manageReceivedMessage(ServerMessage message) {
        if (message.getMessageType() == MessageType.LOGIN_REPLY) {
            clientModel.addPlayerUsername(message.getReceiverUsername());
        }
    }


    /**
     * This update is called when the Client is notified by the Observable ....
     *
     * @param message
     */
    @Override
    public void update(Message message) {
        sendMessage(message);
    }

    /**
     * @param message to send to the server
     */
    public void sendMessage(Message message) {
        try {
            outSocket.writeObject(message);
            outSocket.reset();
            try {
                // This sleep method should be removed. It is here just for debugging.
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
