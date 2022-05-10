package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.Message;

import java.util.Map;

public class UpdatedServerInfoMessage implements Message {
    private Map<String, String> serverInfo;

    public UpdatedServerInfoMessage(Map<String, String> serverInfo){
        this.serverInfo = serverInfo;
    }

    public Map<String, String> getServerInfo() {
        return serverInfo;
    }
}
