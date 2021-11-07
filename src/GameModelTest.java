
/*
import junit.framework.TestCase;

import java.util.Map;

public class GameModelTest extends TestCase {
    GameModel gm = new GameModel();

    protected void setUp(){
    }

    protected void tearDown(){

    }

    public void testActivePlayer(){
        assertEquals("P1", gm.getPlayers().get(0).getName());
    }

    public void testAddPlayer(){
        int numbPlayers = 2;
        gm.addPlayers(numbPlayers);
        int total = 0;
        for(String i : gm.getPlayers()){
            total ++;
        }
        assertEquals(numbPlayers, total);


    }
    public void testPayRent(){
        gm.addPlayer(2)
        gm.setCurrentCard(1);
        gm.payRent(gm.getActivePlayer(), gm.getCurrentCard);
        gm.getActivePlayer()


    }



    public void testCreateGameBoard(){
        gm.createGameBoard();
        assertNotNull(gm.getGameBoard());
    }

    public void testUpdateStatus(){
        gm.addPlayer(2);
        gm.getPlayers().get(0).setMoney(0);
        gm.updateStatus();
        assertEquals(4,gm.getPlayers().size());

    }

    public void testChangeTurn(){
        gm.changeTurn();
        assertEquals(currTurn,1);
    }

    public void testBuyProperty(){
        gm.setCurrentCard(1);
        gm.buyProperty();
        assertEquals(1, gm.getActivePlayer().getProperties().size());
    }

    public void testUnDecided(){
        assertEquals("UNDECIDED", GameModel.Status.valueOf("UNDECIDED").toString());
    }

    public void testPlayerWin(){
        for(int i = 1; i< gm.getPlayers.size() ; i++){
            gm.getPlayers().get(i).setMoney(0);
        }
        assertEquals("WINNER", gm.getStatus().toString());
    }
}
*/
