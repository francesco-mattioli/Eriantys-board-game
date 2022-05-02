package it.polimi.ingsw.triton.launcher.client.cli;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class Cli extends Observable<Message> implements ClientView{
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

    public void askGameMode() {
        String input="";
        do{
            try {
                out.print("You are the first player\nPlease, select a game mode [N for normal mode, E for expert mode]: ");
                input = readLine();
            } catch (ExecutionException e) {
                out.println("You should type N or E. Try again...");
            }
        }while(!(input.equalsIgnoreCase("E") || input.equalsIgnoreCase("N")));
        // if input is E, expertMode is true
        boolean expertMode= input.equalsIgnoreCase("E");
        notify(new GameModeReply(clientModel.getUsername(), expertMode));
    }

    @Override
    public void askPlayersNumber() {
        String input="";
        try {
                out.print("Enter number of players: [2 or 3] ");
                input = readLine();
                int numOfPlayers = Integer.parseInt(input);
                notify(new PlayersNumberReply(clientModel.getUsername(), numOfPlayers));
            } catch (ExecutionException | NumberFormatException e) {
                out.println("Try again...");
                askPlayersNumber();
        }
    }




    @Override
    public void askTowerColor(boolean[] chosenTowerColors) {
        String input="";
        try {
            out.print("Choose a tower color [ ");
            for(int i=0;i< chosenTowerColors.length;i++){
                if(chosenTowerColors[i])
                    out.print(TowerColor.values()[i]+" ");
            }
            out.print("]");
            input = readLine();
            notify(new TowerColorReply(clientModel.getUsername(), TowerColor.valueOf(input.toUpperCase())));
        } catch (ExecutionException | NullPointerException e) {
            out.println("Try again...");
            askTowerColor(chosenTowerColors);
        }
    }

    public void showLobbyMessage(ArrayList<String> onlineNicknames, int maxNumberPlayers ) {
        out.println("There are " + onlineNicknames.size() +
                "online / " + maxNumberPlayers + "players connected\n Waiting for " + (maxNumberPlayers-onlineNicknames.size()) + "players");
    }

    //CHECK IF ENUMS WORKS CORRECTLY
    @Override
    public void askWizard(ArrayList<Wizard> wizards) {
        String input="";
        try {
            out.print("Choose a Wizard [ ");
            for(Wizard wizard: wizards){
                out.print(wizard+" ");
            }
            out.print("]");
            input = readLine();
            notify(new WizardReply(clientModel.getUsername(), Wizard.valueOf(input.toUpperCase())));
        } catch (ExecutionException | NullPointerException e) {
            out.println("Try again...");
            askWizard(wizards);
        }
    }

    // TO DO
    public void showInitializedGame(ArrayList<Island> islands, ArrayList<CloudTile> cloudTiles, SchoolBoard schoolBoard){
        //professors
        // wizards are contained in the Enum
    }

    // showChangePhase() con modifiche, per esempio cloud tiles riempite

    // we need to play an assistant card based on the assistant card played by other players
    // IDEA: everytime an assistant card is played, we update client model.
    // In client model we have a map of assistantCardPlayed that we shoe when is called ask AssistantCard

    // TO CHECK IF ENUM WORKS
    @Override
    public void askAssistantCard() {
        String input="";
        // TO DO: print asissitant cards played by other players
        AssistantDeck assistantDeck= clientModel.getAssistantDeck();
        try {
            out.print("Choose an Assistant Card [ ");
            for (AssistantCard assistantCard : assistantDeck.getAssistantDeck()) {
                out.print(assistantCard.getType() + " ");
            }
            out.print("]");
            input = readLine();
            AssistantCard assistantCardReply=null;
            for (AssistantCard assistantCard : assistantDeck.getAssistantDeck()){
                if ((assistantCard.getType()).equals(AssistantCardType.valueOf(input.toUpperCase())))
                    assistantCardReply = assistantCard;
            }
            notify(new AssistantCardReply(clientModel.getUsername(), assistantCardReply));
        } catch (ExecutionException | NullPointerException e) {
            out.println("Try again...");
            askAssistantCard();
        }
    }



    @Override
    public void askMoveStudentFromEntrance() {
        String input="";
        SchoolBoard schoolBoard= clientModel.getSchoolBoard();
        out.print("Choose three students to move from entrance to dining room or an island\n");
        out.print(schoolBoard.toString());
        out.print("To do so, type on each line [color of student, diningRoom] or [color of student, island id]\n");
        try {
            input = readLine();
            String[] splittedInput = input.split(",");
            // to do: MOVE STUDENTS
            notify(new WizardReply(clientModel.getUsername(), Wizard.valueOf(input.toUpperCase())));
        } catch (ExecutionException | NullPointerException e) {
            out.println("Try again...");
            askMoveStudentFromEntrance();
        }
    }












    // DO DELETE
    @Override
    public void showLobbyMessage() {

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
    public void showLoginReply() {

    }

    @Override
    public void showMergeIslandsMessage() {

    }

    @Override
    public void showMotherNaturePosition() {

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
    public void showWinMessage() {

    }

    @Override
    public void showLoseMessage() {

    }




    @Override
    public void showYourTurnMessage() {

    }

    @Override
    public void showAvailableCharacterCard() {

    }

    @Override
    public void askStudentsToMoveOntoIslandCharCard01() {

    }

    @Override
    public void askIslandToCalculateInfluenceCharCard03() {

    }

    @Override
    public void askIslandToPutNoEntryTileCharCard05() {

    }

    @Override
    public void askStudentToSwitchFromCardToEntranceCharCard07() {

    }

    @Override
    public void askColorWithNoInfluenceCharCard09() {

    }

    @Override
    public void askStudentsToSwitchCharCard10() {

    }

    @Override
    public void askStudentsToMoveIntoDiningRoomCharCard11() {

    }

    @Override
    public void askColorCharCard12() {

    }

    @Override
    public void showErrorMessage(ErrorTypeID fullLobby) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
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




}


