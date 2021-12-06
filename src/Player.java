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

    public enum PlayerNumber {
        player1,
        player2,
        player3,
        player4
    }

    /**
     * Empty constructor for the player
     */
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

    /**
     * Gets the player number
     * @return
     */
    public PlayerNumber getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Sets the player number
     * @param playerNumber enum of the player to be set
     */
    public void setPlayerNumber(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * Sets the player number
     * @param playerNumber the int of the player number to be set
     */
    public void setPlayerNumber(int playerNumber) {
        switch (playerNumber){
            case 1: this.playerNumber = PlayerNumber.player1; return;
            case 2: this.playerNumber = PlayerNumber.player2; return;
            case 3: this.playerNumber = PlayerNumber.player3; return;
            case 4: this.playerNumber = PlayerNumber.player4;
        }
    }

    /**
     * Sets the name of the player
     * @param name the String of the player name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the int array of the dice rolls
     * @return the array of the dice rolls
     */
    public int[] getRolls() {
        return rolls;
    }

    /**
     * Checks whether the player has rolled or not
     * @return the boolean of whether they rolled or not. true: yes. false: no
     */
    public boolean isHasRolled() {
        return hasRolled;
    }

    /**
     * toString method for the player. Makes it readable
     * @return the String of the information
     */
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

    /**
     * Sets whether the player has rolled or not
     * @param hasRolled the boolean of whether they have or have not rolled. true: rolled. false: did not roll
     */
    public void setHasRolled(boolean hasRolled) {
        this.hasRolled = hasRolled;
    }

    /**
     * Sets the dice rolls that the player got
     * @param rolls the int array of the dice rolls
     */
    public void setRolls(int[] rolls) {
        this.rolls = rolls;
    }

    /**
     * Checks if the player is active or not
     * @return  the boolean of whether they are active or not. true: active. false: inactive
     */
    public boolean isActivePlayer() {
        return isActivePlayer;
    }

    /**
     * Sets the player activity status
     * @param activePlayer the Player of which the status is to be set
     */
    public void setActivePlayer(boolean activePlayer) {
        isActivePlayer = activePlayer;
    }

    /**
     * Checks if the player is a bot
     * @return the boolean of whether they are or not. true: bot. false: not a bot
     */
    public boolean isBot() {
        return isBot;
    }

    /**
     * Gets the number of times the player rolled a double
     * @return the int of the number of doubles rolled
     */
    public int getNumTimeRolledDouble() {
        return numTimeRolledDouble;
    }

    /**
     * sets the number of doubles rolled
     * @param numTimeRolledDouble the int of the number of doubles
     */
    public void setNumTimeRolledDouble(int numTimeRolledDouble) {
        this.numTimeRolledDouble = numTimeRolledDouble;
    }

    /**
     * getter for ex-convict
     * @return boolean of whether they are an ex-convict or not: true: they are. false: they are not
     */
    public boolean getExconvict(){
        return exconvict;
    }

    /**
     * setter for the ex-convict status
     * @param bool the boolean of whether they are an ex-convict or not
     */
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

    /**
     * getter for whether the player is a bot or not
     * @return the boolean of whether they are a bot. true: bot. false: not bot
     */
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

    /**
     * sends the player to jail
     */
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

    /**
     * serializes to XML
     * @param filename the String of the file name to serialize to
     */
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

    /**
     * deserialize from XML
     * @param filename the String of the file name to serialize from
     * @return the Player object
     */
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
}
