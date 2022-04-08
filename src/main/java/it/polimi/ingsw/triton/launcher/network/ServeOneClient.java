package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.controller.Controller;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.util.Observable;

// Each player must be treated as a thread
public class ServeOneClient implements Runnable {
    private final Socket socket;
    private BufferedReader inSocket; // input stream for receiving messages from the client
    private PrintWriter outSocket; //output stream for sending messages to the client
    private Controller controller;

    public ServeOneClient(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller = controller;
        try{
            inSocket= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try{
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            closeSocket();
        }
    }

    public void login() throws IOException {
        String clientSeq = inSocket.readLine();
        controller.setUsername(clientSeq);
        outSocket.println("username ok");
        controller.print();
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
