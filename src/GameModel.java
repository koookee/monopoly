import java.util.*;

/**
 * @author Andre, Jack, Cassidy, Hussain
 * This class represents the model of our monoploy game
 *
 */
public class GameModel {
    private GameModel.Status status;
    private GameModel.Turn turn;
    private ArrayList<GameView> views;
    private Map<Integer, Card> gameBoard;
    private ArrayList<Player> players;
    private Player activePlayer;
    private Card currentCard;



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
        String[] streetNames = {"Sparks Street","Lebreton Flats","wellington Street","laurier Avenue",
                "waller Street","bronson Avenue","hurdman Road","Lett Street","lampman Crescent",
                "macKay Street","slater Street","thompson Street","sweetLand Avenue","sloper Place",
                "perly Drive","morrison Street","keefer Street","mcLeod Street","parliament Hill",
                "rideau Canal", "street 21", "street 22"};
        int[] costs = {60,60,100,100,120,180,180,200,220,220,240,260,260,280,300,300,320,350,400,420,450,500};




        String[] colors = {"brown","brown","light blue","light blue","light blue","pink","pink","pink",
                "orange","orange","orange", "red","red","red","yellow","yellow", "yellow","green","green",
                "green","blue","blue"};

        for (int i = 0; i < streetNames.length; i++) {
            gameBoard.put(i,new Card(streetNames[i],costs[i],colors[i]));
        }
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
        //System.out.println(dice1 + " " + dice2);

        while(dice1 == dice2){
            dice1 =(int)(Math.random()*6+1);
            dice2 = (int)(Math.random()*6+1);
            //System.out.println(dice1 + "" + dice2);
            roll += dice1 + dice2;
        }

        // This breaks the program when roll is > 1
        /*
        if (activePlayer.getPosition() + roll > 21){
            activePlayer.setPosition((activePlayer.getPosition() + roll) - 21);
            currentCard = gameBoard.get(activePlayer.getPosition()-1);
        }
        else{
            activePlayer.setPosition(activePlayer.getPosition() + roll);
            currentCard = gameBoard.get(activePlayer.getPosition()-1);
        }
        */

        activePlayer.setPosition((activePlayer.getPosition() + roll) % gameBoard.size());
        currentCard = gameBoard.get(activePlayer.getPosition());

        //If player X turn set there position to += the roll amount

        for (GameView view : views) {
            view.handleGameStatusUpdate(new GameEvent(this, status, currentCard,roll));
        }

        this.changeTurn();
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

    public Card getCurrentCard() {
        return currentCard;
    }

    public static enum Status {
        P1_WINS,
        P2_WINS,
        P3_WINS,
        P4_WINS,
        UNDECIDED;
    }

    public static enum Turn{
        P1_TURN,
        P2_TURN,
        P3_TURN,
        P4_TURN;
    }
}
