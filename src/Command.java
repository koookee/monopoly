/**
 * @author Andre, Jack, Cassidy, Hussain
 * This class is used for reading the unputs of the players.
 *
 */
public class Command {
    private String commandWord;

    /**
     * The constructor for the command class
     * @param firstWord the string that the user entered
     */
    public Command(String firstWord)
    {
        this.commandWord = firstWord;
    }

    /**
     * Gets the command word entered by the user
     * @return The command word
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return true if this command was not understood
     */
    public boolean isUnknown()
    {
        return commandWord.equals("");
    }
}
