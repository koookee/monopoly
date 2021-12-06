/**
 * @author Andre, Jack, Cassidy, Hussein
 */

import javax.swing.*;

public class CardAnnouncements {
private GameModel model;

    /**
     * The constructor for the CardController class
     * @param model the game model that controls the logic
     */
    public CardAnnouncements(GameModel model){
      this.model = model;
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
        }
    }

    public void payJailFee(GameFrame frame, String message){
        int confirm = JOptionPane.showConfirmDialog(frame, message, null, JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            model.getActivePlayer().setIsInJail(0);
            model.getActivePlayer().setMoney(model.getActivePlayer().getMoney() - 50);
        }
    }

    /**
     * Announces that the player is going to jail
     * @param frame the game frame it's displayed on
     * @param message the String message inside the popup
     */
    public void announceToJail(GameFrame frame, String message){
        System.out.println("CARD CONTROLLER");
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.PLAIN_MESSAGE);
    }

    public void announceDouble(GameFrame frame, String message){
        model.getActivePlayer().setIsInJail(0);
        model.getActivePlayer().setExconvict(true);
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.PLAIN_MESSAGE);

    }
    public void announceJailTime(GameFrame frame, String message){
        System.out.println("JAILTIME CONTROLLER");
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.PLAIN_MESSAGE);
    }

    public void announcePassGo(GameFrame frame, String message){
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.PLAIN_MESSAGE);
    }
}
