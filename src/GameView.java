/**
 * @author Andre, Jack, Cassidy, Hussein
 */

public interface GameView {
    void handleGameStatusUpdate(GameEvent e);

    void unownedProperty(GameEvent e);

    void ownedProperty(GameEvent e);

    void announcePlayerPass(GameEvent e);

    void announceBankruptcy(GameEvent e);

    void announceWinner(GameEvent e);

    void announceToJail(GameEvent e);

    void announceJailTime(GameEvent e);
}
