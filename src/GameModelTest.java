/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class is to test the game model class.
 */

import junit.framework.TestCase;

public class GameModelTest extends TestCase {
    GameModel gm = new GameModel();

    protected void setUp(){
        gm.addPlayers(4);

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
        assertEquals("P1", gm.getPlayers().get(0).getName());
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
        gm.createGameBoard();
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

    /**
     * testPlayChoice1 tests to see that when play(1) is called that the dice rolls a value.
     */
    public void testPlayChoice1(){
        gm.play(1);
        assert(gm.roll != 0);
    }

    /**
     * testPlayChoice5 tests to see that when play(5) is called that the ActivePlayer buys a property.
     */
    public void testPlayChoice5(){
        gm.setCurrentCard(1);
        gm.play(5);
        assertEquals(1, gm.getActivePlayer().getProperties().size());
    }


}
