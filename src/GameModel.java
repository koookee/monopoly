/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class represents the model of our monoploy game
 *
 */

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLOutput;
import java.util.*;

public class GameModel {
    private GameModel.Status status;
    private ArrayList<GameView> views;
    private Map<Integer, Card> gameBoard;
    private ArrayList<Player> players;
    private int currTurn;
    private Player activePlayer;
    private Card currentCard;
    private int numTimesRolledDouble;
    private boolean hasNotRolled;
    private ArrayList<Card> buyArrOptions;
    private int numOfHouses;
    private int numOfHotels;
    int dice1;
    int dice2;
    int roll;

    /**
     * The constructor for the GameModel class
     * We set the amount of players to 4 as the default since we haven't added the ai yet
     */

    public GameModel() {
        this.currTurn = 0;
        this.status = GameModel.Status.UNDECIDED;
        this.views = new ArrayList();
        this.gameBoard = new HashMap();
        this.buyArrOptions = new ArrayList<>();
        this.players = new ArrayList<>();
        this.numTimesRolledDouble = 0;
        this.hasNotRolled = true;
        this.numOfHouses = 32;
        this.numOfHotels = 12;
        this.createGameBoard();
    }

    /**
     * This method adds a player to our list of players
     * We set the active player to the first player since they are Player 1
     * @param name is the String of the name the player wants
     */
    private void addPlayer(String name){
        players.add(new Player(name, false));
        players.add(new AutoPlayer(name, true));
        if(players.size()==1) activePlayer = players.get(0);
        activePlayer.setActivePlayer(true);
    }

    /**
     * This method creates the gameboard for the players
     */
    public void createGameBoard(){
        int position = 0;
        String[] streetNames = {"Go","Sparks Street","Lebreton Flats","wellington Street","laurier Avenue","Bytown&Prescott Railway",
                "waller Street","bronson Avenue","Jail","Hurdman Road","Canadian Pacific Railway","Lett Street","lampman Crescent",
                "macKay Street","slater Street","Canadian National Railway","thompson Street","sweetLand Avenue","sloper Place",
                "Water Works", "New York Central Railway","perly Drive","morrison Street","Go to Jail","keefer Street","mcLeod Street","parliament Hill",
                "Electric Company","rideau Canal", "street 21", "street 22"};
        int[] costs = {0,60,60,100,100,200,120,180,0,180,200,200,220,220,240,200,260,260,280,100,200,300,300,0,320,350,400,200,420,450,500};

        int[] houseCosts = {0,50,50,50,50,50,50,50,0,100,100,100,100,100,100,150,150,150,150,150,150,150,150,0,200,200,200,200,200,200,200};

        int[] hotelCosts = {0,50,50,50,50,50,50,50,0,100,100,100,100,100,100,150,150,150,150,150,150,150,150,0,200,200,200,200,200,200,200};

        Color[] colors = {Color.white,new Color(150, 75, 0),new Color(150, 75, 0), Color.CYAN,Color.CYAN,Color.black,Color.CYAN,Color.pink,Color.blue,Color.pink,Color.black,
                Color.pink,Color.orange,Color.orange,Color.orange, Color.black, Color.red,Color.red, Color.red,Color.white,Color.black, Color.yellow,Color.yellow,Color.blue,Color.yellow,Color.green,
                Color.green,Color.white, Color.green,new Color(0,135,225),new Color(0,135,225)};

        for (int i = 0; i < streetNames.length; i++) {
            if (i%5==0 && i<21 && i>1){
                gameBoard.put(i,new Railroad(streetNames[i],costs[i],position, colors[i], Card.CardType.railroad));
            }else if(i== 19 || i == 27) {
                gameBoard.put(i, new Utilities(streetNames[i], costs[i],position, colors[i], Card.CardType.ultility));
            }else if(i == 23){
                gameBoard.put(i, new Jail(streetNames[i], costs[i],position, colors[i], Card.CardType.jail));
            }
            else{
                gameBoard.put(i,new Card(streetNames[i],costs[i],position, colors[i], Card.CardType.property, houseCosts[i], hotelCosts[i]));
            }
            position++;
        }
    }

