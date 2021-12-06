import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class InternationalCards {
    private HashMap<Integer, Card> gameBoard;

    public InternationalCards(){
        gameBoard = new HashMap<>();
        createOriginalGameBoard();

    }

    public void serializeOriginalToXML(){
        File directory = new File("originalcards1\\");

        for(int i = 0; i < gameBoard.size(); i++){
            gameBoard.get(i).serializeToXML("originalcards1\\"+gameBoard.get(i).getName() + ".xml");
        }
    }

    public void serializeCustomToXML(){
        File directory = new File("internationalcards1\\");

        for(int i = 0; i < gameBoard.size(); i++){
            gameBoard.get(i).serializeToXML("internationalcards1\\"+gameBoard.get(i).getName() + ".xml");
        }
    }

    public void createOriginalGameBoard(){
        int position = 0;
        String[] streetNames = {"Go","Sparks Street","Lebreton Flats","Wellington Street","Laurier Avenue","Bytown&Prescott Railway",
                "Waller Street","Bronson Avenue","Jail","Hurdman Road","Canadian Pacific Railway","Lett Street","Lampman Crescent",
                "MacKay Street","Slater Street","Canadian National Railway","Thompson Street","SweetLand Avenue","Sloper Place",
                "Water Works", "New York Central Railway","Perly Drive","Morrison Street","Go to Jail","Keefer Street","McLeod Street","Parliament Hill",
                "Electric Company","Rideau Canal", "Street 21", "Street 22"};
        int[] costs = {0,60,60,100,100,200,120,180,0,180,200,200,220,220,240,200,260,260,280,100,200,300,300,0,320,350,400,200,420,450,500};

        int[] houseCosts = {0,50,50,50,50,50,50,50,0,100,100,100,100,100,100,150,150,150,150,150,150,150,150,0,200,200,200,200,200,200,200};

        int[] hotelCosts = {0,50,50,50,50,50,50,50,0,100,100,100,100,100,100,150,150,150,150,150,150,150,150,0,200,200,200,200,200,200,200};

        Color[] colors = {Color.white,new Color(247, 235, 98),new Color(247, 235, 98), //yellow
                new Color(55, 176, 201),new Color(55, 176, 201),Color.black,new Color(55, 176, 201), //light blue
                new Color(255, 128, 139),new Color(255, 59, 59), //red
                new Color(255, 128, 139),Color.black, //pink
                new Color(255, 128, 139),new Color(116, 83, 164),new Color(116, 83, 164),new Color(116, 83, 164), //purple
                Color.black, new Color(214, 223, 36),new Color(214, 223, 36),new Color(214, 223, 36), //green
                Color.white,Color.black, new Color(157, 201, 251),new Color(157, 201, 251), //light light blue
                new Color(255, 59, 59),new Color(157, 201, 251),new Color(199, 228, 207), //mint
                new Color(199, 228, 207),Color.white, new Color(199, 228, 207),
                new Color(255,163,77), new Color(255,163,77)}; //orange

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

    public void createCustomGameBoard(){
        int position = 0;
        String[] streetNames = {"Go","Albatross Avenue","Anchor Way","Barnacle Road","Bottom Feeder Lane","Oceanic Express Railway",
                "Conch Street","Coral Avenue","Jail","Intertidal Seaway","Plankton's Locomotive Railway","Seashell Street","Stormy Way",
                "Flying Dutchman's Ship","Squilliam's Tower","Mrs. Puff's Railway","Shell Emporium","Sandy's Treedome","Chum Bucket",
                "Water Works", "Gary's Railway","Patrick's Rock","Shelly Superhighway","Go to Jail","Jellyfish Gardens","Squidward's House",
                "Nautical Mart", "Electric Company","Ye Olde Shoppe", "King Neptune's Castle", "Krusty Krab"};
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

    public static HashMap<Integer, Card> deserializeFromXML(File directoryName){
        HashMap<Integer, Card> tempBoard = new HashMap<>();
        for(File f: directoryName.listFiles()){
            Card c = Card.deserializeFromXML(directoryName + "\\" + f.getName());
            tempBoard.put(c.getPosition(), c);
        }
        return  tempBoard;
    }

    public static void main(String[] args) {
        InternationalCards ic = new InternationalCards();
        //ic.serializeOriginalToXML();
       // ic.createCustomGameBoard();
        //ic.serializeCustomToXML();
        HashMap<Integer, Card> test = null;
        test = deserializeFromXML(new File("originalcards1\\"));
        for(Card c:test.values()){
            System.out.println(c.getName());
        }
    }
}
