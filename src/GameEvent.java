/**
 * @author Andre
 */
import java.util.EventObject;

public class GameEvent extends EventObject {



    private GameModel.Status status;

    private Card card;
    private int roll;

    public GameEvent(GameModel gameModel, GameModel.Status status, Card card, int roll) {
        super(gameModel);

        this.status = status;

        this.card = card;
        this.roll = roll;
    }

    public int getRoll() {
        return roll;
    }

    public GameModel.Status getStatus() {
        return status;
    }


    public Card getCard() {
        return card;
    }
}
