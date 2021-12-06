/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class represents the players who are playing Monopoly.
 */


import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Player {
    private int prevPosition;
    private String name;
    private int money;
    private int position;
    private boolean playing;
    private ArrayList<Card> properties;
    private int numUtils;
    private int isInJail;
    private boolean exconvict;
    private boolean isBot;
    private int numTimeRolledDouble;
    private boolean isActivePlayer;
    private int[] rolls;
    private boolean hasRolled;
    private PlayerNumber playerNumber;

    public static enum PlayerNumber {
        player1,
        player2,
        player3,
        player4
    }

    public Player(){}

    /**
     * The constructor for the Player class
     * @param name the name of the player
     */
    public Player(String name, boolean isBot){
        this.name = name;
        this.isBot = isBot;
        this.money = 1500;
        this.position = 0;
        this.playing = true;
        properties = new ArrayList<>();
        this.prevPosition = 0;
        this.numUtils = 0;
        this.isInJail = 0;
        this.exconvict = false;
        this.numTimeRolledDouble =0;
        isActivePlayer = false;
        rolls = new int[2];
        hasRolled =false;
    }

    public PlayerNumber getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
    }
    public void setPlayerNumber(int playerNumber) {
        switch (playerNumber){
            case 1: this.playerNumber = PlayerNumber.player1; return;
            case 2: this.playerNumber = PlayerNumber.player2; return;
            case 3: this.playerNumber = PlayerNumber.player3; return;
            case 4: this.playerNumber = PlayerNumber.player4;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getRolls() {
        return rolls;
    }

    public boolean isHasRolled() {
        return hasRolled;
    }

    @Override
    public String toString() {
        return "Player{" +
                "prevPosition=" + prevPosition +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", position=" + position +
                ", playing=" + playing +
                ", properties=" + properties +
                ", numUtils=" + numUtils +
                ", isInJail=" + isInJail +
                ", exconvict=" + exconvict +
                ", isBot=" + isBot +
                ", numTimeRolledDouble=" + numTimeRolledDouble +
                ", isActivePlayer=" + isActivePlayer +
                ", rolls=" + Arrays.toString(rolls) +
                ", hasRolled=" + hasRolled +
                ", playerPosition=" + playerNumber +
                '}';
    }

    public void setHasRolled(boolean hasRolled) {
        this.hasRolled = hasRolled;
    }

    public void setRolls(int[] rolls) {
        this.rolls = rolls;
    }

    public boolean isActivePlayer() {
        return isActivePlayer;
    }

    public void setActivePlayer(boolean activePlayer) {
        isActivePlayer = activePlayer;
    }

    public void setProperties(ArrayList<Card> properties) {
        this.properties = properties;
    }

    public void setNumUtils(int numUtils) {
        this.numUtils = numUtils;
    }

    public boolean isExconvict() {
        return exconvict;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    public int getNumTimeRolledDouble() {
        return numTimeRolledDouble;
    }

    public void setNumTimeRolledDouble(int numTimeRolledDouble) {
        this.numTimeRolledDouble = numTimeRolledDouble;
    }

    public boolean getExconvict(){
        return exconvict;
    }
    public void setExconvict(boolean bool){
        exconvict = bool;
    }
    /**
     * This method is used for a player to pay another player's rent for their property
     * @param player Player they are paying
     * @param card Card that the player is paying rent for
     */
    public void payRent(Player player, Card card){
        this.money -= card.getRent();
        player.collectMoney(card);
    }

    /**
     * Method to collect rent from given card.
     * @param card Property that the player is collecting from.
     */
    public void collectMoney(Card card){
        this.money += card.getRent();
    }

    /**
     * Getter for the name of the player.
     * @return name of the player.
     */
    public String getName() {
        return name;
    }

    public boolean getIsBot(){
        return isBot;
    }

    /**
     * Getter for the current money of the player
     * @return Returns the players current amount of money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Getters for the properties that the player currently owns
     * @return Returns the ArrayList of properties.
     */
    public ArrayList<Card> getProperties() {
        return properties;
    }

    /**
     * Setter for the players current amount money.
     * @param paid Takes the new value of money you want to set it too.
     */
    public void setMoney(int paid){
        this.money = paid;
    }

    /**
     * Method for when the player buys a new property.
     * @param currentCard The card the player is buying.
     */
    public void buyCard(Card currentCard){
        this.money -= currentCard.getCost();
        this.properties.add(currentCard);
        if (currentCard.getCardType() == Card.CardType.ultility) this.numUtils++;
        currentCard.setOwned(this);
    }

    /**
     * Checks if the player is still playing
     * @return returns true if the player is still playing and false otherwise
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * CSetter for whether the player is playing or not
     * @param playing the playing Boolean that determines whether the player is playing or not
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * getter for the number of utilities
     * @return the int of the number of utilities
     */
    public int getNumUtils() {
        return numUtils;
    }

    /**
     * Gets the position of the player
     * @return the int of the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the player
     * @param position the int of the position
     */
    public void setPosition(int position) {

        this.position = position;
    }

    /**
     * Gets the previous position of the player
     * @return the int of the previous position
     */
    public int getPrevPosition() {
        return prevPosition;
    }

    /**
     * Sets the previous position of the player
     * @param position the int of the previous position
     */
    public void setPrevPosition(int position) {
        this.prevPosition = position;
    }

    /**
     * getter for if the player is in jail
     * @return the int of whether the player is in jail or not
     */
    public int getIsInJail(){
        return this.isInJail;
    }

    /**
     * setter for the jail time
     * @param jailTime the int time
     */
    public void setIsInJail(int jailTime){
        this.isInJail = jailTime;
    }

    public void goToJail(){
        this.setPosition(8);
        this.setIsInJail(1);
        this.setIsInJail(1);
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

    public void serializeToXML (String filename)
    {
        try{
            FileOutputStream fos = new FileOutputStream(filename);
            XMLEncoder encoder = new XMLEncoder(fos);

            encoder.writeObject(this);
            encoder.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Player deserializeFromXML(String filename) {
        Player playerToReturn = null;
        try{
            FileInputStream fis = new FileInputStream(filename);
            XMLDecoder decoder = new XMLDecoder(fis);
            Player decoded = (Player) decoder.readObject();
            decoder.close();
            fis.close();
            playerToReturn =  decoded;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerToReturn;
    }

    public static void main(String[] args) {
        Player player = new Player("Joe", false);
        //player.buyCard(new Card("card", 0, 2, Color.black));
        player.serializeToXML("player.xml");


        Player player1 = Player.deserializeFromXML("player.xml");
        System.out.println(player1);
    }
}
