import java.util.ArrayList;

public class Player {
    private String name;
    private int money;
    private ArrayList<Card> properties;
    private int position;


    public Player(String name){
        this.name = name;
        this.money = 0;
        this.properties = new ArrayList<>();
        this.position = 0; 
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getProperties() {
        return properties;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int paid){
        this.money = paid;
    }

    public void pay(Player x){

    }



}
