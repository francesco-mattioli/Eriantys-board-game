package it.polimi.ingsw.triton.launcher.network.client;

import it.polimi.ingsw.triton.launcher.network.Observer;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.network.message.servermessage.ErrorMessage;
import it.polimi.ingsw.triton.launcher.network.message.servermessage.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
// capire se usare observer o riferimenti
public class Client implements Observer<Message> {
    private Socket socket;
    private ObjectInputStream inSocket;
    private ObjectOutputStream outSocket;

    public Client() {
        try {
            socket = new Socket("localhost", 3000);
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            inSocket = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage(){
        try {
            ServerMessage message = (ServerMessage) inSocket.readObject();

        }catch(IOException | ClassNotFoundException e){
            // TO DO: ServerMessage message = new ErrorMessage(null, "Connection lost with the server.");
            System.err.println("Error! " + e.getMessage());
        }
    }


    @Override
    public void update(Message message) {
        sendMessage(message);
    }

    public void sendMessage(Message message) {
        try {
            outSocket.writeObject(message);
            outSocket.reset();
            try {
                Thread.sleep(500000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
