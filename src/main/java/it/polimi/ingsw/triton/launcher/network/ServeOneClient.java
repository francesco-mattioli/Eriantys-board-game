package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.controller.Controller;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.util.Observable;

// Each player must be treated as a thread
public class ServeOneClient implements Runnable {
    private final Socket socket;
    private final Server server;
    private ObjectInputStream inSocket; // input stream for receiving messages from the client
    private ObjectOutputStream outSocket; //output stream for sending messages to the client
    //private Controller controller;
    private boolean connected;
    private final Object inputLock;
    private final Object outputLock;

    public ServeOneClient(Socket socketClient, Server server) {
        this.socket = socketClient;
        this.server = server;
        this.connected = true;
        try{
            inSocket= new ObjectInputStream(socketClient.getInputStream());
            outSocket= new ObjectOutputStream(socketClient.getOutputStream());
        }catch (IOException e) {
            e.printStackTrace();
        }
        inputLock = new Object();
        outputLock = new Object();
    }

    @Override
    public void run() {
        try {
            handleConnection();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleConnection() throws IOException {
        try{
            while (!Thread.currentThread().isInterrupted()){
                synchronized (inputLock){
                    Message message = (Message) inSocket.readObject();
                    if(message.getMessageType() == MessageType.LOGIN_REQUEST && message != null){
                        server.addClient(message.getNickname(), this);
                    }else{
                        server.onReceive(message);
                    }
                }
            }
        }catch(ClassCastException | ClassNotFoundException e){
            e.printStackTrace();
        }
        socket.close();
    }

    /*public void login() throws IOException {
        String clientSeq = inSocket.readLine();
        controller.setUsername(clientSeq);
        outSocket.println("username ok");
        controller.print();
    }*/

    private void closeSocket() {
        if(connected){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connected = false;
            Thread.currentThread().interrupt();
            server.onDisconnect(this);
        }
    }

    public void sendMessage(Message message){
        synchronized (outputLock){
            try {
                outSocket.writeObject(message);
                outSocket.reset();
            } catch (IOException e) {
                closeSocket();
            }
        }
    }
}
