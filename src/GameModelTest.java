/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class is to test the game model class.
 */

import junit.framework.TestCase;

public class GameModelTest extends TestCase {
    GameModel gm = new GameModel();
    protected void setUp(){
        gm.addPlayers(3, 1);

    }

    /**
     * testAddPlayer tests to see when a player is added.
     */
    public void testAddPlayer(){
        int total = 0;
        for(Player i : gm.getPlayers()){
            total ++;
        }
        assertEquals(4, total);
    }

    /**

     * testActivePlayer tests to see if ActivePlayer is working correctly.
     */
    public void testActivePlayer(){
        assertEquals(gm.getActivePlayer(), gm.getPlayers().get(0));
    }

    /**
     * testPayRent tests that when a player pays another.
     */
    public void testPayRent(){
        gm.setCurrentCard(1);
        gm.getCurrentCard().setOwned(gm.getPlayers().get(1));
        gm.payRent(gm.getPlayers().get(1), gm.getCurrentCard());

        assertEquals(1494, gm.getActivePlayer().getMoney());
    }

    /**
     * testCreateGameBoard tests to see if a game board is created when called to.
     */
    public void testCreateGameBoard(){
        //gm.setGameBoard();
        assertNotNull(gm.getGameBoard());
    }

    /**
     * testChangeTurn tests to see if when called that the turn changes to the next player.
     */
    public void testChangeTurn(){
        gm.changeTurn();
        assertEquals(1,gm.getCurrTurn());
    }

    /**
     * testBuyProperty tests to see when a property is bought that the player gains the card and
     * their money is subtracted.
     */
    public void testBuyProperty(){
        gm.setCurrentCard(1);
        gm.buyProperty();
        assertEquals(1, gm.getActivePlayer().getProperties().size());
        assertEquals(1440, gm.getActivePlayer().getMoney());
    }

    /**
     * testUnDecided tests to see that the game's status is "UNDECIDED" when the game starts.
     */
    public void testUnDecided(){
        assertEquals("UNDECIDED", GameModel.Status.valueOf("UNDECIDED").toString());
    }

    public void testNextTurn(){
        gm.setCurrentCard(1);
        gm.buyProperty();
        assertEquals(gm.getActivePlayer().getProperties().get(0), gm.getCurrentCard());
    }

    public void testBuyHouse(){
        gm.setCurrentCard(2);
        gm.buyHouse(gm.getCurrentCard());

        assertEquals(gm.getCurrentCard().getHouses(), 1);
    }
}
