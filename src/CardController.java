import javax.swing.*;

public class CardController {
private GameModel model;

    public CardController(GameModel model){
      this.model = model;
    }


    public void buyProperty(GameFrame frame, String message) {
       int confirmed = JOptionPane.showConfirmDialog(frame, message, null, JOptionPane.YES_NO_OPTION);
        if(confirmed == JOptionPane.YES_OPTION){
            this.model.play(5);
        }
    }


    public void payRent(GameFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.PLAIN_MESSAGE);
    }

    public void confirmPass(GameFrame frame, String message) {
        int confirmed = JOptionPane.showConfirmDialog(null, message);
        if(confirmed == JOptionPane.YES_OPTION){
            this.model.play(3);
        }
    }
}
