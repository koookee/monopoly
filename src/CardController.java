import javax.swing.*;

public class CardController {
private GameModel model;

    public CardController(GameModel model){
      this.model = model;
    }


    public int buyProperty(GameFrame frame, String message) {
       int n = JOptionPane.showConfirmDialog(frame, message, null, JOptionPane.YES_NO_OPTION);
       return n;

    }


    public void payRent(GameFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.PLAIN_MESSAGE);
    }

    public int confirmPass(GameFrame frame, String message) {
        int confirmed = JOptionPane.showConfirmDialog(null, message);
        return confirmed;
    }
}
