/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class handles different events that can be created in the Game.
 *
 */
import java.util.EventObject;

public class GameEvent extends EventObject {
    private GameModel.Status status;
    private Card card;
    private int[] roll;

    /**
     * The GameEvent constructor
     * @param gameModel the game model of type GameModel
     * @param status the status of type enum
     * @param card the card of type Card for the property
     * @param roll the roll of type int for the roll number
     */
    public GameEvent(GameModel gameModel, GameModel.Status status, Card card, int[] roll) {
        super(gameModel);

        this.status = status;

        this.card = card;
        this.roll = roll;
    }

    /**
     * Gets the roll number
     * @return the int roll
     */
    public int[] getRoll() {
        return roll;
    }

    /**
     * A getter for the status
     * @return the Enum status
     */
    public GameModel.Status getStatus() {
        return status;
    }

    /**
     * A getter for Card
     * @return the card of type Card
     */
    public Card getCard() {
        return card;
    }


    @Override
    public Object getSource() {
        return super.getSource();
    }

    public GameModel getModel(){
        return (GameModel) getSource();
    }
}
