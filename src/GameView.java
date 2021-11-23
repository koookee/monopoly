/**
 * @author Andre, Jack, Cassidy, Hussein
 */

public interface GameView {
    void handleGameStatusUpdate(GameEvent e);

    void unownedProperty(GameEvent e);

    void ownedProperty(GameEvent e);

    void askToBuyHouse(GameEvent e);

    void announcePlayerPass(GameEvent e);

    void announceBankruptcy(GameEvent e);

    void announceWinner(GameEvent e);

    void announceToJail(GameEvent e);

    void announceJailTime(GameEvent e);

    void askToBuyHotel(GameEvent gameEvent);

    void handleBot(GameEvent e);

    void announceBoughtBotProperty(GameEvent gameEvent);

    void announcePaidBotRent(GameEvent gameEvent);
}
