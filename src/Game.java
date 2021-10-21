import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game
{
    private Parser parser;
    private boolean gameIsOver;
    private boolean inMenu;



    /**
     * @author Hussein
     * Constructor for the Game class
     */
    public Game()
    {
        parser = new Parser();
        gameIsOver = false;
        inMenu = true;


    }

    /**
     * @author Andre
     * Creates the game board
     */


    /**
     * @author Hussein
     * Starts the game
     */
    public void play()
    {
        while (!gameIsOver) {

            Command command = parser.getCommand();
            processCommand(command, 1);
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println("Thank you for playing. Good bye.");
        System.out.println("---------------------------------------------------------------");
    }

    /**
     * @Author: Hussein
     * Displays and allows the player to interact with the game menu
     */
    private void displayGameMenu()
    {
        System.out.println("---------------------------------------------------------------");
        System.out.println("Welcome to Monopoly!");
        System.out.println("For a list of all the commands, type 'help'");
        System.out.println("Type 'play' when you're ready");
        System.out.println("---------------------------------------------------------------");

        while (inMenu) {
            Command menuCommand = parser.getCommand();
            processCommand(menuCommand, 0);
        }
        play();
    }

    /**
     * @author Hussein
     * Processes a given command. It will be processed depending on the state of the game. If the player
     * is in the game menu for example, in-game commands like state, buy, etc. will not work
     * @param command The command to process.
     * @param state The state the game is in. 0 is game menu, 1 is during the game
     */
    private void processCommand(Command command, int state)
    {
        if(command.isUnknown()) {
            System.out.println("---------------------------------------------------------------");
            System.out.println("Invalid command");
            System.out.println("---------------------------------------------------------------");
        }

        String commandString = command.getCommandWord();

        if (commandString.equals("help")) printHelp();


        if (state == 0){
            if (commandString.equals("play")) inMenu = false;
            else if (commandString.equals("quit")) {
                inMenu = false;
                gameIsOver = true;
            }
            else {
                System.out.println("---------------------------------------------------------------");
                System.out.println("List of currently available commands: 'play', 'help', 'quit'");
                System.out.println("---------------------------------------------------------------");
            }
        }
        else if (state == 1){
            if (commandString.equals("quit")) gameIsOver = true;
        }
    }

    /**
     * @Author: Hussein
     * Prints a description of all the game commands
     */
    private void printHelp()
    {
        System.out.println("---------------------------------------------------------------");
        System.out.println("List of all commands are: ");
        System.out.println("pass: passes turn to the next player");
        System.out.println("buy: buys the property you are on (assuming it's available)");
        System.out.println("state: shows your current state (money, owned properties, etc.)");
        System.out.println("play: starts the game");
        System.out.println("quit: quits the game");
        System.out.println("---------------------------------------------------------------");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.displayGameMenu();
    }
}
