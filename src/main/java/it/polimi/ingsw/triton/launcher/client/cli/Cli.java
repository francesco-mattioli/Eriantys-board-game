package it.polimi.ingsw.triton.launcher.client.cli;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.*;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.Utility;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.*;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply.*;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;
import it.polimi.ingsw.triton.launcher.client.Client;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.client.view.ClientView;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class Cli extends Observable<Message> implements ClientView{
    private final PrintStream out;
    private ClientModel clientModel;
    Thread inputReadThread;
    private static final String TRY_AGAIN = "Try again...";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLDGREEN = "\u001B[1;32m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BOLDYELLOW = "\u001B[1;33m";
    public static final String ANSI_PINK = "\u001B[35m";
    public static final String ANSI_CLEAR_CONSOLE = "\033[H\033[2J";
    public static final String commandForCharacterCard="--playCC";


    /**
     * Instantiates a new Cli;
     * The PrintStream out variable is set to System.out, by this way System.out.println() is not replicated multiple times.
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
        String defaultIp = "localhost";
        String ip;
        while (true){
            out.print("Please, enter the ip address of the server [default: " + defaultIp + "]:");
            ip = readLine();
            if(ip.isEmpty() || ip.equalsIgnoreCase("localhost")) {
                ip = defaultIp;
                break;
            }
            else if(isCorrectIpAddress(ip))
                break;
        }
        notify(new UpdatedServerInfoMessage(ip));
    }

    /**
     * This method analyzes the string inserted by the user, applying a pattern that allows the system to know if the
     * string corresponds to an IP address format.
     * @param address the string with the IP address to analyze.
     * @return true if the string corresponds to an IP address, false otherwise.
     */
    private boolean isCorrectIpAddress(String address){
        String pattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return address.matches(pattern);
    }

    /**
     * Asks the player to insert the username he wants in the game.
     * After that, the server will check if the username is forbidden or already used and, in that case, it'll send
     * back another request asking the username.
     */
    @Override
    public void askUsername() {
        out.print(ANSI_BOLDGREEN + "Enter your username: " + ANSI_RESET);
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
     * The first player hass to insert 'E' if he wants the expert mode, 'N' otherwise.
     * @return true if the player wants the expert mode, false otherwise.
     */
    private boolean askGameMode() {
        String input="";
        out.println("You are the first player");
        try{
            do{
                out.print(ANSI_BOLDGREEN + "Please, select a game mode [N for normal mode, E for expert mode]: " + ANSI_RESET);
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
                out.print(ANSI_BOLDGREEN + "Enter number of players [2 or 3]: " + ANSI_RESET);
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
        /*if(maxNumberPlayers-onlineNicknames.size() != 0)
            out.println("There are " + onlineNicknames.size() +
                " out of " + maxNumberPlayers + " players connected; Waiting for " + (maxNumberPlayers-onlineNicknames.size()) + " players...");
        else
            out.println("There are " + onlineNicknames.size() +
                    " out of " + maxNumberPlayers + " players connected; The game is starting...");*/
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
            out.print(ANSI_BOLDGREEN + "Choose a tower color [ ");
            for(int i=0;i< chosenTowerColors.length;i++){
                if(!chosenTowerColors[i]) {
                    out.print(i + " for " + TowerColor.values()[i]);
                    if(i<chosenTowerColors.length-1)
                        out.print(", ");
                }
            }
            out.print(" ]: " + ANSI_RESET);
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
            out.print(ANSI_BOLDGREEN + "Choose a Wizard " + Utility.printAvailableWizards(wizards));
            out.print(": " + ANSI_RESET);
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
     * Shows to the players that a new game phase is starting (planning phase or action phase).
     * @param gameState the new phase of the game.
     */
    @Override
    public void showChangePhase(GameState gameState){
        out.println(ANSI_BOLDYELLOW+" ---"+gameState.name()+"---"+ANSI_RESET);
    }

    /**
     * Asks the player to choose an assistant card to play.
     * This assistant card can be chosen viewing the assistant deck which is updated every time the player plays
     * an assistant card removing it.
     * If the player inputs a string which is not an assistant card type, the method will ask again to choose an assistant card.
     * The server will check if the assistant card chosen by the player is already played by him or already played in this turn
     * by other players.
     */
    @Override
    public void askAssistantCard() {
        AssistantDeck assistantDeck= clientModel.getAssistantDeck();
        try {
            out.print(ANSI_BOLDGREEN + "Draw an Assistant Card\n[ " + ANSI_RESET + ANSI_GREEN);
            for (AssistantCard assistantCard : assistantDeck.getAssistantDeck()) {
                out.print(assistantCard.toString());
            }
            out.print(ANSI_BOLDGREEN + " ]: " + ANSI_RESET);
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
            out.println(ANSI_GREEN + "Choose three students to move from entrance to dining room or an island!");
            out.print("Islands:\n" + ANSI_RESET);
            out.println(clientModel.printIslands());
            out.print("\n");
            out.println(ANSI_GREEN + "SchoolBoards:\n" + ANSI_RESET);
            out.print(clientModel.printOtherSchoolBoards());
            out.println(clientModel.printYourSchoolBoard());
            out.println("To do so, type on each line [color of student, d (for dining room) ] or [color of student, island id]");
            out.print(ANSI_BOLDGREEN + "Please, enter data: " + ANSI_RESET);
            String input = readLine();
            if(input.equals(commandForCharacterCard)&& clientModel.isExpertMode())
                showAndPlayCharacterCard();
            else {
                String[] splittedInput = input.split(",");
                Color color = Color.valueOf(splittedInput[0].toUpperCase());
                if (removeSpaces(splittedInput[1]).equals("d"))
                    notify(new MoveStudentOntoDiningRoomMessage(clientModel.getUsername(), color));
                else
                    notify(new MoveStudentOntoIslandMessage(clientModel.getUsername(), Integer.parseInt(removeSpaces(splittedInput[1])), color));
            }
        } catch (IllegalArgumentException e) {
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
            out.print("Islands:\n" + ANSI_RESET);
            out.println(clientModel.printIslands());
            out.print("\n");
            out.print(clientModel.printOtherSchoolBoards());
            out.println(clientModel.printYourSchoolBoard());
            out.println("Mother nature is on the island: " + clientModel.getMotherNaturePosition().getId());
            out.print(ANSI_BOLDGREEN + "Enter the number of steps that mother nature has to do: " + ANSI_RESET);
            String input = readLine();
            if(input.equals(commandForCharacterCard)&& clientModel.isExpertMode())
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
            out.println(ANSI_GREEN + "Choose a cloud tile to withdraw the students!");
            out.print("Islands:\n" + ANSI_RESET);
            out.println(clientModel.printIslands());
            out.print("\n");
            out.print(clientModel.printOtherSchoolBoards());
            out.println(clientModel.printYourSchoolBoard());
            out.print("CloudTiles:" + ANSI_RESET);
            out.println(clientModel.printCloudTiles());
            out.print(ANSI_BOLDGREEN + "Enter the id of the cloud tile you choose: " + ANSI_RESET);
            String input = readLine();
            if(input.equals(commandForCharacterCard)&& clientModel.isExpertMode())
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

    public void askCharCard01(){
        out.print(ANSI_BLUE + "Choose the color of the student to move onto an island: " + ANSI_RESET);
        try {
            String input = readLine();
            Color studentToMove = Color.valueOf(input.toUpperCase());
            out.println(clientModel.printIslands());
            out.print(ANSI_BLUE + "Choose the id of the island: " + ANSI_RESET);
            input = readLine();
            notify(new CharacterCard01Reply(clientModel.getUsername(), studentToMove, Integer.parseInt(input)));
        } catch (NumberFormatException e){
            out.println(TRY_AGAIN);
            askCharCard01();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    public void askCharCard03(){
        out.print(ANSI_BLUE + "Select the island where you want to calculate the influence: " + ANSI_RESET);
        try {
            out.println(clientModel.printIslands());
            String input = readLine();
            notify(new CharacterCard03Reply(clientModel.getUsername(), Integer.parseInt(input)));
        } catch (NumberFormatException e){
            out.println(TRY_AGAIN);
            askCharCard03();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    public void askCharCard05(){
        out.print(ANSI_BLUE + "Select the island where to put a no entry tile: " + ANSI_RESET);
        try {
            out.println(clientModel.printIslands());
            String input = readLine();
            notify(new CharacterCard05Reply(clientModel.getUsername(), Integer.parseInt(input)));
        } catch (NumberFormatException e){
            out.println(TRY_AGAIN);
            askCharCard05();
        } catch (NullPointerException e){
            showAbortMessage();
        }
    }

    public void askCharCard07(){
        int repeat = 0;
        int [] fromCard = new int[5];
        int [] fromSchoolBoard = new int[5];
        out.println(ANSI_BLUE + "Swap up to three students between this card and your school board" + ANSI_RESET);
        out.println(clientModel.getCharacterCardById(7).studentsToString());
        out.println(clientModel.printYourSchoolBoard());
        do{
            try {
                repeat++;
                // choose the student to swap on card, then update fromCard array to send to Server
                out.print(ANSI_BLUE + "Enter the "+ordinal(repeat) + " student from this card (press enter if you want to stop): " + ANSI_RESET);
                String inputFromCard = readLine();
                if(inputFromCard.isEmpty())
                    break;
                Color color = Color.valueOf(inputFromCard.toUpperCase());
                fromCard[color.ordinal()]++;

                // choose the student to swap on school board, then update fromSchoolBoard array to send to Server
                out.print("Enter the "+ordinal(repeat) + ANSI_BLUE + " student from your school board: " + ANSI_RESET);
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
     * This a  helper method used by askCharCard07() and askCharCard10() to print number in an ordinal way
     * For example, 1 --> 1st, 2-->2nd, ect...
     * @param i the number to convert from integer to its ordinal representation
     * @return ordinal representation
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

    public void askCharCard09(){
        out.print(ANSI_BLUE + "Enter the color that will not count in the calculation of influence: " + ANSI_RESET);
        try {
            out.print("Islands:\n" + ANSI_RESET);
            out.println(clientModel.printIslands());
            out.print("\n");
            out.print(clientModel.printOtherSchoolBoards());
            out.println(clientModel.printYourSchoolBoard());
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

    public void askCharCard10(){
        int repeat = 0;
        int [] fromEntrance = new int[5];
        int [] fromDiningRoom = new int[5];
        out.println(clientModel.printYourSchoolBoard());
        out.println(ANSI_BLUE + "Swap up to two students between your entrance and your dining room" + ANSI_RESET);
        do{
            try {
                repeat++;
                // choose the student to swap on entrance, then update fromEntrance array to send to Server
                out.print("Enter the "+ordinal(repeat) + ANSI_BLUE + " student from the entrance (press enter if you want to stop): " + ANSI_RESET);
                String inputFromEntrance = readLine();
                if(inputFromEntrance.isEmpty())
                    break;
                Color color = Color.valueOf(inputFromEntrance.toUpperCase());
                fromEntrance[color.ordinal()]++;

                // choose the student to swap on dining room, then update fromDiningRoom array to send to Server
                out.print("Enter the "+ordinal(repeat) + ANSI_BLUE + " - student from your dining room: " + ANSI_RESET);
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

    public void askCharCard11(){
        out.println(clientModel.getCharacterCardById(11).toString());
        out.println(clientModel.printYourSchoolBoard());
        out.print(ANSI_BLUE + "Enter the color of the student you want to move into your dining room: " + ANSI_RESET);
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

    public void askCharCard12(){
        out.println(clientModel.printOtherSchoolBoards());
        out.println(clientModel.printYourSchoolBoard());
        out.println("Enter a color of student: every player (including yourself) must return 3 students of that color from their Dining Room to the bag.");
        out.println("If any player has fewer than 3 students of that color, return as many students as they have.");
        out.print(ANSI_BLUE + "Enter the color: " + ANSI_RESET);
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

    @Override
    public void showErrorMessage(ErrorTypeID errorTypeID) {
        out.println(ANSI_RED + errorTypeID.getDescription() + ANSI_RESET);
        if(errorTypeID == ErrorTypeID.FULL_LOBBY)
            System.exit(1);
    }

    @Override
    public void showDisconnectionMessage() {
        out.println(ANSI_RED + "A player has disconnected! The game is finished." + ANSI_RESET);
        inputReadThread.interrupt();
        System.exit(1);
    }

    @Override
    public void showEmptyBagMessage() {
        out.println("The bag is empty! The game will finish at the end of last player's turn");
        out.println("Every player will not draw any students from the cloud tiles");
    }

    @Override
    public void showInfoStudentIntoDiningRoom(String username, String moveDescription){
        if(!clientModel.getUsername().equals(username))
            out.println(moveDescription);
    }

    @Override
    public void showInfoStudentOntoIsland(String username, String moveDescription){
        if(!clientModel.getUsername().equals(username))
            out.println(moveDescription);
    }

    @Override
    public void showMotherNaturePosition(int islandId){
        out.println("Mother nature has been moved.\nMother nature is on the island: " + islandId);
    }

    @Override
    public void showChangeInfluenceMessage(String username, int islandId){
        if(username.equals(clientModel.getUsername()))
            out.println("You are the new dominator of the island " + islandId + ".");
        else
            out.println("The island " + islandId + " has a new dominator. " + "The new dominator is: " + username + ".");
        out.println(clientModel.printIslands());
    }

    @Override
    public void showMergeIslandsMessage(int island1Id, int island2Id){
        out.println("The island " + island1Id + " is now merged with the island " + island2Id + ".");
        out.println("These are the remaining islands: " + clientModel.printIslands());
    }
    @Override
    public void showMoveTowerOntoIsland(int islandId) {
        out.println("A tower has been moved onto island "+islandId);
        clientModel.printIslands();
    }

    @Override
    public void showMoveTowerOntoSchoolBoard(String username,SchoolBoard schoolBoard) {
        out.println("A tower has been moved back onto "+username+"'s school board");
    }

    public void showInfoChosenCloudTile(String username, String choiceDescription){
        if(!clientModel.getUsername().equals(username))
            out.println(choiceDescription);
    }

    @Override
    public void showUpdateWallet(){
        out.println(clientModel.printWallet());
    }

    @Override
    public void showTieMessage() {
        out.println(ANSI_YELLOW + "You have tied" + ANSI_RESET);
        askPlayAgain();
    }

    @Override
    public void showWinMessage() {
        out.println(ANSI_YELLOW + "Congratulations! You're the winner!" + ANSI_RESET);
        askPlayAgain();
    }

    @Override
    public void showLoseMessage(String winnerUsername) {
        out.println(ANSI_YELLOW + "You Lose! The winner is: " + winnerUsername + ANSI_RESET);
        askPlayAgain();
    }

    @Override
    public void askPlayAgain(){
        String results = "";
        do{
            out.print("Do you want to play again? [y/n]: ");
            results = readLine();
        }while(!results.equalsIgnoreCase("y") && !results.equalsIgnoreCase("n") && !results.equalsIgnoreCase("yes") && !results.equalsIgnoreCase("no"));
        if(results.equalsIgnoreCase("y") || results.equalsIgnoreCase("yes")){
            out.println(ANSI_CLEAR_CONSOLE);
            askUsername();
        }else{
            inputReadThread.interrupt();
            System.exit(0);
        }
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
    }

    private void showAndPlayCharacterCard(){
        try {
            out.println(clientModel.printWallet());
            out.println(clientModel.getAvailableCharacterCards().toString());
            out.print(ANSI_BLUE + "Please, choose a character card id to play: " + ANSI_RESET);
            String input=readLine();
            notify(new UseCharacterCardRequest(clientModel.getUsername(),Integer.parseInt(removeSpaces(input))));
        } catch (NullPointerException e) {
            System.exit(1);
        } catch(NumberFormatException e){
            out.println(TRY_AGAIN);
            showAndPlayCharacterCard();
        }
    }

    public void showAbortMessage(){
        out.println(ANSI_RED + "Connection with server dropped! Quit..." + ANSI_RESET);
        System.exit(1);
    }

    private String removeSpaces(String string){
        return string.replace(" ", "");
    }

    /**
     * Read a string line using a separated thread
     *
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws NullPointerException the null pointer exception
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


    public ClientModel getClientModel(){
        return clientModel;
    }

}


