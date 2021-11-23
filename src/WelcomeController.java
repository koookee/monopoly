/**
 * @author Andre, Jack, Cassidy, Hussein
 */

import javax.swing.*;
public class WelcomeController {
    private GameModel model;
    public WelcomeController(GameModel model){
        this.model = model;
    }
    public int getPlayerNumber(JFrame frame){
        Integer[] playerChoices = {1, 2, 3 ,4};
        Integer[][] botChoicesChoices= {{1,2,3}, {0,1,2}, {0,1}, {0}};
        Integer[] botChoices;
        

        Integer playerNum = (Integer) JOptionPane.showInputDialog(frame, "Pick number of real players",null, JOptionPane.QUESTION_MESSAGE, null,
                playerChoices, playerChoices[0]);
        
        if(playerNum == 1){
            botChoices = botChoicesChoices[0];
        }
        else if(playerNum == 2){
            botChoices = botChoicesChoices[1];
        }
        else if(playerNum == 3){
            botChoices = botChoicesChoices[2];
        }
        else{
            botChoices = botChoicesChoices[3];
        }

        Integer botNum = (Integer) JOptionPane.showInputDialog(frame, "Pick number of bots", null, JOptionPane.QUESTION_MESSAGE, null,
                botChoices,
                botChoices[0]);

        model.addPlayers(playerNum, botNum);


        return playerNum + botNum;

    }

}
