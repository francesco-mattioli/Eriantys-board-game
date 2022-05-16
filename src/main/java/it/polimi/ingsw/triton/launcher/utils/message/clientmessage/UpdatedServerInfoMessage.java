package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.Message;

/**
 * This message is not send by the client or server, but it's useful to communicate the IP address
 * that the user has written to connect to the server.
 * The client will try to connect to that IP address.
 */
public class UpdatedServerInfoMessage implements Message {
    private final String serverInfo;

    public UpdatedServerInfoMessage(String serverInfo){
        this.serverInfo = serverInfo;
    }

    public String getServerInfo() {
        return serverInfo;
    }
}
