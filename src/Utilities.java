import java.awt.*;

public class Utilities extends Card{
    public Utilities(){

    }
    /**
     * the constructor for the card Class
     * @param name the name of that card
     * @param cost the cost of the card
     * @param position the position of the card
     * @param color the color of the card
     * @param cardType the card type of the card
     */
    public Utilities(String name, int cost, int position, Color color, CardType cardType) {
        super(name, cost, position, color, cardType, 0, 0);
    }
    /**
     * returns the rent of the card
     * @return an int amount of the rent
     */
    @Override
    public int getRent() {
        if(this.getOwner() == null){
            return 0;
        }
        else if(this.getOwner().getNumUtils() == 1){
            return Math.abs((this.getOwner().getPosition() - this.getOwner().getPrevPosition()))*4;
        }else{
            return Math.abs(this.getOwner().getPosition() - this.getOwner().getPrevPosition())*10;
        }
    }
}
