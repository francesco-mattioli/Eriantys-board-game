package it.polimi.ingsw.triton.launcher.client;

import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.UpdatedServerInfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.DisconnectionMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.utils.message.Message;

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
    private ExecutorService receiveExecutionQueue;
    private ExecutorService visitExecutionQueue;
    private final ClientView clientView;
    public static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final int port = 50535;

    /**
     * Initializes socket and input, output streams to communicate with the server
     * Executors.newSingleThreadExecutor() sets the thread for receiving message from server
     */
    public Client(ClientView clientView) {
        this.clientView = clientView;
    }


    /**
     *
     * Receive message from the server. Create a thread for receiving and manage the message.
     * By creating a thread for each message, we can receive multiple message at the same time.
     * Based on the message class type, it calls the right method in the Client View using the Visitor Pattern.
     * For each message, the visit method is called on a separated thread so when a Disconnection Message arrives,
     * every thread which is using a visit method will be closed, so the disconnection message will be displayed.
     * By this way, the disconnection message has a higher priority over other messages.
     * Consequently, it's fundamental to initialize the visitTaskQueue in order to execute the visit method, associated
     * to the Disconnection Message, using Executors.
     *
     */
    public void receiveMessage() {
        receiveExecutionQueue.execute(() -> {

            while (!receiveExecutionQueue.isShutdown()) {
                try {
                    ServerMessage message = (ServerMessage) inSocket.readObject();
                    if(message instanceof DisconnectionMessage) {
                        visitExecutionQueue.shutdownNow();
                        visitExecutionQueue = Executors.newSingleThreadExecutor();
                    }
                    // Accept the message using Visitor Pattern
                    visitExecutionQueue.execute(() ->message.accept(new ServerMessageVisitor(clientView)));
                } catch (IOException | ClassNotFoundException e) {
                    //Client.LOGGER.severe("Error: " + e.getMessage()+ ": Connection will be closed");
                    disconnect();
                    receiveExecutionQueue.shutdownNow();
                    visitExecutionQueue.shutdownNow();
                    clientView.showAbortMessage();
                }
            }
        });
    }



    /**
     * This method is executed when the notify method is called on ClientView
     * If it's an UpdateServeInfoMessage, it's a notification reserve to the Client class in order to instantiate the Socket.
     * Otherwise, it's a message to send to Server.
     * @param message to send from client to server
     */
    @Override
    public void update(Message message) {
        if(message instanceof UpdatedServerInfoMessage){
            /* To implement timeout client-side, it is necessary to define SocketAddress.
            SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 50535);
            socket.connect(socketAddress,5000);
            */
            // without timeout
            instantiateSocket((UpdatedServerInfoMessage) message);
        }
        else
            sendMessage(message);
    }

    private void instantiateSocket(UpdatedServerInfoMessage message){
        try {
            socket= new Socket(message.getServerInfo(), port);
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            inSocket = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            clientView.showGenericMessage("Cannot connect to this server!");
            clientView.askIpAddress();
        }
        this.receiveExecutionQueue = Executors.newSingleThreadExecutor();
        this.visitExecutionQueue = Executors.newSingleThreadExecutor();
        receiveMessage();
        //clientView.askUsername();
    }

    /**
     * @param message to send from client to server
     */
    public void sendMessage(Message message) {
        try {
            outSocket.writeObject(message);
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
                receiveExecutionQueue.shutdownNow();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
