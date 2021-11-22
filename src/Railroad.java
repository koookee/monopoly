import java.awt.*;

public class Railroad extends Card{

    /**
     * the constructor for the card Class
     * @param name the name of that card
     * @param cost the cost of the card
     * @param position the position of the card
     * @param color the color of the card
     * @param cardType the card type of the card
     */
    public Railroad(String name, int cost, int position, Color color, CardType cardType) {
        super(name, cost, position, color, cardType, 0, 0);
    }

    /**
     * returns the rent of the card
     * @return an int amount of the rent
     */
    @Override
    public int getRent() {
        int rent = 25;
        for (Card card : super.getOwner().getProperties()) {
            if (card instanceof Railroad){
                rent = rent*2;
            }
        }
        return rent/2;
    }
}
