/**
 * @author Andre, Jack, Cassidy, Hussain
 * This class displays our game to the terminal
 *
 */

public class Game implements GameView
{
    private Parser parser;
    private GameModel gameModel;
    private boolean inMenu;
    private boolean inGame;
    private GameModel model;

    /**
     * @author Hussein
     * Constructor for the Game class
     */
    public Game()
    {
        parser = new Parser();
        inMenu = true;
        inGame = false;
        model = new GameModel();
        model.addGameModelView(this);
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
        System.out.println("Type 'start' when you're ready");
        System.out.println("---------------------------------------------------------------");

        while (inMenu) {
            Command menuCommand = parser.getCommand();
            processCommand(menuCommand, 0);
        }
    }


    /**
     * Displays and allows player to interact with the actual game so that they can play
     */

    private void inGameMenu(){
        System.out.println("---------------------------------------------------------------");
        System.out.println("Welcome to Monopoly!");
        System.out.println("Type 'roll' to start your turn ");
        System.out.println("---------------------------------------------------------------");

        while(inGame){
            System.out.println("\nIt is " + model.getActivePlayer().getName() +"'s turn, your current balance is " + model.getActivePlayer().getMoney());
            System.out.println("Roll when you are ready");
            Command gameCommand = parser.getCommand();
            processCommand(gameCommand, 1);
        }
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
        /*
        if(command.isUnknown()) {
            System.out.println("---------------------------------------------------------------");
            System.out.println("Invalid command");
            System.out.println("---------------------------------------------------------------");
        }
       */
        String commandString = command.getCommandWord();

        if (state == 0) {
            if (commandString.equals("start")) {
                inMenu = false;
                inGame = true;
                inGameMenu();
            }
            else if (commandString.equals("quit")) {
                inMenu = false;
                System.out.println("Thank you for playing");
            }
            else if (commandString.equals("help")) printHelp();
            else {
                System.out.println("---------------------------------------------------------------");
                System.out.println("List of currently available commands: 'start', 'help', 'quit'");
                System.out.println("---------------------------------------------------------------");
            }
        }
        else if (state == 1){
            if (commandString.equals("quit")) {
                inGame = false;
                System.out.println("Thank you for playing");
            }
            else if (commandString.equals("roll")){
                model.play();
            }
            else if (commandString.equals("help")) printHelp();
            else if (commandString.equals("state")) printState();
            else System.out.println("Invalid command! Try 'roll', 'state', 'help', or 'quit'!");
        }
        else if(state == 2){
            if(commandString.equals("buy")){
                if (model.getActivePlayer().getMoney() > model.getCurrentCard().getCost()) {
                    model.buyProperty();
                    System.out.println("Your money is now: " + model.getActivePlayer().getMoney());
                }
                else System.out.println("Not enough money");
            }
            else if (commandString.equals("pass")) {
                System.out.println("You're passing");
            }
            else if (commandString.equals("help")) {
                printHelp();
            }
            else if(commandString.equals("state")){
                printState();
            }
            else if(commandString.equals("quit")){
                inGame = false;
            }
            else{
                System.out.println("Invalid command! Try 'buy', 'pass', 'state', or 'quit'!");
            }
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

    /**
     * Prints a description of the state
     */
    private void printState(){
        System.out.println(model.getActivePlayer().getName());
        System.out.println("Your current balance is: $" + model.getActivePlayer().getMoney());
        System.out.println("Your number of properties is " + model.getActivePlayer().getProperties().size() + " ");
        for (int i = 0; i < model.getActivePlayer().getProperties().size(); i++) {
            if(i < model.getActivePlayer().getProperties().size()-1 ) {
                System.out.println(i+1 + ": " + model.getActivePlayer().getProperties().get(i).getName() + ",");
            }else{
                System.out.println(i+1 + ": " + model.getActivePlayer().getProperties().get(i).getName() + "");
            }
        }
    }

    /**
     * Prints when a player goes bankrupt
     * @param playerName the string of the player that went bankrupt
     */
    public static void printBankruptcy(String playerName){
        System.out.println(playerName + " went bankrupt");
    }

/**
 * This class handles the update to the view of the game class
     * @param e is a game event that holds useful information
     */
    @Override
    public void handleGameStatusUpdate(GameEvent e) {
        this.gameModel = (GameModel) e.getSource();
        if (!e.getStatus().equals(GameModel.Status.UNDECIDED)){
            System.out.println("game is over");
            inGame = false;
        }
        else {
            System.out.println(gameModel.getActivePlayer().getName() + " rolled " + e.getRoll()[0] + " " + e.getRoll()[1]);
            System.out.println("The card you are on is " + e.getCard().getName() + " cost: " + e.getCard().getCost() + " color: " + e.getCard().getColor());

            if (!e.getCard().isOwned()) {
                System.out.println("Would you like to buy this property? or pass");
                Command command = parser.getCommand();
                processCommand(command, 2);
                while (!command.getCommandWord().equals("buy") && !command.getCommandWord().equals("pass") && !command.getCommandWord().equals("quit")){
                    command = parser.getCommand();
                    processCommand(command, 2);
                }
            } else {
                System.out.println(e.getCard().getOwner().getName() + " owns this property lol");
                gameModel.getActivePlayer().payRent(e.getCard().getOwner(), e.getCard());
                System.out.println("you paid " + e.getCard().getOwner().getName() + " " + e.getCard().getRent() + " dollars");
                System.out.println("You now have " + gameModel.getActivePlayer().getMoney() + " dollars");
            }

        }
    }

    /**
     * this method starts the game
     */
    public void play(){
        displayGameMenu();
    }

    /**
     * the main method
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
