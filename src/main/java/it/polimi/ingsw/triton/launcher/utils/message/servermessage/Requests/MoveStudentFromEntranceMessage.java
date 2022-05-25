package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MoveStudentOntoDiningRoomMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MoveStudentOntoIslandMessage;


/**
 * This message is sent by the server to ask the current player which student wants to move from his entrance.
 */
public class MoveStudentFromEntranceMessage extends AskMessage {

    public MoveStudentFromEntranceMessage(){
        super.expectedResponseMessageClasses.add(MoveStudentOntoDiningRoomMessage.class);
        super.expectedResponseMessageClasses.add(MoveStudentOntoIslandMessage.class);

    }


    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
