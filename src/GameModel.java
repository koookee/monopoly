import java.util.HashMap;
import java.util.Map;

public class GameModel {
    private GameModel.Status status;
    private GameModel.Turn turn;


    private Map<Integer, Card> gameBoard;

    public static enum Status {

    }
    public static enum Turn{

    }

    public GameModel() {
        this.gameBoard = new HashMap();
    }

    public void createGameBoard(){

        String[] streetNames = {"Sparks Street","Lebreton Flats","wellington Street","lauier Avenue","waller Street","bronson Avenue","hurdman Road",
                "Lett Street","lampman Crescent","macKay Street","slater Street","thompson Street","sweetLand Avenue","sloper Place","perly Drive",
                "morrison Street","keefer Street","mcLeod Street","rochester Street","parliament Hill","rideau Canal"};
        int[] costs = {60,60,100,100,120,180,180,200,220,220,240,260,260,280,300,300,320,350,400};

        String[] colors = {"Brown","Brown","light blue","light blue","light blue","pink","pink","pink","orange","orange","orange","yellow","yellow",
                "yellow","green","green","green","blue","blue"};

        for (int i = 0; i < streetNames.length; i++) {
            gameBoard.put(i,new Card(streetNames[i],costs[i],colors[i]));
        }

    }
}
