import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


import java.util.ArrayList;
import java.util.Map;


public class GameFrame extends JFrame implements GameView {


    private GameModel model;
    private Map<Integer,Card> board;
    private ArrayList<JPanel> squares;

    private final JLabel icon = new JLabel(new ImageIcon("boot.png"));
    private final JLabel icon2 = new JLabel(new ImageIcon("pin.png"));
    private final JLabel icon3 = new JLabel(new ImageIcon("iron.png"));
    private final JLabel icon4 = new JLabel(new ImageIcon("hat.png"));

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
        this.playerPanel = paintPlayerInfo(model.getActivePlayer(), new int[]{0,0});


        model = new GameModel();
        model.addGameModelView(this);



    }


    private void createSquares(){
        for (int i = 0; i < this.model.getGameBoard().size(); i++) {

            Card c = this.model.getGameBoard().get(i);
            JPanel square = new JPanel(new GridLayout(3,1));
            square.setBorder(new LineBorder(Color.black));

            JPanel squareTop = new JPanel();
            squareTop.setBackground(c.getColor());
            JLabel name = new JLabel(c.getName());
            JPanel squareBot = new JPanel();
            squareBot.setBorder(new LineBorder(Color.black));

            squareTop.add(name);
            if (i>botSquares-1 && i<=leftSquares-1 ||i>topSquares-1 && i<=rightSquares-1  ){
                square.setPreferredSize(new Dimension(290,300));
                squareBot.setPreferredSize(new Dimension(290,25));

            }else{
                square.setPreferredSize(new Dimension(100,100));
                squareBot.setPreferredSize(new Dimension(100,25));

            }
            JLabel price = new JLabel("$" + c.getCost());
            if(c.getCost() !=0){
                squareBot.add(price);
            }

            JPanel squareBody = new JPanel(new GridLayout(1,4));

            square.add(squareTop);
            square.add(squareBody);
            square.add(squareBot);

            squares.add(square);
        }
        squares.get(0).add(icon,1);
        squares.get(0).add(icon2,1);
        squares.get(0).add(icon3,1);
        squares.get(0).add(icon4,1);
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
                bot.add(squares.get(botSquares-i-1));
            }else if(i>botSquares-1 && i<=leftSquares-1){
                left.add(squares.get(leftSquares+botSquares-i-1));
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

    private JPanel paintPlayerInfo(Player activePlayer, int[] roll){
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

        JLabel playerRoll = new JLabel("Player Rolled : " + roll[0] + " "+ roll[1]);
        bodyPanel.add(playerRoll);
        mainPanel.add(bodyPanel,BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new GridLayout(3,3));
        JButton rollButton = new JButton("Roll");
        rollButton.addActionListener(e -> model.play());
        footerPanel.add(rollButton);
        JButton buy = new JButton("Buy");
        JButton pass = new JButton("Pass");
        JButton nextTurn = new JButton("Next Turn");
        footerPanel.add(buy);
        footerPanel.add(pass);
        footerPanel.add(nextTurn);
        mainPanel.add(footerPanel, BorderLayout.PAGE_END);
        return mainPanel;

    }
    public void displayGUI(){
        this.setLayout(new BorderLayout());

        this.mainPanel.revalidate();
        this.playerPanel.revalidate();
        this.add(playerPanel, BorderLayout.LINE_END);
        this.add(mainPanel,BorderLayout.CENTER);



        this.revalidate();
        this.setVisible(true);
    }

    /**
     * This class handles the update to the view of the game class
     * @param e is a game event that holds useful information
     */
    @Override
    public void handleGameStatusUpdate(GameEvent e) {


        this.model = (GameModel) e.getSource();

        playerPanel = paintPlayerInfo(model.getActivePlayer(),e.getRoll());
        displayGUI();
        updatePlayerIcon(model.getActivePlayer());
    }
    private void updatePlayerIcon(Player activePlayer){
        int position = activePlayer.getPosition();

        if(activePlayer.getName().equals("P1")){

            squares.get(position).add(icon);

            System.out.println(squares.get(position).getComponents().length);;
        }else if(activePlayer.getName().equals("P2")){

            squares.get(position).add(icon2);
        }
        else if (activePlayer.getName().equals("P3")) {
            squares.get(position).add(icon3,2);
        }
        else if (activePlayer.getName().equals("P4")) {

            squares.get(position).add(icon4,2);
        }


    }


    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.displayGUI();

    }
}
