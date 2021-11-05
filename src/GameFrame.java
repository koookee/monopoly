import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.jar.JarEntry;

public class GameFrame extends JFrame implements GameView {

    private Parser parser;
    private boolean inGame;
    private boolean inMenu;
    private GameModel model;
    private Map<Integer,Card> board;
    private ArrayList<JPanel> squares;

    private GameModel gameModel;
    private final int botSquares = 6, leftSquares = 12, topSquares =17, rightSquares = 23;
    private  JPanel mainPanel;
    private JPanel playerPanel;

    public GameFrame(){
        super("Monopoly");
        this.model = new GameModel();
        model.addGameModelView(this);
        this.squares = new ArrayList<>();
        this.board = model.getGameBoard();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600,1024);
        this.createSquares();
        this.mainPanel = paintBoard();
        this.playerPanel = paintPlayerInfo(model.getActivePlayer());

        // New stuff -----

        parser = new Parser();
        inMenu = true;
        inGame = false;
        model = new GameModel();
        model.addGameModelView(this);

        // ---------------

    }


    private void createSquares(){
        for (int i = 0; i < this.model.getGameBoard().size(); i++) {

            Card c = this.model.getGameBoard().get(i);
            JPanel square = new JPanel(new BorderLayout());
            square.setBorder(new LineBorder(Color.black));
            square.setPreferredSize(new Dimension(150,200));
            JPanel squareTop = new JPanel();
            squareTop.setBackground(c.getColor());
            JLabel name = new JLabel(c.getName());


            squareTop.add(name);
            square.add(squareTop, BorderLayout.PAGE_START);


            squares.add(square);
        }
        
    }

    private JPanel paintBoard(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new LineBorder(Color.black));
        JPanel bot = new JPanel(new GridLayout(1,6));
        JPanel left = new JPanel(new GridLayout(6,1));
        JPanel top = new JPanel(new GridLayout(1,5));
        JPanel right = new JPanel(new GridLayout(6,1));
        mainPanel.add(bot, BorderLayout.PAGE_END);
        mainPanel.add(left ,BorderLayout.LINE_START);
        mainPanel.add(top, BorderLayout.PAGE_START);
        mainPanel.add(right, BorderLayout.LINE_END);
        for (int i = 0; i < squares.size(); i++) {
            if(i<=botSquares-1){
                bot.add(squares.get(5-i));
            }else if(i>botSquares-1 && i<=leftSquares-1){
                left.add(squares.get(i));
            }
            else if(i>leftSquares-1 && i<=topSquares-1){
                top.add(squares.get(i));
            }
            else if(i>topSquares-1 && i<=rightSquares-1){
                right.add(squares.get(i));
            }
        }
        return mainPanel;
    }

    private JPanel paintPlayerInfo(Player activePlayer){
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel playerName = new JLabel("Player: " + activePlayer.getName());
        mainPanel.add(playerName, BorderLayout.PAGE_START);
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel,BoxLayout.PAGE_AXIS));
        JLabel playerMoney = new JLabel("Player Money: $"+ activePlayer.getMoney()+ "\n");
        bodyPanel.add(playerMoney);
        String playerProperties = "";
        for (Card c :
                activePlayer.getProperties()) {
            playerProperties += c.getName() + " \n";
        }
        JLabel playerPropertiesLabel = new JLabel("Player Properties :" + playerProperties);
        bodyPanel.add(playerPropertiesLabel);

        JLabel playerPosition = new JLabel("Player Position: " + activePlayer.getPosition());
        bodyPanel.add(playerPosition);

        mainPanel.add(bodyPanel,BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new GridLayout(3,3));
        JButton rollButton = new JButton("Roll");
        rollButton.addActionListener(e -> model.play());
        footerPanel.add(rollButton);
        mainPanel.add(footerPanel, BorderLayout.PAGE_END);
        return mainPanel;

    }
    public void displayGUI(){
        this.setLayout(new BorderLayout());
        this.mainPanel.revalidate();
        this.playerPanel.revalidate();
        this.add(playerPanel, BorderLayout.LINE_END);
        this.add(mainPanel,BorderLayout.CENTER);



        this.setVisible(true);
    }

    /**
     * This class handles the update to the view of the game class
     * @param e is a game event that holds useful information
     */
    @Override
    public void handleGameStatusUpdate(GameEvent e) {
        /*
        this.gameModel = (GameModel) e.getSource();
        if (!e.getStatus().equals(GameModel.Status.UNDECIDED)){
            System.out.println("game is over");
            inGame = false;
        }
        else {
            System.out.println(gameModel.getActivePlayer().getName() + " rolled " + e.getRoll()[0] + " " + e.getRoll()[1]);
            System.out.println("The card you are on is " + e.getCard().getName() + " cost: " + e.getCard().getCost() + " color: " + e.getCard().getColor());

            if (!e.getCard().isOwned()) {
                System.out.println("Would you like to buy this property? or pass");
                Command command = parser.getCommand();
                gameModel.processCommand(command, 2);
                while (!command.getCommandWord().equals("buy") && !command.getCommandWord().equals("pass") && !command.getCommandWord().equals("quit")){
                    command = parser.getCommand();
                    gameModel.processCommand(command, 2);
                }
            } else {
                System.out.println(e.getCard().getOwner().getName() + " owns this property lol");
                gameModel.getActivePlayer().payRent(e.getCard().getOwner(), e.getCard());
                System.out.println("you paid " + e.getCard().getOwner().getName() + " " + e.getCard().getRent() + " dollars");
                System.out.println("You now have " + gameModel.getActivePlayer().getMoney() + " dollars");
            }
        }
        
         */

        this.model = (GameModel) e.getSource();
        System.out.println("doing stuff");
        playerPanel = paintPlayerInfo(model.getActivePlayer());
        displayGUI();
    }


    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.displayGUI();

    }
}
