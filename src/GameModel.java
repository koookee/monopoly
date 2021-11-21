/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class represents the model of our monoploy game
 *
 */

import java.awt.*;
import java.util.*;

public class GameModel {
    private GameModel.Status status;
    private ArrayList<GameView> views;
    private Map<Integer, Card> gameBoard;
    private ArrayList<Player> players;
    private int currTurn;
    private Player activePlayer;
    private Card currentCard;
    private int numTimesRolledDouble;
    private boolean hasNotRolled;
    int dice1;
    int dice2;
    int roll;

    /**
     * The constructor for the GameModel class
     * We set the amount of players to 4 as the default since we haven't added the ai yet
     */

    public GameModel() {
        this.currTurn = 0;
        this.status = GameModel.Status.UNDECIDED;
        this.views = new ArrayList();
        this.gameBoard = new HashMap();
        this.players = new ArrayList<>();
        this.numTimesRolledDouble = 0;
        this.hasNotRolled = true;
        this.createGameBoard();
    }

    /**
     * This method adds a player to our list of players
     * We set the active player to the first player since they are Player 1
     * @param name is the String of the name the player wants
     */
    private void addPlayer(String name){
        players.add(new Player(name));
        if(players.size()==1) activePlayer = players.get(0);
    }

    /**
     * This method creates the gameboard for the players
     */
    public void createGameBoard(){
        // As of right now "Go" does not exist
        String[] streetNames = {"Go","Sparks Street","Lebreton Flats","wellington Street","laurier Avenue","Bytown&Prescott Railway",
                "waller Street","bronson Avenue","Jail","Hurdman Road","Canadian Pacific Railway","Lett Street","lampman Crescent",
                "macKay Street","slater Street","Canadian National Railway","thompson Street","sweetLand Avenue","sloper Place",
                "Water Works", "New York Central Railway","perly Drive","morrison Street","Go to Jail","keefer Street","mcLeod Street","parliament Hill",
                "Electric Company","rideau Canal", "street 21", "street 22"};
        int[] costs = {0,60,60,100,100,200,120,180,0,180,200,200,220,220,240,200,260,260,280,100,200,300,300,0,320,350,400,200,420,450,500};

        Color[] colors = {Color.white,new Color(150, 75, 0),new Color(150, 75, 0), Color.CYAN,Color.CYAN,Color.black,Color.CYAN,Color.pink,Color.blue,Color.pink,Color.black,
                Color.pink,Color.orange,Color.orange,Color.orange, Color.black, Color.red,Color.red, Color.red,Color.white,Color.black, Color.yellow,Color.yellow,Color.blue,Color.yellow,Color.green,
                Color.green,Color.white, Color.green,new Color(0,135,225),new Color(0,135,225)};

        for (int i = 0; i < streetNames.length; i++) {
            if (i%5==0 && i<21 && i>1){
                gameBoard.put(i,new Card(streetNames[i],costs[i],colors[i], Card.CardType.railroad));
            }else if(i== 18 || i == 25) {
                gameBoard.put(i, new Card(streetNames[i], costs[i], colors[i], Card.CardType.ultility));
            }else if(i == 8 || i == 23){
                gameBoard.put(i, new Card(streetNames[i], costs[i], colors[i], Card.CardType.jail));
            }
            else{
                gameBoard.put(i,new Card(streetNames[i],costs[i],colors[i], Card.CardType.property));
            }
        }
    }

    /**
     * The getter for the game board
     * @return the Map<Integer, Card> of the game board
     */
    public Map<Integer,Card> getGameBoard(){
        return this.gameBoard;
    }

    /**
     * This method adds a view to the model
     * @param view a view that the model will notify
     */
    public void addGameModelView(GameView view){
        this.views.add(view);
    }

    /**
     * this method changes the players turn
     */
    public void changeTurn(){
        currTurn = (currTurn + 1) % players.size();
        while (!players.get(currTurn).isPlaying()) currTurn = (currTurn + 1) % players.size(); // Skips the players that are bankrupt
        activePlayer = players.get(currTurn);
    }

    /**
     * Getter for the current turn
     * @return the current turn
     */
    public int getCurrTurn(){
        return currTurn;
    }

    /**
     * This method updates the status of the game
     * to show if a player is in or out. It also
     * sets the game state to Winning if there's 1
     * player left
     * @return the int of whether a player went bankrupt or not
     */
    private int updateStatus(){

        int removePlayer = -1;
        int playerLost = 0;
        int numToReturn = 0;


        for(Player x: players){
            if(x.getMoney()<=0 && x.isPlaying()){
                //removePlayer = players.indexOf(x);
                x.setPlaying(false);
                playerLost = 1;
                for(Card c : x.getProperties()){
                    c.setOwned(false);
                }

            }
        }

        int currentPlayers = 0;

        for (Player p : players) {
            if(p.isPlaying()) currentPlayers++;

        }

        if(playerLost == 1) {
            numToReturn = 1;
            //players.remove(removePlayer);
        }
        if(currentPlayers == 1){
            status = Status.WINNER;
        }
        return numToReturn;
    }


