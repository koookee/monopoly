/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class represents the model of our monoploy game
 *
 */

import java.awt.*;
import java.net.Inet4Address;
import java.util.*;

public class GameModel {
    private GameModel.Status status;
    private GameModel.Turn turn;
    private ArrayList<GameView> views;
    private Map<Integer, Card> gameBoard;
    private ArrayList<Player> players;
    private Player activePlayer;
    private Card currentCard;
    private int numTimesRolledDouble;




    /**
     * this is the default contructor
     * We set the amount of players to 4 as the default since we haven't added the ai yet
     */

    public GameModel() {
        this.status = GameModel.Status.UNDECIDED;
        this.turn = GameModel.Turn.P1_TURN;
        this.views = new ArrayList();
        this.gameBoard = new HashMap();
        this.players = new ArrayList<>();
        this.numTimesRolledDouble = 0;
        this.addPlayer("P1");
        this.addPlayer("P2");
        this.addPlayer("P3");
        this.addPlayer("P4");
        this.createGameBoard();
    }

    /**
     * This method adds a player to our list of players
     * We set the active player to the first player since they are Player 1
     * @param name is the String of the name the player wants
     */
    private void addPlayer(String name){
        players.add(new Player(name));
        if(players.size()==1){
            activePlayer = players.get(0);
        }
    }

    /**
     * This method creates the gameboard for the players
     */
    public void createGameBoard(){
        // As of right now "Go" does not exist
        String[] streetNames = {"Go","Sparks Street","Lebreton Flats","wellington Street","laurier Avenue",
                "waller Street","bronson Avenue","hurdman Road","Lett Street","lampman Crescent",
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
    private void changeTurn(){
        int index = players.indexOf(activePlayer);
        index++;
        index = index % 4;
        switch (turn) {
            case P1_TURN:
                turn = GameModel.Turn.P2_TURN;
                activePlayer = players.get(index);
                break;
            case P2_TURN:
                turn = GameModel.Turn.P3_TURN;
                activePlayer = players.get(index);
                break;
            case P3_TURN:
                turn = GameModel.Turn.P4_TURN;
                activePlayer = players.get(index);
                break;
            case P4_TURN:
                turn = GameModel.Turn.P1_TURN;
                activePlayer = players.get(index);
                break;
        }
    }

    /**
     * this method updates the status of the game
     * to show if a player is in or out
     */
    private void updateStatus(){

        int removePlayer = -1;

        for(Player x: players){
            if(x.getMoney()<=0){
                Game.printBankruptcy(x.getName());
                removePlayer = players.indexOf(x);
                x.setPlaying(false);
                for(Card c : x.getProperties()){
                    c.setOwned(false);
                }
            }
        }

        if(removePlayer != -1){
            players.remove(removePlayer);
        }


        if(players.size() == 1){
            switch(players.get(0).getName()){
                case "P1":
                    status = Status.P1_WINS;
                    break;

                case "P2":
                    status = Status.P2_WINS;
                    break;

                case "P3":
                    status = Status.P3_WINS;
                    break;

                case "P4":
                    status = Status.P4_WINS;
                    break;
            }
        }
    }

    /**
     * this method is called to play the game
     */
    public void play(){
        this.updateStatus();

        int dice1 = (int)(Math.random()*6+1);
        int dice2 = (int)(Math.random()*6+1);
        int roll = dice1 + dice2;


        activePlayer.setPosition((activePlayer.getPosition() + roll) % gameBoard.size());
        currentCard = gameBoard.get(activePlayer.getPosition());

        //If player X turn set there position to += the roll amount

        for (GameView view : views) {
            view.handleGameStatusUpdate(new GameEvent(this, status, currentCard,new int[] {dice1, dice2 }));
        }
        this.updateStatus();
        if(dice1 == dice2 && numTimesRolledDouble<=3){
            this.play();
            this.numTimesRolledDouble++;
        }else{
            this.changeTurn();
            this.numTimesRolledDouble = 0;
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
        return activePlayer;
    }


    /**
     * Getter for the current card that the player is on.
     * @return Returns a Card that is the current card that the player is on.
     */
    public Card getCurrentCard() {
        return currentCard;
    }




    /**
     * enum that holds the different statuses for the game, certain player winning or undecided
     */
    public static enum Status {
        P1_WINS,
        P2_WINS,
        P3_WINS,
        P4_WINS,
        UNDECIDED;
    }

    /**
     * enum that holds the different turns of players in monopoly.
     */
    public static enum Turn{
        P1_TURN,
        P2_TURN,
        P3_TURN,
        P4_TURN;
    }

}
