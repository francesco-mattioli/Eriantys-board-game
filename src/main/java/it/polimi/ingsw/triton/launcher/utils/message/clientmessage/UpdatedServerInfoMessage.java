package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.Message;

public class UpdatedServerInfoMessage implements Message {
    private final String serverInfo;

    public UpdatedServerInfoMessage(String serverInfo){
        this.serverInfo = serverInfo;
    }

    public String getServerInfo() {
        return serverInfo;
    }
}