    /**
     * The getter for the game board
     * @return the Map<Integer, Card> of the game board
     */
    public Map<Integer,Card> getGameBoard(){
        return this.gameBoard;
    }

    /**
     * This method adds a view to the model
     * @param view a view that the model will notify
     */
    public void addGameModelView(GameView view){
        this.views.add(view);
    }

    /**
     * this method changes the players turn
     */
    public void changeTurn(){
        currTurn = (currTurn + 1) % players.size();
        while (!players.get(currTurn).isPlaying()) currTurn = (currTurn + 1) % players.size(); // Skips the players that are bankrupt
        activePlayer.setActivePlayer(false);
        activePlayer = players.get(currTurn);
        activePlayer.setActivePlayer(true);
        activePlayer.setExconvict(false);
        if(activePlayer instanceof AutoPlayer) {
            botPlay();

        }else if (activePlayer.getIsInJail() != 0 && activePlayer.getIsInJail() < 3 && activePlayer.getMoney() >= 50) {
            for (GameView view : views) {
                view.payJailFee(new GameEvent(this, status, currentCard, new int[]{0, 0}));
            }
            updateStatus();

        }
        if(activePlayer.getIsInJail() == 3 ){
            announceJailTime();
            activePlayer.setIsInJail(0);
            activePlayer.setMoney(activePlayer.getMoney() - 50);
            updateStatus();
        }

    }

    /**
     * Getter for the current turn
     * @return the current turn
     */
    public int getCurrTurn(){
        return currTurn;
    }

