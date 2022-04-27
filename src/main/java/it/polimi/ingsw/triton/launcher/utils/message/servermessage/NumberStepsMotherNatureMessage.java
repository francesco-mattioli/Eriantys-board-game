package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks to the player how many steps mother nature has to do.
 */
public class NumberStepsMotherNatureMessage extends ServerMessage{
    public NumberStepsMotherNatureMessage(String receiverUsername) {
        super(MessageType.NUMBER_STEPS_MOTHER_NATURE, receiverUsername);
    }
}
