/**
 * @author Andre, Jack, Cassidy, Hussein
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private GameModel model;

    /**
     * Constructor for the GameController class
     * @param model the game model that controls the logic
     */
    public GameController(GameModel model){
        this.model = model;
    }

    /**
     * The method that gets called when a button in the player panel is pressed
     * @param e is a game event that holds useful information
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // System.out.println("Working");
        String[] input = e.getActionCommand().split(" ");
        this.model.play(Integer.parseInt(input[0]));
    }

    /**
     * The popup for when a player goes bankrupt
     * @param frame the game frame it's displayed on
     * @param message the String message in the popup
     */
    public void bankruptcy(GameFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * The popup for when a player wins the game
     * @param frame the game frame it's displayed on
     * @param message the String message in the popup
     */
    public void winner(GameFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.PLAIN_MESSAGE);
    }
}
