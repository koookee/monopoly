import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private String name;
    private int money;
    private int position;


    public Player(String name){
        this.name = name;
        this.money = 1500;
        this.position = 0;
    }

    public void payRent(Player player, Card card){
        player.money -= card.getRent();
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

    public void setMoney(int paid){
        this.money = paid;
    }

    public void buyCard(Card currentCard){
        this.money -= currentCard.getCost();
        currentCard.setOwned(this);
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
