
import java.util.*;
public class GameModel {
    private GameModel.Status status;
    private GameModel.Turn turn;
    private ArrayList<GameView> views;
    private Map<Integer, Card> gameBoard;
    private ArrayList<Player> players;
    private Player activePlayer;
    private Card currentCard;


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
        createGameBoard();
    }

    private void addPlayer(String name){
        players.add(new Player(name));
        if(players.size()==1){
            activePlayer = players.get(0);
        }
    }

    public void createGameBoard(){

        String[] streetNames = {"Sparks Street","Lebreton Flats","wellington Street","lauier Avenue","waller Street","bronson Avenue","hurdman Road",
                "Lett Street","lampman Crescent","macKay Street","slater Street","thompson Street","sweetLand Avenue","sloper Place","perly Drive",
                "morrison Street","keefer Street","mcLeod Street","parliament Hill","rideau Canal"};
        int[] costs = {60,60,100,100,120,180,180,200,220,220,240,260,260,280,300,300,320,350,400};


        String[] colors = {"Brown","Brown","light blue","light blue","light blue","pink","pink","pink","orange","orange","orange","yellow","yellow",
                "yellow","green","green","green","blue","blue"};

        for (int i = 0; i < streetNames.length-1; i++) {
            gameBoard.put(i,new Card(streetNames[i],costs[i],colors[i]));
        }

    }


    public void addGameModelView(GameView view){

        this.views.add(view);
    }

    public void removeGameModelView(GameView view){
        this.views.remove(view);
    }

    public GameModel.Status getStatus(){
        return this.status;
    }

    private void changeTurn(){
        switch (turn) {
            case P1_TURN:
                turn = GameModel.Turn.P2_TURN;
                activePlayer = players.get(1);
                break;
            case P2_TURN:
                turn = GameModel.Turn.P3_TURN;
                activePlayer = players.get(2);
                break;
            case P3_TURN:
                turn = GameModel.Turn.P4_TURN;
                activePlayer = players.get(3);
                break;
            case P4_TURN:
                turn = GameModel.Turn.P1_TURN;
                activePlayer = players.get(0);
                break;
        }
    }
    private void updateStatus(){
        return; //TODO: we need to figure out how to do the win checking
    }

    /*
    WE GOTTA IMPLEMENT THIS SHIT MY LORDY
    */

    /**
     * Author: Andre and Hussein
     */
    public void play(){
        int roll = (int)(Math.random()*6+1);

        activePlayer.setPosition((activePlayer.getPosition() + roll) % 22);
        currentCard = gameBoard.get(activePlayer.getPosition());
        //If player X turn set there position to += the roll amount

        for (GameView view : views) {
            view.handleGameStatusUpdate(new GameEvent(this, status, currentCard,roll));
        }

        this.changeTurn();
    }

    public void buyProperty(){
        this.activePlayer.buyCard(currentCard);
    }
    public Player getActivePlayer() {
        return activePlayer;
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