    /**
     * This method updates the status of the game
     * to show if a player is in or out. It also
     * sets the game state to Winning if there's 1
     * player left
     * @return the int of whether a player went bankrupt or not
     */
    private int updateStatus(){

        int removePlayer = -1;
        int playerLost = 0;
        int numToReturn = 0;


        for(Player x: players){
            if(x.getMoney()<=0 && x.isPlaying()){
                //removePlayer = players.indexOf(x);
                x.setPlaying(false);
                playerLost = 1;
                for(Card c : x.getProperties()){
                    c.setOwned(false);
                }
                for (GameView v :
                        views) {
                    v.announceBankruptcy(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                    v.removePlayerIcon(x);
                }



            }
        }

        int currentPlayers = 0;

        for (Player p : players) {
            if(p.isPlaying()) currentPlayers++;

        }

        if(playerLost == 1) {
            numToReturn = 1;
            //players.remove(removePlayer);
        }
        if(currentPlayers == 1){
            status = Status.WINNER;
            for (GameView v :
                    views) {
                v.announceWinner(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                v.closeWindow();
            }
        }

        return numToReturn;
    }

    private void updateViews(int numDice1, int numDice2){
        for (GameView view : views) {
            view.handleGameStatusUpdate(new GameEvent(this, status, currentCard, new int[]{numDice1, numDice2}));
        }
    }
    public void enableBuyButton(){
        for(GameView view : views){
            view.enableBuyButton(getActivePlayer());
        }
    }
    public void disableBuyButton(){
        for(GameView view : views){
            view.disableBuyButton();
        }
    }
    public void setEnableRoll(boolean b){
        for(GameView view : views){
            view.setRollEnable(b);
        }
    }

    private void checkJailRoll(){
        if (activePlayer.getIsBot() && (activePlayer.getIsInJail() != 0 && activePlayer.getIsInJail() < 3)){
            activePlayer.setIsInJail(0);
            activePlayer.setMoney(activePlayer.getMoney() - 50);
            updateStatus();
        }
        if(activePlayer.getIsInJail() != 0 && activePlayer.getIsInJail() < 3 && dice1!=dice2){
            currentCard = gameBoard.get(8);
            setEnableRoll(false);
            announceJailTime();
            int current = this.activePlayer.getIsInJail();
            current += 1;
            this.activePlayer.setIsInJail(current);
            System.out.println(activePlayer.getIsInJail());
        }
        else{
            activePlayer.setPrevPosition(activePlayer.getPosition());
            activePlayer.setPosition((activePlayer.getPosition() + roll) % gameBoard.size());
            currentCard = gameBoard.get(activePlayer.getPosition());
        }

        if (dice1 == dice2){
            if(activePlayer.getIsInJail() != 0 && activePlayer.getIsInJail() < 3){
                announceJailTime();
                setEnableRoll(false);
            }
            else {
                setEnableRoll(true);
                if (activePlayer.getNumTimeRolledDouble() == 3){
                    announceJail();
                    setCurrentCard(21);
                    activePlayer.goToJail();
                    activePlayer.setNumTimeRolledDouble(-1);
                    setEnableRoll(false);
                }
                activePlayer.setNumTimeRolledDouble(activePlayer.getNumTimeRolledDouble() + 1);
            }
        }
        else{
            setEnableRoll(false);
            activePlayer.setNumTimeRolledDouble(0);
        }
    }

    public void roll(){
        rollDice();
        System.out.println(activePlayer.getPrevPosition() + " "+ activePlayer.getPosition());
        checkJailRoll();


        if(currentCard instanceof Jail){
            ((Jail) currentCard).putInJail(activePlayer);
            if (dice1 == dice2) setEnableRoll(false);
            announceJail();
        }
        else if (currentCard.isOwned() && currentCard.getOwner()!= activePlayer) {
            if (!activePlayer.isBot()) {
                for (GameView v :
                        views) {
                    v.ownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                }
            }
            payRent(currentCard.getOwner(),currentCard);
            disableBuyButton();
            updateStatus();
        }
        else if (currentCard.getCost()==0 || currentCard.getCost() > activePlayer.getMoney()){
            disableBuyButton();
        }
        else enableBuyButton();

        if(activePlayer.getIsInJail() > 1){
            dice1 = 0;
            dice2 = 0;
        }

        updateViews(dice1,dice2);

    }

    public void buy(){
        //if (activePlayer.getMoney() > currentCard.getCost()) {
        //    activePlayer.buyCard(currentCard);
        //    views.get(0).unownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
        //    disableBuyButton();
        //}
        clearBuyArrOptions();
        addCardsToBuyArr();
        for(GameView view : views) view.displayBuyArrOptions(buyArrOptions);
    }

    public void confirmPurchase(String cardName){
        Card tile = getCard(cardName);
        System.out.println(tile.getName());
        if (tile.isOwned() == false) buyProperty(); // No owner so purchase property
        else if (tile.getHouses() == 4) buyHotel(tile); // Player has enough to buy a hotel
        else if (tile.getHouses() < 4) buyHouse(tile); // Not enough to buy hotel but owns property
        disableBuyButton();
    }

    /**
     * Clears the buyArrOptions ArrayList
     */
    private void clearBuyArrOptions(){
        while (buyArrOptions.size() != 0) buyArrOptions.remove(0);
    }

    /**
     * Adds all tiles (cards) on the board to the buyArrOptions ArrayList assuming they can be added
     */
    private void addCardsToBuyArr(){
        if (checkIfCanBuyProperty()) buyArrOptions.add(currentCard);
        for (Integer key: gameBoard.keySet()){
            if (checkIfCanBuyHouse(gameBoard.get(key)) || checkIfCanBuyHotel(gameBoard.get(key))){
                buyArrOptions.add(gameBoard.get(key));
            }
        }
    }

    public void nextTurn(){
        this.updateStatus();
        this.changeTurn();
        activePlayer.setNumTimeRolledDouble(0);
        setEnableRoll(true);
        disableBuyButton();
        updateViews(0,0);


    }
    public void announceJailTime(){
        for(GameView view : views){
            view.announceJailTime(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
        }
    }

    public void announceJail(){
        for(GameView view : views){
            view.announceToJail(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
        }
    }





    private void botLandOnProperty() {


        if(activePlayer.getMoney() > currentCard.getCost() && currentCard.getCost() != 0){
            activePlayer.buyCard(currentCard);
            for(GameView view: views){
                view.announceBoughtBotProperty(new GameEvent(this, status, currentCard, new int[]{dice1,dice2}));
            }
        }
    }

    public void botPlay() {
        roll();
        botLandOnProperty();
        if (dice1 == dice2){
            botPlay();
        }
        else nextTurn();
    }

    private void rollDice() {
        dice1 = (int) (Math.random() * 6 + 1);
        dice2 = (int) (Math.random() * 6 + 1);
        roll = dice1 + dice2;



//         For debugging purposes (can make players move to specific tiles)
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter roll 1");
//        int num = scanner.nextInt();
//        dice1 = num;
//        System.out.println("Enter roll 2");
//        num = scanner.nextInt();
//        dice2 = num;
//        roll = dice1 + dice2;
        activePlayer.setRolls(new int[] {dice1, dice2});
    }
    /**
     * this method is used to check if the player can buy the property
     * @return the boolean of whether the player can buy the property or not
     */
    private boolean checkIfCanBuyProperty(){
        if (activePlayer.getMoney() > currentCard.getCost() && currentCard.isOwned() == false) return true;
        return false;
    }


    /**
     * this method is used to buy a property for a player
     */
    public void buyProperty(){
        this.activePlayer.buyCard(currentCard);
        for(GameView view : views)  view.unownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
    }

    /**
     * this method is used to see if a player can buy a house
     * @param c the tile to check on
     * @return the boolean of whether the player can buy a house or not
     */
    private boolean checkIfCanBuyHouse(Card c){ // Check if player has enough funds before calling this method
        boolean ownsAllTiles = true;
        ArrayList<Card> cards = new ArrayList<>();
        Color color = c.getColor();

        for (Integer integer : gameBoard.keySet()){
            Card card = gameBoard.get(integer);
            if (color.getRGB() == card.getColor().getRGB()){
                cards.add(card);
                if(card.getOwner() != activePlayer) ownsAllTiles = false;
            }
        }

        // Checks if all other cards have enough houses
        boolean allowedToBuyHouse = true;
        if (ownsAllTiles){
            for (Card card : cards){
                if (c.getHouses() - card.getHouses() > 0 || c.getHouses() == 4) allowedToBuyHouse = false;
            }
        }

        if (numOfHouses == 0 || !ownsAllTiles || activePlayer.getMoney() < c.getHouseCost()) allowedToBuyHouse = false;

        return allowedToBuyHouse;
    }

    /**
     * Buys the house
     * @param c the tile the house is on
     */
    public void buyHouse(Card c){
        numOfHouses--;
        c.setHouses(c.getHouses() + 1);
        c.setCost(c.getCost() + c.getHouseCost());
        activePlayer.setMoney(activePlayer.getMoney() - c.getHouseCost());
        // TODO should change the method call
        for(GameView view : views)  view.unownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
    }

    /**
     * this method is used to see if a player can buy a hotel
     * @param c the tile to check on
     * @return the boolean of whether the player can buy a hotel or not
     */
    private boolean checkIfCanBuyHotel(Card c){
        boolean canBuy = true;
        ArrayList<Card> cards = new ArrayList<>();
        Color color = c.getColor();

        for (Integer integer : gameBoard.keySet()){
            Card card = gameBoard.get(integer);
            if (color.getRGB() == card.getColor().getRGB()){
                cards.add(card);
            }
        }

        for (Card card : cards){ // We don't need to check if they own all tiles of the same color since having a house means they do
            if (card.getHouses() != 4 && card.getHotels() == 0) canBuy = false;
        }

        if (activePlayer.getMoney() < c.getHotelCost() || numOfHotels == 0) canBuy = false;
        return canBuy;
    }

    /**
     * converts the 4 houses into a hotel
     * @param c the tile the house is on
     */
    private void buyHotel(Card c){
        numOfHouses += 4;
        numOfHotels--;
        c.setHouses(0);
        c.setHotels(1);
        c.setCost(c.getCost() + c.getHotelCost());
        activePlayer.setMoney(activePlayer.getMoney() - c.getHotelCost());
        // TODO should change the method call
        for(GameView view : views)  view.unownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
    }

    /**
     * Getter for players
     * @return list of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    private Card getCard(String cardName){
        for (Integer key: gameBoard.keySet()) if (gameBoard.get(key).getName().equals(cardName)) return gameBoard.get(key);
        return null; // No card found
    }

    /**
     * Setter for Current Card.
     */
    public void setCurrentCard(int i ) {
        this.currentCard = gameBoard.get(i);
    }

    /**
     * Getter for Current Card.
     * @return Card
     */
    public Card getCurrentCard() {
        return currentCard;
    }

    /**
     * getter for the activePlayer
     * @return Returns a Player that is the current player
     */
    public Player getActivePlayer() {
        return players.get(currTurn);
    }

    /**
     * Gets the only player left in the game
     * @return the winner of type Player
     */
    public Player getWinner(){
        for (Player player : players){
            if (player.isPlaying()) return player;
        }
        return null;
    }

    /**
     * Adds a number of players to the game
     * @param playerNum the int for the number of players to be added
     */
    public void addPlayers(int playerNum, int botNum) {
        if(playerNum + botNum > 4 || (playerNum == 1 && botNum == 0)){
            return;
        }
        int i = 0;
        for (i = 0; i <playerNum; i++){
            this.players.add(new Player("P"+(i+1), false));
        }
        for(int j = 0; j < botNum; j++){
            this.players.add(new AutoPlayer("Bot"+ (j+i),  true));
        }
        activePlayer = players.get(0);

    }

    /**
     * Makes a player pay rent to another player
     * @param owner the player that owns the property and is receiving the money
     * @param card the card on which the property is on
     */
    public void payRent(Player owner, Card card) {
        this.activePlayer.payRent(owner, card);

    }
    // ---------------------------------------------------------------------------------
    /**
     * enum that holds the different statuses for the game, certain player winning or undecided
     */
    public static enum Status {
        WINNER,
        UNDECIDED,
    }

    public void save(){
        File directory = new File("xml folder\\");
        File[] files = directory.listFiles();
        for (File f :
                files) {
            f.delete();
        }
        for (Player p :
                players) {
            p.serializeToXML("xml folder\\"+p.getName() +".xml");
        }
        for (GameView v :
                views) {
            v.setImportButtonEnable(true);
        }
    }

    public void importXML() {

        ArrayList<Player> importedPLayers = new ArrayList<>();
        createGameBoard();
        setEnableRoll(true);
        boolean dontUpdate[] = new boolean[4];


        int k = 0;
        for (Player p :
                players) {
            p = Player.deserializeFromXML("xml folder\\" + p.getName() + ".xml");
            dontUpdate[k] = p.getPosition() == players.get(k).getPosition();
            if (p.getPosition() != players.get(k).getPosition())
                p.setPrevPosition(players.get(k).getPosition());
            if (p.getRolls()[0] != 0) setEnableRoll(false);
            importedPLayers.add(p);
            for (Card c :
                    p.getProperties()) {
                gameBoard.put(c.getPosition(), c);
            }
        k++;
        }
        players = importedPLayers;

        for (int i = 0; i < players.size(); i++) {
            if (!gameBoard.get(players.get(i).getPosition()).isOwned()) enableBuyButton();
            if (players.get(i).isActivePlayer()) {
                activePlayer = players.get(i);
            }
            if (!dontUpdate[i])
                views.get(0).updateFromImport(players.get(i), players.get(i).getRolls());
        }
    }











}
