
public class CommandWords
{
    private static final String[] validCommands = {"pass", "buy", "state", "quit", "play", "help", "roll"};

    /**
     * The CommandWords constructor
     */
    public CommandWords()
    {

    }

    /**
     * @author Hussein
     * Checks whether the entered String is a valid command
     * @return true if the string is a valid command, false otherwise
     */
    public boolean isValidCommand(String inputString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(inputString)) return true;
        }
        return false;
    }
}
