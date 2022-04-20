package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.network.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client{
    private Socket socket;
    private ObjectInputStream inSocket;
    private ObjectOutputStream outSocket;
    private ExecutorService executor;

    public Client(){
        try {
            socket = new Socket("localhost", Server.PORT);
            inSocket = new ObjectInputStream(socket.getInputStream());
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            executor = Executors.newSingleThreadExecutor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void readMessage(){
        executor.execute(() -> {
            while(!executor.isShutdown()){
                Message message;
                try {
                    message = (Message) inSocket.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    disconnect();
                    executor.shutdownNow();
                }
            }
        });
    }*/

    /*private void login() {
        System.out.println("Insert username:");
        try {
            String username = inKeyboard.readLine();
            outSocket.println(username); // this method sends the input to the ServeOneClient through the clientSocket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void sendMessage(Message message){
        try {
            outSocket.writeObject(message);
            outSocket.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        if(!socket.isClosed()){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
