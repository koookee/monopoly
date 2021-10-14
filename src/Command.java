public class Command {
    private String commandWord;

    /**
     * @author: Hussein
     * The constructor for the command class
     * @param firstWord the string that the user entered
     */
    public Command(String firstWord)
    {
        this.commandWord = firstWord;
    }

    /**
     * @author Hussein
     * Gets the command word entered by the user
     * @return The command word
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * @author Hussein
     * @return true if this command was not understood
     */
    public boolean isUnknown()
    {
        return commandWord.equals("");
    }
}
