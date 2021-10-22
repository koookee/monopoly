public class Card {
    private String name;
    private int cost;
    private String color;
    private boolean isOwned;
    private Player owner;



    public Card(String name, int cost) {
        this.isOwned = false;
        this.name = name;
        this.cost = cost;


    }

    /**
     * @author Andre
     * @param name
     * @param cost
     */
    public Card(String name, int cost,String color) {
        this(name,cost);
        this.color = color;
    }

    public boolean isOwned() { return isOwned; }


    public void setOwned( Player owner) {
        isOwned = true;
        this.owner = owner;
    }

    public Player getOwner(){
        return owner;
    }

    /**
     * @author Andre
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @author Andre
     * @return
     */
    public int getCost() {
        return cost;
    }

    /**
     * @author Andre
     * @return
     */
    public int getRent() {
        return (int) (cost * 0.1) ;
    }
}
