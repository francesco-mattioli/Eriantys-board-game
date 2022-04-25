package it.polimi.ingsw.triton.launcher.view.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class InputReadTask implements Callable<String> {
    private final BufferedReader bufferedReader;

    public InputReadTask() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }
    @Override
    public String call() throws IOException, InterruptedException {
        // wait until there is data to complete a readLine()
        while (!bufferedReader.ready()) {
            Thread.sleep(200);
        }
        return bufferedReader.readLine();
    }
}

/*
In synchronous operations tasks are performed one at a time and only when one is completed,
the following is unblocked. In other words, you need to wait for a task to finish to move
to the next one.

In asynchronous operations, on the other hand, you can move to another task before the previous
 one finishes. This way, with asynchronous programming you’re able to deal with multiple
 requests simultaneously, thus completing more tasks in a much shorter period of time.
 */