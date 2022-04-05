package it.polimi.ingsw.triton.launcher.model;

public class MoveStudentIntoDiningRoom implements Action{
    private Color student;
    private Wallet wallet;

    public MoveStudentIntoDiningRoom(Color student, Wallet wallet){
        this.student = student;
        this.wallet = wallet;
    }

    public void updateWallet(){
        // TODO implement here
    }

    @Override
    public void execute() {
        // TODO implement here
    }
}
