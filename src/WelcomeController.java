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

        Integer playerNum = (Integer) JOptionPane.showInputDialog(frame, "Pick number of real players",null, JOptionPane.QUESTION_MESSAGE, null,
                playerChoices, playerChoices[0]);

        Integer[] botChoices = {0, 1, 2, 3};
        Integer botNum = (Integer) JOptionPane.showInputDialog(frame, "Pick number of bots", null, JOptionPane.QUESTION_MESSAGE, null,
                botChoices,
                botChoices[0]);

        model.addPlayers(playerNum, botNum);

        return playerNum + botNum;

    }

}
