package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.network.ServeOneClient;

public class InfoMessageVisitor {
    private final ServeOneClient serveOneClient;
    private final String username;

    public InfoMessageVisitor(ServeOneClient serveOneClient, String username) {
        this.serveOneClient = serveOneClient;
        this.username = username;
    }

    /**
     * Calls the method of serveOneClient class to send the message.
     *
     * @param message the message to send to all the clients.
     */
    public void visit(InfoMessage message) {
        serveOneClient.sendMessage(message);
    }

    /**
     * Calls the method of serveOneClient class to send the message.
     *
     * @param message the message to send to a specific client.
     */
    public void visit(InfoWithReceiverMessage message) {
        if (message.receiverUsername.equals(username))
            serveOneClient.sendMessage(message);
    }
}
