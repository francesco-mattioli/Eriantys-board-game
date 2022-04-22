package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.controller.Controller;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;
import it.polimi.ingsw.triton.launcher.network.message.PlayersNumbersAndModeReply;
import it.polimi.ingsw.triton.launcher.view.VirtualView;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Scanner;

// Each player must be treated as a thread
public class ServeOneClient implements Runnable {
    private final Socket socket;
    private final Server server;
    private final int id;
    private ObjectInputStream inSocket; // input stream for receiving messages from the client
    private ObjectOutputStream outSocket; //output stream for sending messages to the client
    //private Controller controller;
    private boolean connected;
    private final Object inputLock;
    private final Object outputLock;
    private boolean active = true;

    public ServeOneClient(Socket socketClient, Server server,int id) {
        this.socket = socketClient;
        this.server = server;
        this.id=id;
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
        handleConnection();
    }


    public void handleConnection(){
        try{
            while(isActive()){
                Message message = (Message)inSocket.readObject();
                if(message.getMessageType()==MessageType.LOGIN_REQUEST)
                    server.lobby(this, message.getSenderName());
                else if(message.getMessageType()==MessageType.PLAYERSNUMBER_REPLY)
                    server.activateGame(((PlayersNumbersAndModeReply)message).getPlayersNumber(), message.getSenderName());
                else{
                    VirtualView virtualView = server.getController().getVirtualViewByUsername(message.getSenderName());
                    virtualView.notify(message);
                }
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
        }
    }


    public synchronized void sendMessage(Message message) {
        try {
            outSocket.reset();
            outSocket.writeObject(message);
            outSocket.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    public boolean isActive(){
        return active;
    }

    public void close(){
        active = false;
        try {
            socket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
