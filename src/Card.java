import java.awt.*;

/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class represents the Cards/Properties in Monopoly
 *
 */
public class Card {
    private String name;
    private int cost;
    private Color color;
    private boolean isOwned;
    private Player owner;
    private CardType cardType;

    public static enum CardType {
        go,
        jail,
        property,
        railroad,
        ultility
    }

    /**
     * The constructor for the Card class
     * @param name the String for the name of the card
     * @param cost the int for the cost of the property
     */
    public Card(String name, int cost) {
        this.isOwned = false;
        this.name = name;
        this.cost = cost;


    }

    /**
     * the constructor for the card Class
     * @param name the name of that card
     * @param cost the cost of the card
     * @param color the color of the card
     */
    public Card(String name, int cost, Color color) {
        this(name,cost);
        this.color = color;
    }
    public Card(String name, int cost, Color color, CardType cardType) {
        this(name,cost,color);
        this.cardType = cardType;
    }

    /**
     * getter to see if the card is owned
     * @return returns a boolean true or false if the card is owned or not
     */
    public boolean isOwned() { return isOwned; }

    /**
     * sets if the property is owned or not
     * this method is mainly used to set a property to the not owned state
     * @param owned a boolean to set the owned status
     */
    public void setOwned(boolean owned){
        isOwned = owned;
        this.owner = null;
    }

    /**
     * This method give a player the ownership of a property
     * @param owner the Player that owns the card
     */
    public void setOwned(Player owner) {
        isOwned = true;
        this.owner = owner;
    }

    /**
     * getter for the owner of the card
     * @return Player owner
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * getter for the name of the card
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the cost of the card
     * @return int the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * returns the rent of the card
     * @return an int amount of the rent
     */
    public int getRent() {
        if (cardType == CardType.property) return (int) (cost * 0.1);
        else if (cardType == CardType.railroad){
            int rent = 25;
            if (this.getOwner() != null){
                for (Card card : this.getOwner().getProperties()) {
                    if (card.cardType == CardType.railroad){
                        rent = rent*2;
                    }
                }
                return rent/2;
            }
            else return rent;
        }
        else{
            if(this.getOwner() == null){
                return 0;
            }
            else if(this.getOwner().getNumUtils() == 1){
                return (this.getOwner().getPosition() - this.getOwner().getPrevPostion())*4;
            }else{
                return (this.getOwner().getPosition() - this.getOwner().getPrevPostion())*10;
            }

        }
    }

    public CardType getCardType() {
        return cardType;
    }

    /**
     * The getter for color of the card
     * @return the Color of the card tile
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Checks if the property is owned by a player
     * @param p the player on the card
     * @return the int of whether the card has an owner, no owner, or if the owner is p
     */
    public int functionality(Player p){
        if(getCardType().equals(CardType.jail)){
            System.out.println("Function 3");
            return 3;
        }
        else if (this.owner == null){
            System.out.println("unowned");
            return 0;
        }
        else{
            if (this.owner.getName().equals(p.getName())){
                return 1;
            }

            return 2;
        }
        // if owner
        // no owner
        // is owned
    }
}
