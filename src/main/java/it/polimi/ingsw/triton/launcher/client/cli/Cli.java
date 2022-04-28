package it.polimi.ingsw.triton.launcher.client.cli;

import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.LoginRequest;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * The type Cli.
 */
public class Cli extends Observable<Message> implements ClientView, Observer<Object>{
    private final PrintStream out;
    private Client client;

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
        this.client=new Client(this);
        this.addObserver(client);
        askUsername();
    }

    @Override
    public void askUsername() {
        out.print("Enter your username: ");
        try {
            String username = readLine();
            notify(new LoginRequest(username, MessageType.LOGIN_REQUEST));
        } catch (ExecutionException e) {
            out.println("Try again...");
        }

    }

    @Override
    public void askAssistantCard(AssistantDeck assistantDeck) {

    }

    @Override
    public void showChangeInfluenceMessage(Island islandWithNewInfluence, String usernameDominator) {

    }

    @Override
    public void askCloudTile(ArrayList<CloudTile> availableCloudTiles) {

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
    public void askPlayersNumberAndMode() {

    }

    @Override
    public void showTieMessage() {

    }

    @Override
    public void askTowerColor(boolean[] availableColors) {

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
    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        new Thread(futureTask).start();
        // METTERE A POSTO LA QUESTIONE DEL NULL
        String input = null;
        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
    }
}

