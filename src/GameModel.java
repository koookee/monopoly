import java.util.ArrayList;

public class GameModel {
    private GameModel.Status status;
    private GameModel.Turn turn;
    private ArrayList<GameModelView> views;


    public GameModel(){
        this.status = GameModel.Status.UNDECIDED;
        this.turn = GameModel.Turn.P1_TURN;
        this.views = new ArrayList();



    }
    public void addGameModelView(GameModelView view){
        this.views.add(view);
    }

    public void removeGameModelView(GameModelView view){
        this.views.remove(view);
    }

    public GameModel.Status getStatus(){
        return this.status;
    }

    private void changeTurn(){
        switch (turn) {
            case P1_TURN:
                turn = GameModel.Turn.P2_TURN;
                break;
            case P2_TURN:
                turn = GameModel.Turn.P3_TURN;
                break;
            case P3_TURN:
                turn = GameModel.Turn.P4_TURN;
                break;
            case P4_TURN:
                turn = GameModel.Turn.P1_TURN;
                break;
        }
    }

    public void play(){

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
