package it.polimi.ingsw.triton.launcher.client.cli;

import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.client.InputReadTask;
import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.*;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.Utility;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.UpdatedServerInfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages.LoginRequest;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.login_messages.PlayersNumberAndGameModeReply;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class Cli extends Observable<Message> implements ClientView{
    private final PrintStream out;
    private ClientModel clientModel;
    private Thread inputReadThread;
    private static final String DEFAULT_IP = "localhost";
    private static final String TRY_AGAIN = "Try again...";
    private static final String ENTER_THE ="Enter the ";
    private static final String SCHOOLBOARD = "SchoolBoards:";
    public static final String CHARACHTER_CARD_COMMAND="--playCC";
    private static final String CHARACTER_CARDS="Available Character Cards:";

    /**
     * Instantiates a new Cli;
     * The PrintStream out variable is set to System out, by this way System.out.println() is not replicated multiple times.
     */
    public Cli() {
        out = System.out;
    }

    /**
     * Prints the logo and welcomes the player.
     */
    public void start() {
        out.print("\n" +
                "  _____   ____    ___      _      _   _   _____  __   __  ____  \n" +
                " | ____| |  _ \\  |_ _|    / \\    | \\ | | |_   _| \\ \\ / / / ___| \n" +
                " |  _|   | |_) |  | |    / _ \\   |  \\| |   | |    \\ V /  \\___ \\ \n" +
                " | |___  |  _ <   | |   / ___ \\  | |\\  |   | |     | |    ___) |\n" +
                " |_____| |_| \\_\\ |___| /_/   \\_\\ |_| \\_|   |_|     |_|   |____/ \n");
        out.println("Welcome to Eriantys!");
        init();
    }

    /**
     * Creates the Client to communicate with the Server, asking the IP address of it. Then sets the latter as an Observer of the Cli.
     */
    public void init(){
        this.addObserver(new Client(this));
        this.clientModel=new ClientModel();
        askIpAddress();
    }

    /**
     * Asks the player to insert the IP address of the server in order to create the socket.
     * If the user doesn't insert anything, it creates the socket on localhost IP address.
     */
    public void askIpAddress(){
        String ip = "";
        while (!isCorrectIpAddress(ip)){
            out.print(Utility.ANSI_BOLDGREEN+"Please, enter the ip address of the server [default: " + DEFAULT_IP + "]: "+ Utility.ANSI_RESET);
            ip = readLine();
            if(ip.isEmpty() || ip.equalsIgnoreCase(DEFAULT_IP))
                ip = DEFAULT_IP;
        }
        notify(new UpdatedServerInfoMessage(ip));
    }

    /**
     * This method analyzes the string inserted by the user, applying a pattern that allows the system to know if the
     * string corresponds to an IP address format.
     * @param ip the string with the IP address to analyze.
     * @return true if the string corresponds to an IP address, false otherwise.
     */
    private boolean isCorrectIpAddress(String ip){
        if(ip.equalsIgnoreCase(DEFAULT_IP))
            return true;
        return org.apache.commons.validator.routines.InetAddressValidator.getInstance().isValid((ip));
    }

    /**
     * Asks the player to insert the username he wants in the game.
     * Asks the player to insert the username he wants in the game.
     * After that, the server will check if the username is forbidden or already used and, in that case, it'll send
     * back another request asking the username.
     */
    @Override
    public void askUsername() {
        out.print(Utility.ANSI_BOLDGREEN + "Enter your username: " + Utility.ANSI_RESET);
        try {
            String username = readLine();
            notify(new LoginRequest(username));
        } catch (NullPointerException e) {
            out.println(TRY_AGAIN);
        }
    }

    /**
     * If the username is correct, it shows to the player that the login is completed.
     */
    @Override
    public void showLoginReply() {
        out.println("Username '"+clientModel.getUsername()+"' accepted!");
    }

    /**
     * Asks only the first player to insert the number of players of the game and the game mode (expert or not).
     */
    @Override
    public void askNumPlayersAndGameMode(){
        boolean expertMode = askGameMode();
        int numOfPlayers = askNumOfPlayers();
        notify(new PlayersNumberAndGameModeReply(clientModel.getUsername(), numOfPlayers, expertMode));
    }

    /**
     * Asks the player to choose the game mode.
     * The first player has to insert 'E' if he wants the expert mode, 'N' otherwise.
     * @return true if the player wants the expert mode, false otherwise.
     */
    private boolean askGameMode() {
        String input="";
        out.println("You are the first player");
        try{
            do{
                out.print(Utility.ANSI_BOLDGREEN + "Please, select a game mode [N for normal mode, E for expert mode]: " + Utility.ANSI_RESET);
                input = readLine();
            }while(!(input.equalsIgnoreCase("E") || input.equalsIgnoreCase("N")));
        } catch (NullPointerException e){
            showAbortMessage();
        }
        // if input is E, expertMode is true
        return input.equalsIgnoreCase("E");
    }

    /**
     * Asks the first to player to choose the number of players he wants to create the game.
     * Client-side, it checks only if the player inserts a number and not a word.
     * The server will check if the number is 2 or 3 because the game supports only two-players and three-players game.
     * @return the number of player chosen by the first player.
     */
    private int askNumOfPlayers() {
        int numPlayers = 2;
        try {
                out.print(Utility.ANSI_BOLDGREEN + "Enter number of players [2 or 3]: " + Utility.ANSI_RESET);
                String input = readLine();
                numPlayers = Integer.parseInt(input);
        } catch(NumberFormatException e){
            out.println(TRY_AGAIN);
            askNumOfPlayers();
        } catch (NullPointerException e){
            showAbortMessage();
        }
        return numPlayers;
    }

    /**
     * Shows all the online players.
     * @param onlineNicknames the list with the online players.
     */
    public void showLobbyMessage(List<String> onlineNicknames) {
        out.println("ONLINE PLAYERS:");
        for(String username: onlineNicknames){
            out.println("- " + username);
        }
    }

    /**
     * Asks the player to choose his tower color.
     * This method shows only the available tower colors (these have false in their positions in chosenTowerColors array).
     * The player has to insert the number corresponding to the color. If he doesn't input a number or if he inputs an uncorrected number,
     * this method asks again to choose the tower color.
     * Server will check if the color chosen is effectively unused.
     * @param chosenTowerColors the arrays of tower color, available and not.
     */
    @Override
    public void askTowerColor(boolean[] chosenTowerColors) {
        try {
            out.print(Utility.ANSI_BOLDGREEN + "Choose a tower color [");
            for(int i=0;i< chosenTowerColors.length;i++){
                if(!chosenTowerColors[i]) {
                    out.print(i + " for " + TowerColor.values()[i]);
                    if(i<chosenTowerColors.length-1)
                        out.print(", ");
                }
            }
            out.print("]: " + Utility.ANSI_RESET);
            String input = readLine();
            notify(new TowerColorReply(clientModel.getUsername(), TowerColor.values()[Integer.parseInt(input)]));
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            out.println(TRY_AGAIN);
            askTowerColor(chosenTowerColors);
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Asks the player to choose his wizard. Every wizard is associated to a color.
     * If the player inputs a word that is not a correct color of the game, it will ask again to insert the color of the wizard.
     * The server will check if the color of the wizard chosen by the player is effectively unused.
     * @param wizards the list of available wizards.
     */
    @Override
    public void askWizard(List<Wizard> wizards) {
        try {
            out.print(Utility.ANSI_BOLDGREEN + "Choose a Wizard " + Utility.printAvailableWizards(wizards));
            out.print(": " + Utility.ANSI_RESET);
            String input = readLine();
            notify(new WizardReply(clientModel.getUsername(), Wizard.valueOf(input.toUpperCase())));
        }catch(IllegalArgumentException e){
            out.println(TRY_AGAIN);
            askWizard(wizards);
        }catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Shows all the game information about school boards, islands, character cards, player's assistant deck and player's wallet.
     */
    @Override
    public void showGameInfo() {
        out.println(clientModel.toString());
    }

    /**
     * Shows all the game information about school boards, islands, character cards, player's assistant deck and player's wallet.
     * This method is more cleverly used by GUI.
     */
    @Override
    public void showGameInfo(int characterCardId) {
        showGameInfo();
    }


    /**
     * Shows to the players that a new game phase is starting (planning phase or action phase).
     * @param gameState the new phase of the game.
     */
    @Override
    public void showChangePhase(GameState gameState){
        out.println(Utility.ANSI_BOLDYELLOW+"\t"+gameState.name()+Utility.ANSI_RESET);
    }

    /**
     * Asks the player to choose an assistant card to play.
     * This assistant card can be chosen to view the assistant deck which is updated every time the player plays
     * an assistant card removing it.
     * If the player inputs a string which is not an assistant card type, the method will ask again to choose an assistant card.
     * The server will check if the assistant card chosen by the player is already played by him or already played in this turn
     * by other players.
     */
    @Override
    public void askAssistantCard() {
        AssistantDeck assistantDeck= clientModel.getAssistantDeck();
        try {
            out.print(Utility.ANSI_BOLDGREEN + "Your Deck:\n" + Utility.ANSI_RESET + Utility.ANSI_GREEN);
            for (AssistantCard assistantCard : assistantDeck.getAssistantDeck()) {
                out.print("\t"+Utility.ANSI_GREEN + assistantCard.toString() + Utility.ANSI_RESET);
            }
            out.print(Utility.ANSI_BOLDGREEN + " Please, draw an assistant card: " + Utility.ANSI_RESET);
            String input = readLine();
            AssistantCard assistantCardReply=null;
            for (AssistantCard assistantCard : assistantDeck.getAssistantDeck()){
                if ((assistantCard.getType()).equals(AssistantCardType.valueOf(input.toUpperCase())))
                    assistantCardReply = assistantCard;
            }
            notify(new AssistantCardReply(clientModel.getUsername(), assistantCardReply));
        } catch(IllegalArgumentException e){
            out.println(TRY_AGAIN);
            askAssistantCard();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Shows to other players which assistant card the current player has just chosen.
     * @param username      the current player's username.
     * @param assistantCard the assistant card just played.
     */
    @Override
    public void showInfoAssistantCardPlayed(String username, AssistantCard assistantCard) {
        out.println("Player: "+username+ " has played " + assistantCard.toString());
    }


    /**
     * Shows to the current player which assistant card he's just played.
     * @param assistantCard the assistant card played.
     */
    @Override
    public void showMyInfoAssistantCardPlayed(AssistantCard assistantCard){
        out.println("You have just played: "+assistantCard.getType());
    }

    /**
     * Asks the player to move a student from his entrance to his dining room or an island.
     * This request is repeated three times (two-players game) or four times (three-players game).
     * The player has to input the color of the student he wants to move and then the destination of the movement: d for dining room,
     * the id of the island for an island [COLOR, d/idIsland].
     * The server will check if the player has a student of the specified color in his entrance or if the island is existing.
     * During this method, the player can decide to play a character card. In that case, this method calls showAndPlayCharacterCard()
     * to show which character cards are available.
     * When the player finishes playing a character card, the server will go automatically on asking to move the students from entrance.
     */
    @Override
    public void askMoveStudentFromEntrance() {
        try {
            showGameSummary();
            out.println(Utility.ANSI_BOLDGREEN + "Choose three students to move from entrance to dining room or an island." + Utility.ANSI_RESET);
            out.println(Utility.ANSI_GREEN + "Help: To do so, type on each line [color of student, d (for dining room) ] or [color of student, island id]." + Utility.ANSI_RESET);
            if(clientModel.isExpertMode())
                out.print(Utility.ANSI_BOLDGREEN + "Please, enter data (digit '--playCC' if you want to play a character card): " + Utility.ANSI_RESET);
            else
                out.print(Utility.ANSI_BOLDGREEN + "Please, enter data: " + Utility.ANSI_RESET);
            String input = readLine();
            if(input.equals(CHARACHTER_CARD_COMMAND) && clientModel.isExpertMode())
                showAndPlayCharacterCard();
            else {
                String[] splittedInput = input.split(",");
                Color color = Color.valueOf(splittedInput[0].toUpperCase());
                if (removeSpaces(splittedInput[1]).equals("d"))
                    notify(new MoveStudentOntoDiningRoomMessage(clientModel.getUsername(), color));
                else
                    notify(new MoveStudentOntoIslandMessage(clientModel.getUsername(), Integer.parseInt(removeSpaces(splittedInput[1])), color));
            }
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            out.println(TRY_AGAIN);
            askMoveStudentFromEntrance();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Asks the player to choose how many steps mother nature has to do.
     * This method asks again the number of steps of mother nature if the player doesn't input a number.
     * The server will check if the number inserted by the player is correct (not negative and less than the maximum possible in that round).
     * During this method, the player can decide to play a character card. In that case, this method calls showAndPlayCharacterCard()
     * to show which character cards are available.
     * When the player finishes playing a character card, the server will go automatically on asking the number of steps mother nature has to do.
     */
    @Override
    public void askNumberStepsMotherNature() {
        try {
            showGameSummary();
            if( clientModel.getLastCharacterCardPlayed() != null && clientModel.getLastCharacterCardPlayed().getId() == 4)
                out.println("You have two extra steps!");
            if(clientModel.isExpertMode())
                out.print(Utility.ANSI_BOLDGREEN + "Enter the number of steps that mother nature has to do (press '--playCC' if you want to play a character card): " + Utility.ANSI_RESET);
            else
                out.print(Utility.ANSI_BOLDGREEN + "Enter the number of steps that mother nature has to do: " + Utility.ANSI_RESET);
            String input = readLine();
            if(input.equals(CHARACHTER_CARD_COMMAND) && clientModel.isExpertMode())
                showAndPlayCharacterCard();
            else
                notify(new MotherNatureReply(clientModel.getUsername(), Integer.parseInt(input)));
        } catch (NumberFormatException e) {
            out.println(TRY_AGAIN);
            askNumberStepsMotherNature();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    private void showGameSummary() {
        if(clientModel.isExpertMode()) {
            out.println(Utility.ANSI_BOLDGREEN + CHARACTER_CARDS + Utility.ANSI_RESET);
            out.println(clientModel.getAvailableCharacterCards().toString() + "\n");
        }
        out.print(Utility.ANSI_BOLDGREEN + "Available Cloud Tiles:" + Utility.ANSI_RESET);
        out.println(clientModel.printCloudTiles());
        out.print(Utility.ANSI_BOLDGREEN + "\nIslands:" + Utility.ANSI_RESET);
        out.println(clientModel.printIslands());
        out.print("\n");
        out.println(Utility.ANSI_BOLDGREEN +SCHOOLBOARD + Utility.ANSI_RESET);
        out.print(clientModel.printOtherSchoolBoards());
        out.print(clientModel.printYourSchoolBoard());
        out.println(Utility.ANSI_BOLDGREEN + CHARACTER_CARDS + Utility.ANSI_RESET);
        if(clientModel.isExpertMode())
            showUpdateWallet();
        out.println("Mother nature is on the island: " + clientModel.getMotherNaturePosition().getId());
        out.print("You have played: " + clientModel.getLastAssistantCardPlayed(clientModel.getUsername()));
    }

    /**
     * Asks the player to choose a cloud tile in order to draw the students on it.
     * Each cloud tile is identified by an ID.
     * This method asks again to choose the cloud tile if the player doesn't input a number.
     * The server will check if the id inserted corresponds to an available cloud tile and if this one is not already chosen by another player
     * in that round.
     * During this method, the player can decide to play a character card. In that case, this method calls showAndPlayCharacterCard()
     * to show which character cards are available.
     * When the player finishes playing a character card, the server will go automatically on asking to choose a cloud tile.
     */
    @Override
    public void askCloudTile() {
        try {
            if(clientModel.isExpertMode()) {
                out.println(Utility.ANSI_BOLDGREEN + CHARACTER_CARDS + Utility.ANSI_RESET);
                out.println(clientModel.getAvailableCharacterCards().toString() + "\n");
            }
            out.println(Utility.ANSI_BOLDGREEN +SCHOOLBOARD + Utility.ANSI_RESET);
            out.print(clientModel.printOtherSchoolBoards());
            out.println(clientModel.printYourSchoolBoard());
            out.println(Utility.ANSI_BOLDGREEN + "Useful game info:" + Utility.ANSI_RESET);
            if(clientModel.isExpertMode())
                showUpdateWallet();
            out.print(Utility.ANSI_BOLDGREEN + "\nAvailable Cloud Tiles:" + Utility.ANSI_RESET);
            out.println(clientModel.printCloudTiles());
            out.println(Utility.ANSI_BOLDGREEN + "Choose a cloud tile to pick students from." + Utility.ANSI_RESET);
            if(clientModel.isExpertMode())
                out.print(Utility.ANSI_BOLDGREEN + "Enter the id of the cloud tile you choose (press '--playCC' if you want to play a character card): " + Utility.ANSI_RESET);
            else
                out.print(Utility.ANSI_BOLDGREEN + "Enter the id of the cloud tile you choose: " + Utility.ANSI_RESET);
            String input = readLine();
            if(input.equals(CHARACHTER_CARD_COMMAND) && clientModel.isExpertMode())
                showAndPlayCharacterCard();
            else
                notify(new CloudTileReply(clientModel.getUsername(), Integer.parseInt(input)));
        }
        catch (NumberFormatException e){
            out.println(TRY_AGAIN);
            askCloudTile();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Asks the player to insert the parameters of the character card he chose in order to build the effect.
     * This method calls other methods according to the id of the character card the player chose.
     * @param id the id of the character card the player wants to play.
     */
    @Override
    public void askCharacterCardParameters(int id) {
        switch (id){
            case 1:
                askCharCard01();
                break;
            case 3:
                askCharCard03();
                break;
            case 5:
                askCharCard05();
                break;
            case 7:
                askCharCard07();
                break;
            case 9:
                askCharCard09();
                break;
            case 10:
                askCharCard10();
                break;
            case 11:
                askCharCard11();
                break;
            case 12:
                askCharCard12();
                break;
            default:
                showErrorMessage(ErrorTypeID.CHARACTER_CARD_NOT_AVAILABLE);
                showAndPlayCharacterCard();
                break;
        }
    }

    /**
     * Asks the parameters of the effect of character card 01.
     * The method checks only if the color inserted by the player exists in the game.
     * The server will check if the student of that color effectively exists.
     */
    public void askCharCard01(){
        out.print(Utility.ANSI_BLUE + "Choose the color of the student to move onto an island: " + Utility.ANSI_RESET);
        try {
            String input = readLine();
            Color studentToMove = Color.valueOf(input.toUpperCase());
            out.println(clientModel.printIslands());
            out.print(Utility.ANSI_BLUE + "Choose the id of the island: " + Utility.ANSI_RESET);
            input = readLine();
            notify(new CharacterCard01Reply(clientModel.getUsername(), studentToMove, Integer.parseInt(input)));
        } catch (NumberFormatException e){
            out.println(TRY_AGAIN);
            askCharCard01();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Asks the parameters of the effect of character card 03.
     * The method checks only if the player inserts a number.
     * The server will check if the island with the id inserted by the player exists.
     */
    public void askCharCard03(){
        out.println(clientModel.printIslands());
        out.print(Utility.ANSI_BLUE + "Select the island where you want to calculate the influence: " + Utility.ANSI_RESET);
        try {
            String input = readLine();
            notify(new CharacterCard03Reply(clientModel.getUsername(), Integer.parseInt(input)));
        } catch (NumberFormatException e){
            out.println(TRY_AGAIN);
            askCharCard03();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Asks the parameters of the effect of character card 05.
     * The method checks only if the player inserts a number. If not, it aks again to insert parameters for character card 05.
     * The server will check if the island with the id inserted by the player exists.
     */
    public void askCharCard05(){
        out.println(clientModel.printIslands());
        out.print(Utility.ANSI_BLUE + "Select the island where to put a no entry tile: " + Utility.ANSI_RESET);
        try {
            String input = readLine();
            notify(new CharacterCard05Reply(clientModel.getUsername(), Integer.parseInt(input)));
        } catch (NumberFormatException e){
            out.println(TRY_AGAIN);
            askCharCard05();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Asks the parameters of the effect of character card 07.
     * The method checks only if the player inserts a valid color of the game. If not, it aks again to insert parameters for character card 07.
     * The server will check if the player can swap the selected students and if the character card has the students that the player wants in his school board.
     * The player can swap up to three students. If he wants to swap less than three students, he can press enter when the method
     * asks him to choose a student from the card.
     */
    public void askCharCard07(){
        int repeat = 0;
        int [] fromCard = new int[5];
        int [] fromSchoolBoard = new int[5];
        out.println(Utility.ANSI_BLUE + "Swap up to three students between this card and your school board" + Utility.ANSI_RESET);
        out.println(clientModel.getCharacterCardById(7).studentsToString());
        out.println(clientModel.printYourSchoolBoard());
        do{
            try {
                repeat++;
                // choose the student to swap on card, then update fromCard array to send to Server
                out.print(Utility.ANSI_BLUE + ENTER_THE+ordinal(repeat) + " student from this card (press enter if you want to stop): " + Utility.ANSI_RESET);
                String inputFromCard = readLine();
                if(inputFromCard.isEmpty())
                    break;
                Color color = Color.valueOf(inputFromCard.toUpperCase());
                fromCard[color.ordinal()]++;

                // choose the student to swap on school board, then update fromSchoolBoard array to send to Server
                out.print(ENTER_THE+ordinal(repeat) + Utility.ANSI_BLUE + " student from your school board: " + Utility.ANSI_RESET);
                String inputFromSchoolBoard = readLine();
                color = Color.valueOf(inputFromSchoolBoard.toUpperCase());
                fromSchoolBoard[color.ordinal()]++;

            }catch (IllegalArgumentException e){
                out.println(TRY_AGAIN);
                askCharCard07();
            }
            catch (NullPointerException e){
                showAbortMessage();
            }
        }while (repeat < 3);
        notify(new CharacterCard07Reply(clientModel.getUsername(), fromCard, fromSchoolBoard, clientModel.getCharacterCardById(7).getStudents()));
    }

    /**
     * This a  helper method used by askCharCard07() and askCharCard10() to print number in an ordinal way.
     * For example, 1 --> 1st, 2-->2nd, ect...
     * @param i the number to convert from integer to its ordinal representation.
     * @return ordinal representation.
     */
    private static String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];
        }
    }

    /**
     * Asks the parameters of the effect of character card 09.
     * The method checks only if the player inserts a valid color of the game.
     * If not, it aks again to insert parameters for character card 09.
     */
    public void askCharCard09(){
        try {
            out.print("Islands:\n" + Utility.ANSI_RESET);
            out.println(clientModel.printIslands());
            out.print("\n");
            out.print(clientModel.printOtherSchoolBoards());
            out.println(clientModel.printYourSchoolBoard());
            out.print(Utility.ANSI_BLUE + "Enter the color that will not count in the calculation of influence: " + Utility.ANSI_RESET);
            String input = readLine();
            Color color = Color.valueOf(input.toUpperCase());
            notify(new CharacterCard09Reply(clientModel.getUsername(), color));
        } catch (IllegalArgumentException e){
            out.println(TRY_AGAIN);
            askCharCard09();
        }
        catch (NullPointerException e) {
            showAbortMessage();
        }
    }

    /**
     * Asks the parameters of the effect of character card 10.
     * The method checks only if the player inserts a valid color of the game. If not, it aks again to insert parameters for character card 10.
     * The server will check if the player has the selected students in his entrance and in his dining room.
     * The player can swap up to three students. If he wants to swap less than three students, he can press enter when the method
     * asks him to choose a student from his entrance.
     */
    public void askCharCard10(){
        int repeat = 0;
        int [] fromEntrance = new int[5];
        int [] fromDiningRoom = new int[5];
        out.println(clientModel.printYourSchoolBoard());
        out.println(Utility.ANSI_BLUE + "Swap up to two students between your entrance and your dining room" + Utility.ANSI_RESET);
        do{
            try {
                repeat++;
                // choose the student to swap on entrance, then update fromEntrance array to send to Server
                out.print(Utility.ANSI_BLUE + ENTER_THE+ ordinal(repeat) + " student from the entrance (press enter if you want to stop): " + Utility.ANSI_RESET);
                String inputFromEntrance = readLine();
                if(inputFromEntrance.isEmpty())
                    break;
                Color color = Color.valueOf(inputFromEntrance.toUpperCase());
                fromEntrance[color.ordinal()]++;

                // choose the student to swap on dining room, then update fromDiningRoom array to send to Server
                out.print(Utility.ANSI_BLUE + ENTER_THE+ ordinal(repeat) + " - student from your dining room: " + Utility.ANSI_RESET);
                String inputFromDiningRoom = readLine();
                color = Color.valueOf(inputFromDiningRoom.toUpperCase());
                fromDiningRoom[color.ordinal()]++;
            } catch (IllegalArgumentException e){
                out.println(TRY_AGAIN);
                askCharCard10();
            }
            catch (NullPointerException e){
                showAbortMessage();
            }
        }while (repeat < 2);
        notify(new CharacterCard10Reply(clientModel.getUsername(), fromEntrance, fromDiningRoom));
    }

    /**
     * Asks the parameters of the effect of character card 11.
     * The method checks only if the player inserts a valid color of the game. If not, it aks again to insert parameters for character card 11.
     * The server will check if the card has a student with the color selected by the player.
     */
    public void askCharCard11(){
        out.println(clientModel.getCharacterCardById(11).toString());
        out.println(clientModel.printYourSchoolBoard());
        out.print(Utility.ANSI_BLUE + "Enter the color of the student you want to move into your dining room: " + Utility.ANSI_RESET);
        try {
            String input = readLine();
            Color color = Color.valueOf(input.toUpperCase());
            notify(new CharacterCard11Reply(clientModel.getUsername(), color));
        } catch (IllegalArgumentException e){
            out.println(TRY_AGAIN);
            askCharCard11();
        }
        catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Asks the parameters of the effect of character card 12.
     * The method checks only if the player inserts a valid color of the game.
     * If not, it aks again to insert parameters for character card 12.
     */
    public void askCharCard12(){
        out.println(clientModel.printOtherSchoolBoards());
        out.println(clientModel.printYourSchoolBoard());
        out.println("Enter a color of student: every player (including yourself) must return 3 students of that color from their Dining Room to the bag.");
        out.println("If any player has fewer than 3 students of that color, return as many students as they have.");
        out.print(Utility.ANSI_BLUE + "Enter the color: " + Utility.ANSI_RESET);
        try {
            String input = readLine();
            Color color = Color.valueOf(input.toUpperCase());
            notify(new CharacterCard12Reply(clientModel.getUsername(), color));
        } catch (IllegalArgumentException e){
            out.println(TRY_AGAIN);
            askCharCard12();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    /**
     * Shows to the player which error occurs during the execution of a method or for an invalid input.
     * @param errorTypeID the error type that occurs during the execution.
     */
    @Override
    public void showErrorMessage(ErrorTypeID errorTypeID) {
        out.println(Utility.ANSI_RED + errorTypeID.getDescription() + Utility.ANSI_RESET);
        if(errorTypeID == ErrorTypeID.FULL_LOBBY)
            System.exit(1);
    }

    /**
     * Shows to the player that another player has disconnected.
     * Then, it disconnects him from the game.
     */
    @Override
    public void showDisconnectionMessage() {
        out.println(Utility.ANSI_RED + "A player has disconnected! The game is finished." + Utility.ANSI_RESET);
        inputReadThread.interrupt();
        System.exit(1);
    }

    /**
     * Shows to the player that the bag is now empty and so, the game will finish at the end of last player's turn.
     * If the bag becomes empty before the action phase, it communicates that the players will not draw any students from the cloud
     * tiles in this turn.
     */
    @Override
    public void showEmptyBagMessage() {
        out.println("The bag is empty! The game will finish at the end of last player's turn");
        out.println("Every player will not draw any students from the cloud tiles");
    }

    /**
     * Shows to the other players that the current player has just moved a student from his entrance to his dining room.
     * @param username        the current player's username.
     * @param moveDescription the description of the move.
     */
    @Override
    public void showInfoStudentIntoDiningRoom(String username, String moveDescription){
        if(!clientModel.getUsername().equals(username))
            out.println(moveDescription);
    }

    /**
     * Shows to the other players that the current player has just moved a student from his entrance to an island.
     * @param username        the current player's username.
     * @param moveDescription the description of the move.
     */
    @Override
    public void showInfoStudentOntoIsland(String username, String moveDescription){
        if(!clientModel.getUsername().equals(username))
            out.println(moveDescription);
    }

    /**
     * Shows to the players the position of mother nature.
     * @param islandId the id of the island with mother nature.
     */
    @Override
    public void showMotherNaturePosition(int islandId){
        out.println("Mother nature has been moved.\nMother nature is on the island: " + islandId);
    }

    /**
     * Shows to the players that an island has a new dominator. Then, it prints all the updated islands.
     * @param username the new island dominator's username.
     * @param islandId the id of the island.
     */
    @Override
    public void showChangeInfluenceMessage(String username, int islandId){
        if(username.equals(clientModel.getUsername()))
            out.println("You are the new dominator of the island " + islandId + ".");
        else
            out.println("The island " + islandId + " has a new dominator. " + "The new dominator is: " + username + ".");
        out.println(clientModel.printIslands());
    }

    /**
     * Shows to the players that an island is merged with another one. Then, it prints all the remaining islands.
     * @param island1Id the id of the island merged with mother nature.
     * @param island2Id the id of the island merged to delete.
     */
    @Override
    public void showMergeIslandsMessage(int island1Id, int island2Id){
        out.println("The island " + island1Id + " is now merged with the island " + island2Id + ".");
        out.println("These are the remaining islands: " + clientModel.printIslands());
    }

    /**
     * Shows to the players that a tower has been moved onto an island. Then, it prints all the islands.
     * @param islandId the id of the island where a new tower is built.
     */
    @Override
    public void showMoveTowerOntoIsland(int islandId) {
        out.println("A tower has been moved onto island "+islandId);
    }

    /**
     * Shows to the players that a tower has been moved back onto a school board.
     * @param username    the school board owner's username.
     * @param schoolBoard the school board with new towers.
     */
    @Override
    public void showMoveTowerOntoSchoolBoard(String username,SchoolBoard schoolBoard) {
        out.println("A tower has been moved back onto "+username+"'s school board");
    }

    /**
     * Shows to the other players which cloud tile the current player has just chosen.
     * @param username          the current player's username.
     * @param choiceDescription the description of the chosen cloud tile.
     */
    public void showInfoChosenCloudTile(String username, String choiceDescription){
        if(!clientModel.getUsername().equals(username))
            out.println(choiceDescription);
    }

    /**
     * Shows to the current player the new amount of coins in his wallet.
     */
    @Override
    public void showUpdateWallet(){
        out.println(clientModel.printWallet());
    }

    /**
     * Shows to the player that he has tied the game.
     * Then, it asks if he wants to play again.
     */
    @Override
    public void showTieMessage() {
        out.println(Utility.ANSI_YELLOW + "You have tied" + Utility.ANSI_RESET);
        askPlayAgain();
    }

    /**
     * Shows to the winner player that he won the game.
     * Then, it asks if he wants to play again.
     */
    @Override
    public void showWinMessage() {
        out.println(Utility.ANSI_YELLOW + "Congratulations! You're the winner!" + Utility.ANSI_RESET);
        askPlayAgain();
    }

    /**
     * Shows to the loser players who won the game.
     * @param winnerUsername the winner's username.
     */
    @Override
    public void showLoseMessage(String winnerUsername) {
        out.println(Utility.ANSI_YELLOW + "You Lose! The winner is: " + winnerUsername + Utility.ANSI_RESET);
        askPlayAgain();
    }

    /**
     * Asks the player if he wants to play again.
     * The player has to reply with "yes/y" if he wants to play again, "no/n" otherwise.
     * In case he wants to play again, this method clears the screen and asks the new username else it interrupts
     * the input read thread and closes the connection
     * with the server.
     */
    @Override
    public void askPlayAgain(){
        String results;
        do{
            out.print("Do you want to play again? [y/n]: ");
            results = readLine();
        }while(!results.equalsIgnoreCase("y") && !results.equalsIgnoreCase("n") && !results.equalsIgnoreCase("yes") && !results.equalsIgnoreCase("no"));
        if(results.equalsIgnoreCase("y") || results.equalsIgnoreCase("yes")){
            out.println(Utility.ANSI_CLEAR_CONSOLE);
            askUsername();
        }else{
            inputReadThread.interrupt();
            System.exit(0);
        }
    }

    /**
     * Shows to the player a generic message.
     * @param genericMessage the string to print.
     */
    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
    }

    /**
     * Shows to the current player the available character cards.
     * Then it asks the player to input the id of the character card he wants to play.
     * The method checks only if the player inserts a number.
     * The server will check if the number inserted by the player is an id of an available character card.
     * This method can be called once per turn by pressing "--playCC" during the action phase.
     */
    private void showAndPlayCharacterCard(){
        try {
            out.println(clientModel.printWallet());
            out.println(clientModel.getAvailableCharacterCards().toString());
            out.print(Utility.ANSI_BLUE + "Please, choose a character card id to play [Press enter for undo]: " + Utility.ANSI_RESET);
            String input=readLine();
            if(input.isEmpty())
                input = "-1";
            notify(new UseCharacterCardRequest(clientModel.getUsername(),Integer.parseInt(removeSpaces(input))));
        } catch (NullPointerException e) {
            System.exit(1);
        } catch(NumberFormatException e){
            out.println(TRY_AGAIN);
            showAndPlayCharacterCard();
        }
    }

    /**
     * Shows to the player that a fatal error occurs during the execution of the game and the connection with
     * the server dropped. Then, it closes the game.
     */
    public void showAbortMessage(){
        out.println(Utility.ANSI_RED + "Connection with server dropped! Quit..." + Utility.ANSI_RESET);
        System.exit(1);
    }

    /**
     * This method is useful for removing the spaces when the player inputs the student he wants to move from his entrance.
     * @param string the string to modify
     * @return the string without spaces.
     */
    private String removeSpaces(String string){
        return string.replace(" ", "");
    }

    /**
     * Read a string line using a separated thread.
     * @return the string in input.
     * @throws NullPointerException when a fatal error occurs.
     */
    public String readLine() throws NullPointerException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        this.inputReadThread = new Thread(futureTask);
        inputReadThread.start();
        try {
            return futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }catch(ExecutionException e){
            readLine();
            Thread.currentThread().interrupt();
        }
        throw new NullPointerException("The method had read a null string");
    }

    /**
     * @return the client model of the player.
     */
    public ClientModel getClientModel(){
        return clientModel;
    }
}