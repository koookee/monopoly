import java.awt.*;

public class Jail extends Card{
    /**
     * Empty constructor
     */
    public Jail(){
    }

    /**
     * the constructor for the card Class
     * @param name the name of that card
     * @param cost the cost of the card
     * @param position the position of the card
     * @param color the color of the card
     * @param cardType the card type of the card
     */
    public Jail(String name, int cost, int position, Color color, CardType cardType) {
        super(name, cost, position, color, cardType,0, 0);
    }

    /**
     * Puts the player in jail
     * @param activePlayer the Player to be sent to jail
     */
    public void putInJail(Player activePlayer){
        activePlayer.setPrevPosition(activePlayer.getPrevPosition());
        activePlayer.setPosition(8);
        activePlayer.setIsInJail(1);
    }
}
