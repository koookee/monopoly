/**
 * @author Andre, Jack, Cassidy, Hussein
 */

import javax.swing.*;

public class CardController {
private GameModel model;

    /**
     * The constructor for the CardController class
     * @param model the game model that controls the logic
     */
    public CardController(GameModel model){
      this.model = model;
    }

    /**
     * Gives the player the option to buy a property
     * @param frame the game frame it's displayed on
     * @param message the String message inside the popup
     */
    public void buyProperty(GameFrame frame, String message) {
       int confirmed = JOptionPane.showConfirmDialog(frame, message, null, JOptionPane.YES_NO_OPTION);
        if(confirmed == JOptionPane.YES_OPTION){
            this.model.play(5);
        }
    }

    /**
     * Gives the player the option to buy a house
     * @param frame the game frame it's displayed on
     * @param message the String message inside the popup
     */
    public void buyHouse(GameFrame frame, String message){
        int confirmed = JOptionPane.showConfirmDialog(frame, message, null, JOptionPane.YES_NO_OPTION);
        if(confirmed == JOptionPane.YES_OPTION){
            this.model.play(6);
        }
    }

    /**
     * Tells the player that they have to pay rent
     * @param frame the game frame it's displayed on
     * @param message the String message inside the popup
     */
    public void payRent(GameFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Gives the player the option to confirm whether they want to pass or not
     * @param frame the game frame it's displayed on
     * @param message the String message inside the popup
     */
    public void confirmPass(GameFrame frame, String message) {
        int confirmed = JOptionPane.showConfirmDialog(null, message);
        if(confirmed == JOptionPane.YES_OPTION){
            this.model.play(3);
        }
    }
}
