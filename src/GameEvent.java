/**
 * @author Andre 
 */
import java.util.EventObject;

public class GameEvent extends EventObject {


    private GameModel.Turn turn;
    private GameModel.Status status;

    public GameEvent(GameModel gameModel, GameModel.Turn turn, GameModel.Status status ) {
        super(gameModel);
        this.turn = turn;
        this.status = status;
    }

    public GameModel.Turn getTurn() {
        return turn;
    }

    public GameModel.Status getStatus() {
        return status;
    }
}
