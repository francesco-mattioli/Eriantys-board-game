package it.polimi.ingsw.triton.launcher.client.cli;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.ClientMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.PlayersNumbersAndModeReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.LoginRequest;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import java.io.PrintStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * The type Cli.
 */
public class Cli extends Observable<Message> implements ClientView, Observer<Object>{
    private final PrintStream out;
    private ClientModel clientModel;
    /**
     * Instantiates a new Cli;
     * The PrintStream out variable is set to System.out, by this way System.out.println() is not replicated multiple times.
     *
     */
    public Cli() {
        out = System.out;
    }

    /**
     * Print the logo and welcome the player.
     */
    public void start() {
        out.println("\n" +
                "  _____   ____    ___      _      _   _   _____  __   __  ____  \n" +
                " | ____| |  _ \\  |_ _|    / \\    | \\ | | |_   _| \\ \\ / / / ___| \n" +
                " |  _|   | |_) |  | |    / _ \\   |  \\| |   | |    \\ V /  \\___ \\ \n" +
                " | |___  |  _ <   | |   / ___ \\  | |\\  |   | |     | |    ___) |\n" +
                " |_____| |_| \\_\\ |___| /_/   \\_\\ |_| \\_|   |_|     |_|   |____/ \n" +
                "                                                                \n");
        out.println("Welcome to Eriantys!");
        init();
    }

    /**
     * Creates the Client to communicate with the Server, then sets the latter as an Observer of the Cli.
     * Eventually, it asks the username to the player.
     */
    public void init(){
        this.addObserver(new Client(this));
        this.clientModel=new ClientModel();
        askUsername();
    }

    @Override
    public void askUsername() {
        out.print("Enter your username: ");
        try {
            String username = readLine();
            this.clientModel.setUsername(username);
            notify(new LoginRequest(username, MessageType.LOGIN_REQUEST));
        } catch (ExecutionException e) {
            out.println("Try again...");
        }

    }

    @Override
    public void askPlayersNumberAndMode() {
        String input="";
        do{
            try {
                out.print("Please, select a game mode [N for normal mode, E for expert mode]: ");
                input = readLine();
            } catch (ExecutionException e) {
                out.println("You should type N or E. Try again...");
            }
        }while(!(input.equalsIgnoreCase("E") || input.equalsIgnoreCase("N")));
        // if input is E, expertMode is true
        boolean expertMode= input.equalsIgnoreCase("E");
        try {
                out.print("Enter number of players: [2 or 3] ");
                input = readLine();
            } catch (ExecutionException e) {
                out.println("Try again...");
        }
        int numOfPlayers = Integer.parseInt(input);
        notify(new PlayersNumbersAndModeReply(clientModel.getUsername(), numOfPlayers,expertMode));
    }

    @Override
    public void askAssistantCard() {

    }

    @Override
    public void showChangeInfluenceMessage() {

    }

    @Override
    public void askCloudTile() {

    }

    @Override
    public void showDisconnectionMessage() {

    }

    @Override
    public void showEmptyBagMessage() {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showFillCloudTilesMessage() {

    }

    @Override
    public void showFullLobbyMessage() {

    }

    @Override
    public void showGameInfo() {

    }

    @Override
    public void showGenericMessage() {

    }

    @Override
    public void showInfoActionPhase() {

    }

    @Override
    public void showInfoAssistantCardPlayed() {

    }

    @Override
    public void showLobbyMessage() {

    }

    @Override
    public void showLoginReply() {

    }

    @Override
    public void showMergeIslandsMessage() {

    }

    @Override
    public void showMotherNaturePosition() {

    }

    @Override
    public void askMoveStudentFromEntrance() {

    }

    @Override
    public void showMoveTowerOntoIsland() {

    }

    @Override
    public void showMoveTowerOntoSchoolBoard() {

    }

    @Override
    public void askNumberStepsMotherNature() {

    }



    @Override
    public void showTieMessage() {

    }

    @Override
    public void askTowerColor() {

    }

    @Override
    public void showWinMessage() {

    }

    @Override
    public void askWizard() {

    }

    @Override
    public void showYourTurnMessage() {

    }

    @Override
    public void showAvailableCharacterCard() {

    }

    /**
     * Read a string line using a separated thread
     *
     * @return the string
     * @throws ExecutionException the execution exception
     */
    public String readLine() throws ExecutionException, NullPointerException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        new Thread(futureTask).start();
        // METTERE A POSTO LA QUESTIONE DEL NULL
        try {
            return futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        throw new NullPointerException("the methods had read a null string");
    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
    }
}

