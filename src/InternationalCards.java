import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class InternationalCards {
    private HashMap<Integer, Card> gameBoard;

    public InternationalCards(){
        gameBoard = new HashMap<>();
        createGameBoard();
    }

    public void serializeToXML(){
        File directory = new File("internationalcards1\\");

        for(int i = 0; i < gameBoard.size(); i++){
            gameBoard.get(i).serializeToXML("internationalcards1\\"+gameBoard.get(i).getName() + ".xml");
        }
    }

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

    public static void main(String[] args) {
        InternationalCards ic = new InternationalCards();
        ic.serializeToXML();
    }
}
