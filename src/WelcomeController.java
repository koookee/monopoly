/**
 * @author Andre, Jack, Cassidy, Hussein
 */

import javax.swing.*;
import java.io.File;

public class WelcomeController {
    private GameModel model;
    public WelcomeController(GameModel model){
        this.model = model;
    }

    public int[] askPlayerImport(JFrame frame){
        int numBots =0;
        int numPlayer = 0;

        File directory = new File("xml folder\\");
        if (!directory.exists())
            directory.mkdirs();
        if (directory.listFiles().length != 0){
            int importYesNo = JOptionPane.showOptionDialog(frame, "Do you want to load the last save", null, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,null, new Object[] { "Yes", "No" }, JOptionPane.YES_OPTION);
            File[] files = directory.listFiles();
            for (File f :
                    files) {
                if (f.getName().contains("Bot")) numBots++;
                else numPlayer++;
            }
            if (importYesNo == JOptionPane.YES_OPTION){

                model.addPlayers(numPlayer,numBots);
                return new int[]{numBots+numPlayer,0};

            }else{
                int[] playersAndBots = getPlayerNumber(frame);
                if (playersAndBots[0] == numPlayer && playersAndBots[1] == numBots) {

                    return new int[]{playersAndBots[0] + playersAndBots[1], 0};
                }
                else
                    return new int[] {playersAndBots[0]+ playersAndBots[1], 1};

            }
        }
        else{
            int[] playersAndBots = getPlayerNumber(frame);
            return new int[] {playersAndBots[0]+ playersAndBots[1], 1};
        }


    }

    public int[] getPlayerNumber(JFrame frame){
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


        return new int[] {playerNum , botNum};

    }
}
