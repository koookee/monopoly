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
     * this is the default constructor
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
        String[] streetNames = {"Go","Sparks Street","Lebreton Flats","wellington Street","laurier Avenue",
                "waller Street","bronson Avenue","Hurdman Road","Lett Street","lampman Crescent",
                "macKay Street","slater Street","thompson Street","sweetLand Avenue","sloper Place",
                "perly Drive","morrison Street","keefer Street","mcLeod Street","parliament Hill",
                "rideau Canal", "street 21", "street 22"};
        int[] costs = {0,60,60,100,100,120,180,180,200,220,220,240,260,260,280,300,300,320,350,400,420,450,500};

        Color[] colors = {Color.white,new Color(150, 75, 0),new Color(150, 75, 0), Color.CYAN,Color.CYAN,Color.CYAN,Color.pink,Color.pink,Color.pink,
                Color.orange,Color.orange,Color.orange, Color.red, Color.red, Color.red,Color.yellow,Color.yellow, Color.yellow,Color.green,Color.green,
                Color.green,new Color(0,135,225),new Color(0,135,225)};

        for (int i = 0; i < streetNames.length; i++) {
            gameBoard.put(i,new Card(streetNames[i],costs[i],colors[i]));
        }
    }

    public Map<Integer,Card> getGameBoard(){
        return this.gameBoard;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * This method adds a view to the model
     * @param view a view that the model will notify
     */
    public void addGameModelView(GameView view){
        this.views.add(view);
    }

    /**
     * this method removes a view from the model
     * @param view a view that will be removed
     */
    public void removeGameModelView(GameView view){
        this.views.remove(view);
    }

    /**
     * A getter for the status
     * @return the Enum status
     */
    public GameModel.Status getStatus(){
        return this.status;
    }

    /**
     * this method changes the players turn
     */
    public void changeTurn(){
        currTurn++;
        if (currTurn == players.size()) currTurn = 0;
        activePlayer = players.get(currTurn);
    }

    /**
     * this method updates the status of the game
     * to show if a player is in or out
     */
    private int updateStatus(){

        int removePlayer = -1;

        for(Player x: players){
            if(x.getMoney()<=0){
                removePlayer = players.indexOf(x);
                x.setPlaying(false);
                for(Card c : x.getProperties()){
                    c.setOwned(false);
                }
            }
        }

        if(removePlayer != -1){
            players.remove(removePlayer);
            return 1;
        }

        if(players.size() == 1){
            status = Status.WINNER;
            return 2;
        }
        return 0;
    }

    /**
     * this method is called to play the game
     */
    public void play(int choice){
        if (choice == 1 && hasNotRolled && status.name().equals("UNDECIDED")){
            dice1 = (int)(Math.random()*6+1);
            dice2 = (int)(Math.random()*6+1);
            roll = dice1 + dice2;

            activePlayer.setPrevPosition(activePlayer.getPosition());
            activePlayer.setPosition((activePlayer.getPosition() + roll) % gameBoard.size());
            currentCard = gameBoard.get(activePlayer.getPosition());
            //If player X turn set there position to += the roll amount

            play(4);

            int gameState = this.updateStatus();
            for(GameView view : views){
                if(gameState == 1){
                    view.announceBankruptcy(new GameEvent(this, status, currentCard, new int[] {dice1, dice2}));
                }
                else if(gameState == 2){
                    view.announceWinner(new GameEvent(this, status,currentCard, new int[] {dice1, dice2}));
                }
            }
            if (dice1 == dice2 && numTimesRolledDouble <= 3) {
                this.numTimesRolledDouble++;
                hasNotRolled = true;
            }
            else {
                hasNotRolled = false;
            }
        }
        else if (choice == 2 && status.name().equals("UNDECIDED")) { // Ask if they're sure they want to pass
            for (GameView view : views) {
                view.announcePlayerPass(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
            }
        }
        else if (choice == 3 && status.name().equals("UNDECIDED")){ // Player confirms they want to pass
            this.updateStatus();
            this.changeTurn();
            numTimesRolledDouble = 0;
            hasNotRolled = true;
            for (GameView view : views) {
                view.handleGameStatusUpdate(new GameEvent(this, status, currentCard, new int[]{0, 0})); // Player didn't roll yet
            }
        }
        else if(choice == 4 && status.name().equals("UNDECIDED")) { // Buying property
            int result = currentCard.functionality(activePlayer);
            for (GameView view : views) {
                view.handleGameStatusUpdate(new GameEvent(this, status, currentCard,new int[] {dice1, dice2 }));
                if (result == 0) {
                    view.unownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                } else if (result == 2) {
                    view.ownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                }


            }
        }
        else if (choice == 5 && status.name().equals("UNDECIDED")){ // Confirms buying
            int result = currentCard.functionality(activePlayer);
            if (result == 0) buyProperty();
        }
    }

    /**
     * this method is used to buy a property for a player
     */
    public void buyProperty(){
        this.activePlayer.buyCard(currentCard);
    }

    /**
     * getter for the activePlayer
     * @return Returns a Player that is the current player
     */
    public Player getActivePlayer() {
        return players.get(currTurn);
    }


    /**
     * Getter for the current card that the player is on.
     * @return Returns a Card that is the current card that the player is on.
     */
    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(int x ){
        currentCard = getGameBoard().get(x);
    }

    public int getCurrTurn(){
        return currTurn;
    }

    public void addPlayers(int playerNum) {
        if(playerNum<2 || playerNum>4){
            return;
        }
        for (int i = 0; i <playerNum; i++){
            this.players.add(new Player("P"+(i+1)));
        }
        activePlayer = players.get(0);
    }

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

}
