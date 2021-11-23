
import junit.framework.TestCase;

import java.awt.*;

public class CardTest extends TestCase {
    Card card = new Card("test",100,5, new Color(0), Card.CardType.property,0,0);
    Player testPlayer = new Player("tester");

    /**
     * setUp for the test case.
     */
    protected void setUp(){
        card.setOwned(true);
    }

    /**
     * tests to see if a card is labeled as owned if owned
     */
    public void testIsOwned(){
        card.setOwned(true);
        assertEquals(true, card.isOwned());
    }

    /**
     * tests to see if functionality reads if it is the right card or not.
     */
    public void testFunctionality(){
        assertEquals(0, card.functionality(testPlayer));
    }



}
