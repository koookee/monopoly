import java.awt.*;

public class Railroad extends Card{


    public Railroad(String name, int cost, int position, Color color, CardType cardType) {
        super(name, cost, position, color, cardType, 0, 0);
    }

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
