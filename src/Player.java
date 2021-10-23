import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private String name;
    private int money;
    private int position;
    private boolean playing;
    private ArrayList<Card> properties;


    public Player(String name){
        this.name = name;
        this.money = 1500;
        this.position = 0;
        this.playing = true;
        properties = new ArrayList<>();
    }

    public void payRent(Player player, Card card){
        this.money -= card.getRent();
        player.collectMoney(card);
    }
    public void collectMoney(Card card){
        this.money += card.getRent();
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<Card> getProperties() {
        return properties;
    }

    public void setMoney(int paid){
        this.money = paid;
    }

    public void buyCard(Card currentCard){
        this.money -= currentCard.getCost();
        this.properties.add(currentCard);
        currentCard.setOwned(this);
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return position == player.position && name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position);
    }
}
