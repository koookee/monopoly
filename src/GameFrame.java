/**
 * @author Andre, Jack, Cassidy, Hussein
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;


public class GameFrame extends JFrame implements GameView {
    private GameModel model;

    private ArrayList<JLayeredPane> squares;


    private final JLabel icon = new JLabel(new ImageIcon("boot.png"));
    private final JLabel icon2 = new JLabel(new ImageIcon("pin.png"));
    private final JLabel icon3 = new JLabel(new ImageIcon("iron.png"));
    private final JLabel icon4 = new JLabel(new ImageIcon("hat.png"));
    private JButton rollButton;

    private JLabel[] icons;

    private boolean enableRoll;

    private boolean enableBuy;

    private int playerNum;
    private final int botSquares = 9, leftSquares = 15, topSquares =24, rightSquares = 31;
    private final int botSquareNum = 9, leftSquareNum =6 , topSquareNum =9,rightSquareNum = 7;
    private  JPanel mainPanel;
    private JPanel playerPanel;
    private ArrayList<JPanel> squareBottomArr; // Will display information about who owns the card and how many houses/hotels it has
    private ArrayList<JButton> squareCenterArr; // Stores the buy buttons
    private JFrame buyOptionsWindow;
    private JButton buyButton;
    private JButton pass;

    private WelcomeController welcomeControl;

    private int[] startInformation;


    /**
     * Constructor for GameFrame the class
     */
    public GameFrame() {
        super("Monopoly");
        this.model = new GameModel();
        this.squares = new ArrayList<>();
        this.squareBottomArr = new ArrayList<>();
        this.squareCenterArr = new ArrayList<>();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.icons = new JLabel[]{ icon, icon2, icon3, icon4};
        Dimension frameSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(frameSize.width, frameSize.height - 20);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        enableRoll = true;




        model = new GameModel();
        model.addGameModelView(this);

        welcomeControl = new WelcomeController(model);

        startInformation =welcomeControl.askPlayerImport(this);
        this.playerNum = startInformation[0];




        this.createSquares();
        this.mainPanel = paintBoard();

        this.playerPanel = paintPlayerInfo(model.getActivePlayer(), new int[]{0,0});



        icon.setBounds(10,40, 50,50);
        icon2.setBounds(50,40, 50,50);
        icon3.setBounds(90,40, 50,50);
        icon4.setBounds(130,40, 50,50);

        enableBuy = false;


    }


    /**
     * Creates the squares for the tiles
     */
    private void createSquares() {
        for (int i = 0; i < this.model.getGameBoard().size(); i++) {

            Card c = this.model.getGameBoard().get(i);
            JLayeredPane layeredPane = new JLayeredPane();
            JPanel square = new JPanel(new BorderLayout());
            square.setBorder(new LineBorder(Color.black));

            JPanel squareTop = new JPanel();
            JPanel squareBottom = new JPanel(new GridLayout(1, 5)); // 1 column for player 4 columns for potential houses

            JButton buyButton = new JButton("Buy");
            buyButton.setVisible(false);

            JLabel name;
            squareTop.setBackground(c.getColor());
            if (c.getCost() != 0)  name= new JLabel(c.getName() + " ($" + c.getCost()+")");
            else  name = new JLabel(c.getName());
            if (c.getCardType() == Card.CardType.railroad) name.setForeground(Color.white);

            int width = Toolkit.getDefaultToolkit().getScreenSize().width;
            int height = Toolkit.getDefaultToolkit().getScreenSize().height;

            squareTop.add(name);
            if(i<=botSquares-1){
                layeredPane.setPreferredSize(new Dimension(width/botSquares,height/leftSquareNum-2));
                square.setPreferredSize(new Dimension(100,150));
                square.setBounds(new Rectangle(width/botSquareNum,height/leftSquareNum-2));
            }
            else if (i>botSquares-1 && i<=leftSquares-1 ) {

                layeredPane.setPreferredSize(new Dimension(290, (height - (width / topSquareNum)) / (8)));
                square.setPreferredSize(new Dimension(290, (height - (width / topSquareNum)) / 8));
                square.setBounds(new Rectangle(290, (height - (width / topSquareNum)) / (8)));
            }
            else if(i>topSquares-1 && i<=rightSquares-1 ){
                layeredPane.setPreferredSize(new Dimension(290,(height- (width/topSquareNum))/(9)));
                square.setPreferredSize(new Dimension(290,(height- (width/topSquareNum))/9));
                square.setBounds(new Rectangle(290,(height- (width/topSquareNum))/(9)));
            }else if(i>leftSquares-1 && i<=topSquares-1){
                layeredPane.setPreferredSize(new Dimension(width/topSquareNum,height/leftSquareNum-2));
                square.setPreferredSize(new Dimension(100,150));
                layeredPane.setBorder(new LineBorder(Color.black));
                square.setBounds(new Rectangle(width/topSquareNum,height/leftSquareNum-2));
            }

            square.add(squareTop, BorderLayout.PAGE_START);
            square.add(squareBottom, BorderLayout.PAGE_END);
            square.add(buyButton, BorderLayout.CENTER);

            layeredPane.add(square,JLayeredPane.DEFAULT_LAYER);

            if(i==0){
                for (int j = 0; j < this.playerNum; j++) {
                    layeredPane.add(icons[j],JLayeredPane.PALETTE_LAYER);
                }

            }
            squareCenterArr.add(buyButton);
            squareBottomArr.add(squareBottom);
            squares.add(layeredPane);
        }

    }

    /**
     * Creates the panel that has the GUI for the board
     * @return the JPanel of the monopoly board
     */
    private JPanel paintBoard() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new LineBorder(Color.black));
        JPanel bot = new JPanel(new GridLayout(1, botSquareNum));
        JPanel left = new JPanel(new GridLayout(leftSquareNum, 1));
        JPanel top = new JPanel(new GridLayout(1, topSquareNum));
        JPanel right = new JPanel(new GridLayout(rightSquareNum, 1));
        mainPanel.add(bot, BorderLayout.PAGE_END);
        mainPanel.add(left, BorderLayout.LINE_START);
        mainPanel.add(top, BorderLayout.PAGE_START);
        mainPanel.add(right, BorderLayout.LINE_END);

        for (int i = 0; i < squares.size(); i++) {
            if (i <= botSquares - 1) {
                bot.add(squares.get(botSquares - i -1));
            } else if (i > botSquares - 1 && i <= leftSquares - 1) {
                left.add(squares.get(leftSquares+botSquares-i-1));
            } else if (i > leftSquares - 1 && i <= topSquares - 1) {
                top.add(squares.get(i));
            } else if (i > topSquares - 1 && i <= rightSquares - 1) {
                right.add(squares.get(i));
            }
        }

        return mainPanel;
    }


    /**
     * Creates the panel for the player information
     * @param activePlayer the current active player of type Player
     * @param roll the int array of what the player rolled
     * @return the JPanel that has the player information
     */
    private JPanel paintPlayerInfo(Player activePlayer, int[] roll){

        
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel playerName = new JLabel("Player: " + activePlayer.getName());
        mainPanel.add(playerName, BorderLayout.PAGE_START);
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.PAGE_AXIS));
        JLabel playerMoney = new JLabel("Player Money: $" + activePlayer.getMoney() + "\n");
        bodyPanel.add(playerMoney);
        //String playerProperties = "";
        JLabel playerPropertiesLabel = new JLabel("Player Properties:");
        bodyPanel.add(playerPropertiesLabel);
        for (Card c : activePlayer.getProperties()) {
            JLabel propertyLabel = new JLabel("- " + c.getName());
            bodyPanel.add(propertyLabel);
        }


        JLabel playerPosition = new JLabel("Player Position: " + activePlayer.getPosition());
        bodyPanel.add(playerPosition);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        JLabel playerRoll;

        if (roll[0] == 0 && roll [1] == 0) {
            playerRoll = new JLabel("Player hasn't rolled yet");

        }

        else if(roll[0] == roll[1]) {
            playerRoll = new JLabel("Player Rolled : " + roll[0] + " "+ roll[1] + " (Can roll again)");
        }
        else{
            playerRoll = new JLabel("Player Rolled : " + roll[0] + " " + roll[1]);
        }

        bodyPanel.add(playerRoll);
        mainPanel.add(bodyPanel,BorderLayout.CENTER);
        GameController controller = new GameController(model);

        JPanel footerPanel = new JPanel(new GridLayout(5, 3));

        rollButton = new JButton("Roll");
        buyButton = new JButton("Buy");
        pass = new JButton("Next Turn");

        footerPanel.add(rollButton);


        footerPanel.add(buyButton);


        footerPanel.add(pass);

        JButton save = new JButton("save");
        footerPanel.add(save);


        JButton importButton = new JButton("import");
        footerPanel.add(importButton);

        save.setActionCommand("save");
        save.addActionListener(controller);
        if (startInformation[1] == 1) importButton.setEnabled(false);
        importButton.setActionCommand("import");
        importButton.addActionListener(controller);


        rollButton.setEnabled(enableRoll);
        rollButton.setActionCommand("roll"); //TODO fix the action command so its not a string
        rollButton.addActionListener(controller);

        buyButton.setEnabled(enableBuy);
        buyButton.setActionCommand("displayBuyOptions");
        buyButton.addActionListener(controller);

        pass.setEnabled(!enableRoll);
        pass.setActionCommand("next turn");
        pass.addActionListener(controller);





        /*
        JButton buy = new JButton("Buy");
        buy.setEnabled(buyEnabled);
        buy.setActionCommand(4 + " ");
        buy.addActionListener(controller);
        footerPanel.add(buy);

         */

        mainPanel.add(footerPanel, BorderLayout.PAGE_END);
        return mainPanel;

    }

    /**
     * Updates the GUI of each card on the board
     * @param card the Card that gets updated
     */
    public void updateCardGUI(Card card){
        JPanel gridPanel = squareBottomArr.get(card.getPosition());
        int num = gridPanel.getComponentCount(); // First component is the player label. Next 4 are potentially the houses (or hotel)
        for (int i = 0; i < num; i++) gridPanel.remove(0);
        if (card.getOwner() != null) gridPanel.add(new JLabel(card.getOwner().getName()));
        if (card.getHouses() > 0) {
            for (int i = 0; i < card.getHouses(); i++){
                gridPanel.add(new JLabel(new ImageIcon("house.png")));
            }
        }
        else if (card.getHotels() == 1){
            gridPanel.add(new JLabel(new ImageIcon("hotel.png")));
        }
    }

    /*
    /**
     * Displays the buy button on each card the player owns

    public void displayBuyButtonOnCard(Player p){
        Map<Integer, Card> board = model.getGameBoard();
        for (Integer key : board.keySet()){                 // TEMPORARILY
            if (board.get(key).getOwner() != null && board.get(key).getOwner().getName().equals(p.getName())) { //TODO: Would ideally use == but that compares position and name. Could we change it to only check for name?
                squareCenterArr.get(board.get(key).getPosition()).setVisible(true);
            }
            else squareCenterArr.get(board.get(key).getPosition()).setVisible(false);
        }
    }

     */

    /**
     * Displays the GUI of the whole game
     */
    public void displayGUI() {
        this.setLayout(new BorderLayout());
        //this.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height ));

        this.mainPanel.revalidate();
        this.playerPanel.revalidate();

        this.add(playerPanel, BorderLayout.LINE_END);
        this.add(mainPanel, BorderLayout.CENTER);

        for (Integer key : model.getGameBoard().keySet()){
            updateCardGUI(model.getGameBoard().get(key));
        }


        this.revalidate();
        this.setVisible(true);
    }

    /**
     * This class handles the update to the view of the game class
     *
     * @param e is a game event that holds useful information
     */
    @Override
    public void handleGameStatusUpdate(GameEvent e) {
        this.model = (GameModel) e.getSource();

        getContentPane().remove(playerPanel);

        playerPanel = paintPlayerInfo(model.getActivePlayer(), e.getRoll());

        displayGUI();

        updatePlayerIcon(model.getActivePlayer(), e.getRoll());

    }
    @Override
    public void updateFromImport(Player p, int[] roll ){
//        updatePlayerIcon(p,roll);


        getContentPane().remove(playerPanel);

        if (p.isActivePlayer())
        playerPanel = paintPlayerInfo(p, roll);

        displayGUI();


        updatePlayerIcon(p, roll);



    }

    @Override
    public void announceBoughtBotProperty(GameEvent gameEvent) {
        Player player = gameEvent.getModel().getActivePlayer();
        Card card = gameEvent.getCard();
        JOptionPane.showMessageDialog(this,player.getName() + " has bought " + card.getName() , null, JOptionPane.PLAIN_MESSAGE);
    }

    public void announcePaidBotRent(GameEvent gameEvent) {
        Player player = gameEvent.getModel().getActivePlayer();
        Card card = gameEvent.getCard();
        JOptionPane.showMessageDialog(this,player.getName() + " has paid rent to " + card.getOwner().getName() + " for " + card.getName(), null, JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void enableBuyButton(Player p) {
        enableBuy = true;
        buyButton.setEnabled(true);
       //isplayBuyButtonOnCard(p);
    }
    @Override
    public void disableBuyButton() {
        enableBuy = false;
        buyButton.setEnabled(false);

    }

    @Override
    public void setRollEnable(boolean b) {

        enableRoll = b;
        rollButton.setEnabled(b);
        pass.setEnabled(!b);

    }

    @Override
    public void displayBuyArrOptions(ArrayList<Card> buyArrOptions) {
        buyOptionsWindow = new JFrame("Buy Options");
        buyOptionsWindow.setLayout(new BorderLayout());
        JPanel grid = new JPanel(new GridLayout(buyArrOptions.size(), 2));
        buyOptionsWindow.add(grid, BorderLayout.CENTER);
        for (Card c : buyArrOptions){
            GameController controller = new GameController(model);
            String buyButtonName;
            if (c.isOwned() == false) buyButtonName = "Buy property";
            else if (c.getHouses() == 4) buyButtonName = "Buy hotel";
            else buyButtonName = "Buy house";
            JLabel nameAndCost = new JLabel("Name: " + c.getName() + " | Cost: " + c.getCost());
            JButton button = new JButton(buyButtonName);
            button.addActionListener(controller);
            button.setActionCommand(c.getName());
            grid.add(nameAndCost);
            grid.add(button);
        }
        buyOptionsWindow.setSize(600, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buyOptionsWindow.setVisible(true);
    }

    /**
     * Gives the player information about the property they landed on and the option to buy (if possible)
     * @param gameEvent is a game event that holds useful information
     */
    @Override
    public void unownedProperty(GameEvent gameEvent) {
        GameModel model = gameEvent.getModel();
//        CardController unowned = new CardController(model);
//        Card card = gameEvent.getCard();
//
//        if(card.getCost() !=0 && model.getActivePlayer().getMoney() >= card.getCost()){
//            if (card.getCardType() == Card.CardType.ultility){
//                unowned.buyProperty(this,"You landed on " + card.getName() + ". Cost is $" + card.getCost() +
//                        "\nRent is dependent on your roll"+ "\nWould you like to purchase?");
//            }else {
//                unowned.buyProperty(this, "You landed on " + card.getName() + ". Cost is $" + card.getCost() +
//                        "\nRent is $" + card.getRent() + "\nWould you like to purchase?");
//            }
//        }
//
        getContentPane().remove(playerPanel);
        playerPanel = paintPlayerInfo(model.getActivePlayer(),gameEvent.getRoll());
        buyOptionsWindow.dispatchEvent(new WindowEvent(buyOptionsWindow, WindowEvent.WINDOW_CLOSING)); // Closes the window that has the options of buying a property, house, or hotel
        displayGUI();
    }

    /**
     * Gives the player information about the property they landed on and charges them rent
     * @param gameEvent is a game event that holds useful information
     */
    @Override
    public void ownedProperty(GameEvent gameEvent) {
        GameModel model = gameEvent.getModel();
        CardController owned = new CardController(model);
        Card card = gameEvent.getCard();

        owned.payRent(this, "You landed on " + card.getName() + ". You must pay $" + card.getRent() + " to " + card.getOwner().getName());
        model.payRent(card.getOwner(), card);
        getContentPane().remove(playerPanel);
        playerPanel = paintPlayerInfo(model.getActivePlayer(),gameEvent.getRoll());

        displayGUI();
    }

    /**
     * Asks the player if they want to buy a house
     * @param gameEvent is a game event that holds useful information
     */
    @Override
    public void askToBuyHouse(GameEvent gameEvent) {
        GameModel model = gameEvent.getModel();
        CardController controller = new CardController(model);
        Card card = gameEvent.getCard();

        controller.askToBuyHouse(this, "Would you like to buy a house for $" + card.getHouseCost() + "?");

        getContentPane().remove(playerPanel);
        playerPanel = paintPlayerInfo(model.getActivePlayer(),gameEvent.getRoll());

        displayGUI();
    }

    /**
     * Asks the player if they want to buy a hotel
     * @param gameEvent is a game event that holds useful information
     */
    @Override
    public void askToBuyHotel(GameEvent gameEvent) {
        GameModel model = gameEvent.getModel();
        CardController controller = new CardController(model);
        Card card = gameEvent.getCard();

        controller.askToBuyHotel(this, "Would you like to buy a hotel for $" + card.getHotelCost() + "?");

        getContentPane().remove(playerPanel);
        playerPanel = paintPlayerInfo(model.getActivePlayer(),gameEvent.getRoll());

        displayGUI();
    }

    /**
     * Asks the player if they want to pass their turn
     * @param gameEvent is a game event that holds useful information
     */
    @Override
    public void announcePlayerPass(GameEvent gameEvent) {
        GameModel model = gameEvent.getModel();
        CardController passed = new CardController(model);
        passed.confirmPass(this, "Confirm that you would like to end your turn");
    }

    /**
     * Announces that a player went bankrupt
     * @param gameEvent is a game event that holds useful information
     */
    @Override
    public void announceBankruptcy(GameEvent gameEvent) {
        GameModel model = gameEvent.getModel();
        GameController control = new GameController(model);
        control.bankruptcy(this, (model.getActivePlayer().getName()) + " has gone bankrupt sux 2 suk"); // Added + 1 because getCurrTurn returns 0 - 3
        squares.get(model.getActivePlayer().getPosition()).remove(icons[model.getPlayers().indexOf(model.getActivePlayer())]);
        squares.get(model.getActivePlayer().getPosition()).revalidate();
        squares.get(model.getActivePlayer().getPosition()).repaint();
    }

    /**
     * Announces the winner of the game
     * @param gameEvent is a game event that holds useful information
     */
    @Override
    public void announceWinner(GameEvent gameEvent) {
        GameModel model = gameEvent.getModel();
        GameController control = new GameController(model);
        control.winner(this, model.getWinner().getName() + " is the winner!");
    }

    /**
     * Announces that the player is sent to jail
     * @param gameEvent is a game event that holds useful information
     */    @Override
    public void announceToJail(GameEvent gameEvent) {
        GameModel model = gameEvent.getModel();
        CardController control = new CardController(model);
        control.announceToJail(this, gameEvent.getModel().getActivePlayer().getName()
                +" has been Sent To Jail!");
    }

    @Override
    public void announceJailTime(GameEvent gameEvent){

        GameModel model = gameEvent.getModel();
        CardController control = new CardController(model);
        if(gameEvent.getRoll()[0] == gameEvent.getRoll()[1]){
            control.announceDouble(this, "You are out of Jail!\nYou Rolled: " + gameEvent.getRoll()[0] +" " + gameEvent.getRoll()[1]);
        } else if(model.getActivePlayer().getIsInJail() >= 3){
            control.announceJailTime(this, "You have served your sentence and have been fined $50");
        }
        else {
            control.announceJailTime(this, "You did not roll a double! \nTime in Jail: "
                    + model.getActivePlayer().getIsInJail());
        }
    }

    @Override
    public void payJailFee(GameEvent gameEvent) {
        GameModel model = gameEvent.getModel();
        CardController jailFee = new CardController(model);
        jailFee.payJailFee(this, "Would you like to pay 50$ to get out of Jail?");

    }


    /**
     * Updates the player icons on the board GUI
     * @param activePlayer the current active player of type Player
     * @param roll the int array of what they rolled
     */
    private void updatePlayerIcon(Player activePlayer, int[] roll) {
        int position = activePlayer.getPosition();
        int prev = activePlayer.getPrevPosition();

        if(roll[0] != 0 && roll[1] != 0){
            if(activePlayer.getName().equals("P1")){
                //squares.get(position).add(icon);

                squares.get(prev).remove(icons[0]);
                squares.get(position).add(icons[0],JLayeredPane.PALETTE_LAYER);
                squares.get(prev).revalidate();
                squares.get(prev).repaint();


            }else if(activePlayer.getName().equals("P2") ||activePlayer.getName().equals("Bot1")){

                squares.get(prev).remove(icons[1]);
                squares.get(position).add(icons[1],JLayeredPane.PALETTE_LAYER);
                squares.get(prev).revalidate();
                squares.get(prev).repaint();

            }
            else if (activePlayer.getName().equals("P3")||activePlayer.getName().equals("Bot2")) {

                squares.get(prev).remove(icon3);
                squares.get(position).add(icon3,JLayeredPane.PALETTE_LAYER);
                squares.get(prev).revalidate();
                squares.get(prev).repaint();

//                if (position >botSquares-1 && position<=leftSquares-1 || position> topSquares-1 && position <= rightSquares-1){
//                    icon3.setBounds(150,75,50,50);
//                }else icon3.setBounds(150,100, 50,50);
//                icon4.setBounds(1,100, 50,50);



            }
            else if (activePlayer.getName().equals("P4")|| position> topSquares-1 && position <= rightSquares-1
                    ||activePlayer.getName().equals("Bot3")) {

                squares.get(prev).remove(icon4);
                squares.get(position).add(icon4,JLayeredPane.PALETTE_LAYER);
                squares.get(prev).revalidate();
                squares.get(prev).repaint();
//                if (position >botSquares-1 && position<=leftSquares-1){
//                    icon4.setBounds(1,60,50,50);
//                }else icon4.setBounds(1,100, 50,50);

            }
        }


    }


    /**
     * Main method that runs the program
     * @param args the arguments passed of type String array
     */
    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();

        gameFrame.displayGUI();


    }
}

