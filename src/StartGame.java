import javax.swing.*;

/**
 * @author Evgeniy Karavaev
 */
public class StartGame extends JFrame {

    public StartGame(){
        setTitle("PingPong");
        setSize(800,600);
        setLocation(250,50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new PingPong());
        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->new StartGame());

    }
}
