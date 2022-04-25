package it.polimi.ingsw.triton.launcher.view.cli;

import it.polimi.ingsw.triton.launcher.network.Observable;
import it.polimi.ingsw.triton.launcher.network.client.Client;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;
import it.polimi.ingsw.triton.launcher.network.message.clientmessage.LoginRequest;
import it.polimi.ingsw.triton.launcher.view.View;

import java.io.PrintStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli extends Observable<Message> implements View {
    private final PrintStream out;
    private Client client;

    public Cli() {
        out = System.out;
    }

    public void start() {
        out.println("\n" +
                "  _____   ____    ___      _      _   _   _____  __   __  ____  \n" +
                " | ____| |  _ \\  |_ _|    / \\    | \\ | | |_   _| \\ \\ / / / ___| \n" +
                " |  _|   | |_) |  | |    / _ \\   |  \\| |   | |    \\ V /  \\___ \\ \n" +
                " | |___  |  _ <   | |   / ___ \\  | |\\  |   | |     | |    ___) |\n" +
                " |_____| |_| \\_\\ |___| /_/   \\_\\ |_| \\_|   |_|     |_|   |____/ \n" +
                "                                                                \n");
        out.println("Welcome to Eriantys!");
        init();
    }

    public void init(){
        this.client=new Client();
        this.addObserver(client);
        askUsername();
    }

    @Override
    public void askUsername() {
        out.print("Enter your username: ");
        try {
            String username = readLine();
            notify(new LoginRequest(username, MessageType.LOGIN_REQUEST));
        } catch (ExecutionException e) {
            out.println("try again");
        }

    }

    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        Thread inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }
}

