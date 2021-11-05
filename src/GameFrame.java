import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.jar.JarEntry;

public class GameFrame extends JFrame implements GameView {

    private GameModel model;
    private Map<Integer,Card> board;
    private ArrayList<JPanel> squares;

    public GameFrame(){
        super("Monopoly");
        this.model = new GameModel();
        model.addGameModelView(this);
        this.squares = new ArrayList<>();
        this.board = model.getGameBoard();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600,1024);
        this.createSquares();

    }

    private void createSquares(){
        for (Card c :
                this.model.getGameBoard().values()) {
            JPanel square = new JPanel(new BorderLayout());
            square.setBorder(new LineBorder(Color.black));
            square.setPreferredSize(new Dimension(150,200));
            JPanel squareTop = new JPanel();
            squareTop.setBackground(c.getColor());
            squareTop.add(new JLabel(c.getName()));
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
            if(i<=5){
                bot.add(squares.get(5-i));
            }else if(i>5 && i<=11){
                left.add(squares.get(i));
            }
            else if(i>11 && i<=16){
                top.add(squares.get(i));
            }
            else if(i>16 && i<=22){
                right.add(squares.get(i));
            }
        }
        return mainPanel;
    }
    public void displayGUI(){
        this.setLayout(new BorderLayout());
        JPanel mainPanel = paintBoard();
        this.add(mainPanel,BorderLayout.CENTER);

        this.setVisible(true);
    }

    @Override
    public void handleGameStatusUpdate(GameEvent e) {

    }

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();

        gameFrame.displayGUI();
    }
}
