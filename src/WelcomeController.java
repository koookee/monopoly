/**
 * @author Andre, Jack, Cassidy, Hussein
 */

import javax.swing.*;
public class WelcomeController {
    /**
     * The constructor
     * @param frame the JFrame
     * @return the int number of players
     */
    public int getPlayerNumber(JFrame frame){
        Integer[] choices = {2, 3 ,4};
        Integer input = (Integer) JOptionPane.showInputDialog(frame, "Pick number of players",null, JOptionPane.QUESTION_MESSAGE, null,
                choices,
                choices[0]);

        return input;
    }
}
