package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class MotherNatureReply extends ClientMessage{
    private final int numSteps;
    public MotherNatureReply(String senderUsername, int numSteps) {
        super(MessageType.NUMBER_STEPS_MOTHER_NATURE_REPLY, senderUsername);
        this.numSteps = numSteps;
    }

    public int getNumSteps() {
        return numSteps;
    }
}
