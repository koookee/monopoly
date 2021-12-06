import java.util.ArrayList;

/**
 * @author Andre, Jack, Cassidy, Hussein
 */

public interface GameView {
    void handleGameStatusUpdate(GameEvent e);

    void unownedProperty(GameEvent e);

    void ownedProperty(GameEvent e);

    void announceBankruptcy(GameEvent e);

    void announceWinner(GameEvent e);

    void announceToJail(GameEvent e);

    void announceJailTime(GameEvent e);

    void payJailFee(GameEvent e);

    void updateFromImport(Player p, int[] roll );

    void announceBoughtBotProperty(GameEvent gameEvent);

    void announcePaidBotRent(GameEvent gameEvent);

    void enableBuyButton();

    void disableBuyButton();

    void setRollEnable(boolean b);

    void displayBuyArrOptions(ArrayList<Card> buyArrOptions);

    void setImportButtonEnable(boolean b);

    void removePlayerIcon(Player p);

    void closeWindow();

    void closeBuyWindow();

    void announcePassGo(GameEvent gameEvent);
}
