import java.awt.*;

public class Railroad extends Card{


    public Railroad(String name, int cost, Color color, CardType cardType) {
        super(name, cost, color, cardType);
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
