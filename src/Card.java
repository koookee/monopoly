public class Card {
    private String name;
    private int cost;
    private String color;



    public Card(String name, int cost) {
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


    /**
     * @author Andre
     * @param player
     */
    public void collectRent(Player player){

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
