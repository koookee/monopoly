import java.util.ArrayList;

public class Player {
    private String name;
    private int money;
    private int position;


    public Player(String name){
        this.name = name;
        this.money = 0;
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

    public void buyCard(){

    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
