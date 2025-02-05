import java.awt.*;
import javax.swing.*;


public class Frame extends JFrame {

    Panel panel;

    Frame(){
        panel = new Panel();
        this.add(panel);
        this.setTitle("PING-PONG");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    
}
