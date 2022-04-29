package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message contains the parameters to apply the effect.
 */
public class CharacterCard01Reply extends ClientMessage{
    private final Color student;
    private final Island island;
    public CharacterCard01Reply(String senderUsername, Color student, Island island) {
        super(MessageType.CHARACTER_CARD_01_PARAMETER, senderUsername);
        this.island = island;
        this.student = student;
    }

    public Color getStudent() {
        return student;
    }

    public Island getIsland() {
        return island;
    }
}
