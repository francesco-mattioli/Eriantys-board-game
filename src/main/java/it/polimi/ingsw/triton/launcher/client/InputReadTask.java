package it.polimi.ingsw.triton.launcher.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * This class implements a task so reading can be executed on a different thread.
 * This allows the CLI to deal with multiple requests simultaneously,
 * thus completing more tasks in a much shorter period of time.
 */
public class InputReadTask implements Callable<String> {
    private final BufferedReader bufferedReader;

    /**
     * Instantiates a new Input read task.
     */
    public InputReadTask() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * This method reads a line from System.in.
     *
     * @return the read string
     * @throws IOException if an I/O error occurs
     */
    @Override
    public String call() throws IOException {
        return bufferedReader.readLine();
    }
}
