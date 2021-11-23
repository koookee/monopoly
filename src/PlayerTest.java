import junit.framework.TestCase;

import java.awt.*;

public class PlayerTest extends TestCase{
    Player test = new Player("testPlayer");
    Card card = new Card("test",100,10, new Color(0), Card.CardType.property,0,0);


    /**
     * test to see if the player gains money buy paying themselves for a created card above.
     */
    public void testPayRent(){
        test.payRent(test,card);
        assertEquals(1500, test.getMoney());
    }

    /**
     * tests collectMoney to see if the player gains money when another player lands on their property.
     */
    public void testCollectMoney(){
        test.collectMoney(card);
        assertEquals(1510, test.getMoney());
    }

    /**
     * tests to see if when the player buys a card they actually gain the card in their property list.
     */
    public void testBuyCard(){
        test.buyCard(card);
        assertEquals(1, test.getProperties().size());
    }

}
