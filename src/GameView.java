public interface GameView {
    void handleGameStatusUpdate(GameEvent e);

    void unownedProperty(GameEvent gameEvent);

    void ownedProperty(GameEvent gameEvent);

    void announcePlayerPass(GameEvent gameEvent);

    void announceBankruptcy(GameEvent gameEvent);

    void announceWinner(GameEvent gameEvent);
}
