import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.swing.JTextField;
import java.awt.KeyboardFocusManager;

public class TextFieldWithPrompt extends JTextField {

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
    
        if(getText().isEmpty() && ! (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)){
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setBackground(Color.BLACK);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.drawString("Player", 4, 32);
            g2.dispose();
        }
      }
}