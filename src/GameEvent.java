/**
 * @author Andre
 */
import java.util.EventObject;

public class GameEvent extends EventObject {



    private GameModel.Status status;
    private Player activePlayer;
    private Card card;

    public GameEvent(GameModel gameModel, GameModel.Status status, Player activePlayer, Card card) {
        super(gameModel);

        this.status = status;
        this.activePlayer = activePlayer;
        this.card = card;
    }



    public GameModel.Status getStatus() {
        return status;
    }

    public Player getActivePlayer() {return activePlayer;}
}
