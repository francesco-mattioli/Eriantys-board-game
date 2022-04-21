package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.controller.Controller;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

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
        //try {
            handleConnection();
        //}catch (IOException e) {
          //  e.printStackTrace();
        //}
    }


    public void handleConnection (){
        try{
            while(isActive()){
                Message message = (Message)inSocket.readObject();
                if(message.getMessageType()==MessageType.LOGIN_REQUEST)
                    server.lobby(this, message.getNickname());
                //if(message.getMessageType()==MessageType.PLAYERSNUMBER_REPLY)
                    //server.lobby(this,);

                //read = in.nextLine();
                //notify(read);
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }//finally{
         //   //close();
        //}
    }


    private synchronized void sendMessage(Object message) {
        try {
            outSocket.reset();
            outSocket.writeObject(message);
            outSocket.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }

    private synchronized boolean isActive(){
        return active;
    }



    /*public void handleConnection02() throws IOException {
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
