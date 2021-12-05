
/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class is to test the game model class.
 */

        import junit.framework.TestCase;

        import java.awt.*;

public class PlayerTest extends TestCase {
    Player testPlayer = new Player("testPlayer", false);
    Player testPlayer2 = new Player("testPlayer2", false);
    Card testCard = new Card("testCard",100,13, new Color(000), Card.CardType.property, 0,0);

    public void testSetPlayerNumber(){
        int numb = 1;
        testPlayer.setPlayerNumber(numb);
        assertEquals(testPlayer.getPlayerNumber(), Player.PlayerNumber.player1);
    }

    public void testPayRent(){
        testPlayer.payRent(testPlayer2, testCard);
        assertEquals(testPlayer.getMoney(), 1490);
    }

    public void testCollectMoney(){
        testPlayer.collectMoney(testCard);
        assertEquals(testPlayer.getMoney(), 1510);
    }

    public void testBuyCard(){
        testPlayer.buyCard(testCard);
        assertEquals(testCard, testPlayer.getProperties().get(0));
    }

    public void testGoToJail(){
        testPlayer.goToJail();
        assertEquals(testPlayer.getPosition(), 8);
    }

}