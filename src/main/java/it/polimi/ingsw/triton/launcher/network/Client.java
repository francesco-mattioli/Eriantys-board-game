package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.network.message.Message;

import java.io.*;
import java.net.Socket;

public class Client{
    private Socket socket;
    private ObjectInputStream inSocket;
    private ObjectOutputStream outSocket;
    private BufferedReader inKeyboard;

    public Client(){
        try {
            socket = new Socket("localhost", Server.PORT);

            inSocket = new ObjectInputStream(socket.getInputStream());
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // buffer for reading from input
            inKeyboard = new BufferedReader(new InputStreamReader(System.in));

            //login();

        } catch (IOException e) {
            e.printStackTrace();

        } finally { // anyway the clientSocket must be closed
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

    // to run the Client
    public static void main(String[] args) {
        new Client();
    }




}