    /**
     * This method is called to play the game depending on the choice command
     * @param choice the int that determines the action to take
     */
    public void play(int choice){

        if (choice == 1 && hasNotRolled && status.name().equals("UNDECIDED")) {

            dice1 = (int) (Math.random() * 6 + 1);
            dice2 = (int) (Math.random() * 6 + 1);
            roll = dice1 + dice2;

            if(this.activePlayer.getPosition() + roll > 30){ // PASSING GO
                int currentMoney = this.activePlayer.getMoney();
                this.activePlayer.setMoney(currentMoney + 200);
            }



            if(getActivePlayer().getIsInJail() != 0){       // JAIL TIME
                if(dice1 == dice2 || getActivePlayer().getIsInJail() > 3 ){
                    getActivePlayer().setIsInJail(0);
                }
                else{
                    int current = this.activePlayer.getIsInJail();
                    this.activePlayer.setIsInJail(current += 1 );
                    play(3);
                }
            }

            activePlayer.setPrevPosition(activePlayer.getPosition());
            activePlayer.setPosition((activePlayer.getPosition() + roll) % gameBoard.size());
            currentCard = gameBoard.get(activePlayer.getPosition());
            //If player X turn set there position to += the roll amount

            play(4);

            int gameState = this.updateStatus();
            for (GameView view : views) {
                if (gameState == 1) {
                    view.announceBankruptcy(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                    play(3);
                }
                else if (dice1 == dice2 && numTimesRolledDouble <= 3) {
                    this.numTimesRolledDouble++;
                    hasNotRolled = true;
                } else {
                    hasNotRolled = false;
                }
                if (status.name().equals("WINNER")) {
                    view.announceWinner(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                }
            }
        } else if (choice == 2 && status.name().equals("UNDECIDED")) { // Ask if they're sure they want to pass
            for (GameView view : views) {
                view.announcePlayerPass(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
            }
        } else if (choice == 3 && status.name().equals("UNDECIDED")) { // Player confirms they want to pass
            this.updateStatus();
            this.changeTurn();
            numTimesRolledDouble = 0;
            hasNotRolled = true;
            for (GameView view : views) {
                view.handleGameStatusUpdate(new GameEvent(this, status, currentCard, new int[]{0, 0})); // Player didn't roll yet
            }
        } else if (choice == 4 && status.name().equals("UNDECIDED")) { // Buying property
            int result = currentCard.functionality(activePlayer);
            for (GameView view : views) {
                view.handleGameStatusUpdate(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                if (result == 0) {
                    view.unownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                } else if (result == 2) {
                    view.ownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                } else if (result == 3){
                    view.announceToJail(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                }
            }
        } else if (choice == 5 && status.name().equals("UNDECIDED")) { // Confirms buying
            int result = currentCard.functionality(activePlayer);
            if (result == 0) buyProperty();
        } else if( choice == 6 && status.name().equals("UNDECIDED")) {
            this.activePlayer.setPosition(8);
            this.activePlayer.setIsInJail(1);
        }

    }

    /**
     * this method is used to buy a property for a player
     */
    public void buyProperty(){
        if (!currentCard.getName().equals("Go")) this.activePlayer.buyCard(currentCard);
    }

    /**
     * Getter for players
     * @return list of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Setter for Current Card.
     */
    public void setCurrentCard(int i ) {
        this.currentCard = gameBoard.get(i);
    }

    /**
     * Getter for Current Card.
     * @return Card
     */
    public Card getCurrentCard() {
        return currentCard;
    }

    /**
     * getter for the activePlayer
     * @return Returns a Player that is the current player
     */
    public Player getActivePlayer() {
        return players.get(currTurn);
    }

    /**
     * Gets the only player left in the game
     * @return the winner of type Player
     */
    public Player getWinner(){
        for (Player player : players){
            if (player.isPlaying()) return player;
        }
        return null;
    }

    /**
     * Adds a number of players to the game
     * @param playerNum the int for the number of players to be added
     */
    public void addPlayers(int playerNum) {
        if(playerNum<2 || playerNum>4){
            return;
        }
        for (int i = 0; i <playerNum; i++){
            this.players.add(new Player("P"+(i+1)));
        }
        activePlayer = players.get(0);
    }

    /**
     * Makes a player pay rent to another player
     * @param owner the player that owns the property and is receiving the money
     * @param card the card on which the property is on
     */
    public void payRent(Player owner, Card card) {
        this.activePlayer.payRent(owner, card);
    }
    // ---------------------------------------------------------------------------------
    /**
     * enum that holds the different statuses for the game, certain player winning or undecided
     */
    public static enum Status {
        WINNER,
        UNDECIDED,
    }

    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        for (Card c :
                gameModel.getGameBoard().values()) {
            System.out.println(c.getCardType());
        }
    }

}
