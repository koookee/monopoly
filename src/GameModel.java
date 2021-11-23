/**
 * @author Andre, Jack, Cassidy, Hussein
 * This class represents the model of our monoploy game
 *
 */

import java.awt.*;
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
                gameBoard.put(i,new Card(streetNames[i],costs[i],position, colors[i], Card.CardType.railroad, houseCosts[i], hotelCosts[i]));
            }else if(i== 19 || i == 27) {
                gameBoard.put(i, new Card(streetNames[i], costs[i],position, colors[i], Card.CardType.ultility, houseCosts[i], hotelCosts[i]));
            }else if(i == 23){
                gameBoard.put(i, new Card(streetNames[i], costs[i],position, colors[i], Card.CardType.jail, houseCosts[i], hotelCosts[i]));
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
        activePlayer = players.get(currTurn);
        if(activePlayer instanceof AutoPlayer){
            botPlay();
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
        }

        return numToReturn;
    }


    /**
     * This method is called to play the game depending on the choice command
     * State 1 is the default state and it's what determines the player's
     * roll and checks for bankruptcy, winners, and how many times the
     * player rolled
     * State 2 asks the player if they want to pass
     * State 3 confirms the player pass
     * State 4 checks whether the player has to pay rent, can buy a property,
     * a house, or a hotel
     * State 5 confirms the player's decision of buying a property
     * State 6 confirms the player's decision of buying a house
     * State 7 confirms the player's decision of buying a hotel
     * State 8 sends the player to jail
     * @param state the int that determines the action to take
     */
    public void play(int state){
        if (status.name().equals("UNDECIDED")){
            if(state ==1 && hasNotRolled) {
                if (activePlayer.getIsInJail() != 0 && activePlayer.getIsInJail() < 3 && activePlayer.getMoney() >= 50) {
                    play(8);
                }
            }
            if (state == 1 && hasNotRolled) {

                /*
                dice1 = (int) (Math.random() * 6 + 1);
                dice2 = (int) (Math.random() * 6 + 1);
                roll = dice1 + dice2;


                /*
                // For debugging purposes (can make players move to specific tiles)
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter roll 1");
                int num = scanner.nextInt();
                dice1 = num;
                System.out.println("Enter roll 2");
                num = scanner.nextInt();
                dice2 = num;
                roll = dice1 + dice2;

                 */

                if(activePlayer.getIsInJail() != 0 && activePlayer.getIsInJail() < 3 && dice1!=dice2){       // JAIL TIME
                    for(GameView view : views){
                        view.announceJailTime(new GameEvent(this, status, currentCard, new int[]{dice1,dice2}));
                    }
                    int current = this.activePlayer.getIsInJail();
                    this.activePlayer.setIsInJail(current += 1 );
                    play(3);

                }
                else{
                    if(activePlayer.getIsInJail() >= 3 || (dice1==dice2 && activePlayer.getIsInJail()!=0)){
                        for(GameView view : views){
                            view.announceJailTime(new GameEvent(this, status, currentCard, new int[]{dice1,dice2}));
                        }
                        if(activePlayer.getIsInJail() >= 3){
                            activePlayer.setMoney(activePlayer.getMoney() - 50);

                        }
                        activePlayer.setIsInJail(0);

                    }

                    if(this.activePlayer.getPosition() + roll > 30) { // PASSING GO
                        activePlayer.setMoney(activePlayer.getMoney() + 200);
                    }
                    if(activePlayer.getExconvict()){
                        numTimesRolledDouble = 4;
                        activePlayer.setExconvict(false);
                    }

                    activePlayer.setPrevPosition(activePlayer.getPosition());
                    activePlayer.setPosition((activePlayer.getPosition() + roll) % gameBoard.size());
                    currentCard = gameBoard.get(activePlayer.getPosition());
                    //If player X turn set there position to += the roll amount



                    play(4);

                    int gameState = this.updateStatus();

                    for (GameView view : views) {
                        if (gameState == 1) {
                            view.announceBankruptcy(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                            play(3);
                        }
                        else if (dice1 == dice2 && numTimesRolledDouble <= 3) {
                            numTimesRolledDouble++;
                            hasNotRolled = true;
                        } else if (numTimesRolledDouble == 4){
                            play(3); // forces player to pass
                        }
                        else {
                            hasNotRolled = false;
                        }
                        if (status.name().equals("WINNER")) {
                            view.announceWinner(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                        }
                    }
                }



            } else if (state == 2) { // Ask if they're sure they want to pass
                for (GameView view : views) {
                    view.announcePlayerPass(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                }
            } else if (state == 3) { // Player confirms they want to pass
                this.updateStatus();
                this.changeTurn();
                numTimesRolledDouble = 0;
                hasNotRolled = true;
                for (GameView view : views) {
                    view.handleGameStatusUpdate(new GameEvent(this, status, currentCard, new int[]{0, 0})); // Player didn't roll yet
                }
            } else if (state == 4) { // Landed on a card
                landedOnCard();
            } else if (state == 5) { // Confirms buying property
                int result = currentCard.functionality(activePlayer);
                if (result == 0) buyProperty();
            } else if (state == 6) { // Confirms buying house
                buyHouse();
            } else if (state == 7) { // Confirms buying hotel
                buyHotel();
            } else if( state == 8) {
                for (GameView view : views) {
                    view.payJailFee(new GameEvent(this, status, currentCard, new int[]{0, 0})); // Player didn't roll yet
                }
            }
        }


    }

    /**
     * Determines what to do when a player lands on a card
     */
    private void landedOnCard(){
        int result = currentCard.functionality(activePlayer);
        for (GameView view : views) {
            view.handleGameStatusUpdate(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
            if (result == 0) { // No owners
                view.unownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
            } else if (result == 1) { // Owns property
                if (currentCard.getHouses() < 4 && currentCard.getHotels() == 0 && checkIfCanBuyHouse()) view.askToBuyHouse(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                else if (currentCard.getHouses() == 4 && checkIfCanBuyHotel()) view.askToBuyHotel(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
            }
            else if (result == 2) { // Has to pay rent
                view.ownedProperty(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
            } else if (result == 3){
                putInJail();
                view.handleGameStatusUpdate(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
                view.announceToJail(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
            }
        }
    }

    public void botPlay() {
        dice1 = 1; //(int) (Math.random() * 6 + 1);
        dice2 = 1;//(int) (Math.random() * 6 + 1);
        roll = dice1 + dice2;

        activePlayer.setPrevPosition(activePlayer.getPosition());
        activePlayer.setPosition((activePlayer.getPosition() + roll) % gameBoard.size());
        currentCard = gameBoard.get(activePlayer.getPosition());


        if(currentCard.getCardType() == Card.CardType.jail){
            this.activePlayer.setPosition(8);
            this.activePlayer.setIsInJail(1);
        }

        if(activePlayer.getIsInJail() != 0 && activePlayer.getIsInJail() < 3 && dice1!=dice2){
            for(GameView view: views){
                view.announceJailTime(new GameEvent(this,status,currentCard, new int[]{dice1,dice2}));
            }
            int current = this.activePlayer.getIsInJail();
            this.activePlayer.setIsInJail(current += 1);
            play(3);
        }
        else{
            if(activePlayer.getIsInJail() >= 3 || (dice1==dice2 && activePlayer.getIsInJail() != 0)){
                for(GameView view: views){
                    view.announceJailTime(new GameEvent(this,status,currentCard, new int[]{dice1,dice2}));
                }
                activePlayer.setIsInJail(0);
            }
            if(this.activePlayer.getPosition() + roll > 30){
                activePlayer.setIsInJail(activePlayer.getMoney() + 200);
            }

        }

        if(currentCard.isOwned()){
            activePlayer.payRent(currentCard.getOwner(),currentCard);
            for(GameView view: views){
                view.announcePaidBotRent(new GameEvent(this, status, currentCard, new int[]{dice1,dice2}));
            }

        }
        else{
            if(activePlayer.getMoney() > currentCard.getCost() && currentCard.getCost() != 0){
                activePlayer.buyCard(currentCard);
                for(GameView view: views){
                    view.announceBoughtBotProperty(new GameEvent(this, status, currentCard, new int[]{dice1,dice2}));
                }

            }
        }




        this.updateStatus();

        for (GameView view :
                views) {
            view.handleGameStatusUpdate(new GameEvent(this, status, currentCard, new int[]{dice1, dice2}));
        }
        if (dice1 == dice2 && numTimesRolledDouble < 3) {
            numTimesRolledDouble++;
            System.out.println("The number of times " +activePlayer.getName()  + " rolled double is " + numTimesRolledDouble);
            botPlay();
        }

        else {

            changeTurn();
            hasNotRolled = true;
            numTimesRolledDouble = 0;
        }

        this.updateStatus();
        changeTurn();

    }


    /**
     * this method is used to buy a property for a player
     */
    public void buyProperty(){
        this.activePlayer.buyCard(currentCard);
    }

    public void putInJail(){
        activePlayer.setPosition(8);
        activePlayer.setIsInJail(1);
    }

    /**
     * this method is used to buy a house for a player
     * @return the boolean of whether the player can buy a house or not
     */
    private boolean checkIfCanBuyHouse(){ // Check if player has enough funds before calling this method
        boolean ownsAllTiles = true;
        ArrayList<Card> cards = new ArrayList<>();
        Color color = currentCard.getColor();

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
                if (currentCard.getHouses() - card.getHouses() > 0 || currentCard.getHouses() == 4) allowedToBuyHouse = false;
            }
        }

        if (numOfHouses == 0 || !ownsAllTiles || activePlayer.getMoney() < currentCard.getHouseCost()) allowedToBuyHouse = false;

        return allowedToBuyHouse;
    }

    /**
     * Buys the house
     */
    private void buyHouse(){
        numOfHouses--;
        currentCard.setHouses(currentCard.getHouses() + 1);
        currentCard.setCost(currentCard.getCost() + currentCard.getHouseCost());
        activePlayer.setMoney(activePlayer.getMoney() - currentCard.getHouseCost());
    }

    /**
     * this method is used to buy a hotel for a player
     * @return the boolean of whether the player can buy a hotel or not
     */
    private boolean checkIfCanBuyHotel(){
        boolean canBuy = true;
        ArrayList<Card> cards = new ArrayList<>();
        Color color = currentCard.getColor();

        for (Integer integer : gameBoard.keySet()){
            Card card = gameBoard.get(integer);
            if (color.getRGB() == card.getColor().getRGB()){
                cards.add(card);
            }
        }

        for (Card card : cards){ // We don't need to check if they own all tiles of the same color since having a house means they do
            if (card.getHouses() != 4 && card.getHotels() == 0) canBuy = false;
        }

        if (activePlayer.getMoney() < currentCard.getHotelCost() || numOfHotels == 0) canBuy = false;
        return canBuy;
    }

    /**
     * converts the 4 houses into a hotel
     */
    private void buyHotel(){
        numOfHouses += 4;
        numOfHotels--;
        currentCard.setHouses(0);
        currentCard.setHotels(1);
        currentCard.setCost(currentCard.getCost() + currentCard.getHotelCost());
        activePlayer.setMoney(activePlayer.getMoney() - currentCard.getHotelCost());
    }

    /**
     * Getter for players
     * @return list of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
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
        for (int i = 0; i <playerNum; i++){
            this.players.add(new Player("P"+(i+1), false));
        }
        for(int j = 0; j < botNum; j++){
            this.players.add(new AutoPlayer("Bot"+ (j+1),  true));
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

    /*
    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        for (Card c :
                gameModel.getGameBoard().values()) {
            System.out.println(c.getCardType());
        }
    }

     */

}
