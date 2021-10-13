import java.util.Scanner;

public class Game
{
    Parser parser;

    public Game()
    {
        parser = new Parser();
    }

    public void play()
    {
        boolean finished = false;
        /*
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
         */
        System.out.println("---------------------------------------------------------------");
        System.out.println("Thank you for playing. Good bye.");
        System.out.println("---------------------------------------------------------------");
    }

    /**
     * Author: Hussein
     * Displays and allows the player to interact with the game menu
     */
    private void displayGameMenu()
    {
        System.out.println("---------------------------------------------------------------");
        System.out.println("Welcome to Monopoly!");
        System.out.println("For a list of all the commands, type 'help'");
        System.out.println("Type 'play' when you're ready");
        System.out.println("---------------------------------------------------------------");
        Scanner menuCommand = new Scanner(System.in);
        String menuCommandString = new String();
        menuCommandString = menuCommand.nextLine();
        while (!menuCommandString.equals("play")) {
            if (menuCommandString.equals("help")) printHelp();
            else{
                System.out.println("---------------------------------------------------------------");
                System.out.println("Invalid command");
                System.out.println("---------------------------------------------------------------");
            }
            menuCommandString = menuCommand.nextLine();
        }
        this.play();
    }

    /**
     * Author: Hussein
     * Prints a description of the game commands
     */
    private void printHelp()
    {
        System.out.println("---------------------------------------------------------------");
        System.out.println("List of commands are: ");
        System.out.println("pass: passes turn to the next player");
        System.out.println("buy: buys the property you are on (assuming it's available)");
        System.out.println("state: shows your current state (money, owned properties, etc.)");
        System.out.println("quit: quits the game");
        System.out.println("---------------------------------------------------------------");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.displayGameMenu();
    }
}
