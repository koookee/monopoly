/**
 * @author Andre, Jack, Cassidy, Hussain
 * The parser that scanes the game commands
 *
 */
import java.util.Scanner;

public class Parser
{
    private CommandWords validCommands;
    private Scanner scanner;

    /**
     *
     * The constructor for the Parser class
     */
    public Parser()
    {
        validCommands = new CommandWords();
        scanner = new Scanner(System.in);
    }

    /**
     * Allows the user to enter a command
     * @return a Command object that contains the user input
     */
    public Command getCommand()
    {
        String commandString;
        commandString = scanner.nextLine();

        if(validCommands.isValidCommand(commandString)) {
            return new Command(commandString);
        }
        else {
            return new Command("");
        }
    }
}
