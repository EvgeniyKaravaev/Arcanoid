import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Evgeniy Karavaev
 */

public class PingPong extends JPanel implements KeyListener, ActionListener {

    private int x_rect = 500;
    private int y_rect = 500;
    private int size_width = 150;
    private int size_heigth = 20;

    private int delay = 33;
    private final int SHAG = 50;
    private int count = 1;
    private Random random;
    private Timer timer;
    private JLabel label;
    private JLabel lb;
    private ArrayList<Ball> balls;
    private JButton btn;

    public PingPong(){
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.GREEN,3));
        addKeyListener(this);
        setFocusable(true);
        btn = new JButton();
        btn.setText("Start");
        btn.setVisible(true);
        add(btn);
        random = new Random();
        timer = new Timer(delay,this);
        //timer.start();
        label = new JLabel("Игра окончена!");
        label.setFont(new Font("Serif",Font.ITALIC,34));
        label.setForeground(Color.RED);
        label.setVisible(false);
        lb = new JLabel();
        lb.setFont(new Font("Serif",Font.BOLD,24));
        lb.setForeground(Color.ORANGE);
        add(label);
        add(lb);
        balls = new ArrayList<>();
        ballStart();
        btn.addActionListener((e) -> timer.start());
        btn.addActionListener((e) -> setFocusable(true));
        btn.addActionListener((e) -> btn.setVisible(false));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        if(x_rect < 0 ) x_rect = 0;
        if(y_rect < 0 ) y_rect = 0;
        if(x_rect + size_width >= getWidth()) x_rect = getWidth() - size_width;
        if(y_rect + size_heigth >= getHeight()) y_rect = getHeight() - size_heigth;
        g.fillRect(x_rect,y_rect,size_width,size_heigth);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == 37) x_rect -= SHAG;
        if(code == 39) x_rect += SHAG;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ball b: balls) {
            b.moveBalls();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Ball b : balls) {
            b.draw(g);
        }
    }

    public void ballStart(){
        for(int i = 0;i < 2;i ++){
            int x = random.nextInt(800);
            int y = random.nextInt(350);
            int size = 45;
            balls.add(new Ball(x,y,size));
        }
        repaint();
    }

    private class Ball{

        private int x;
        private int y;
        private int size;
        private Color color;
        private int x_speed,y_speed;
        private final int SPEED = 5;

        public Ball(int x, int y, int size){
            this.x = x;
            this.y = y;
            this.size = size;
            color = new Color(random.nextInt(128),
                    random.nextInt(128),random.nextInt(128));
            x_speed = random.nextInt(SPEED * 2 + 1) - SPEED;
            y_speed = random.nextInt(SPEED * 2 + 1) - SPEED;

        }

        public void draw(Graphics graphics){
            graphics.setColor(color);
            graphics.fillOval(x - size/2,y - size/2,size,size);
            graphics.setColor(color);
        }

        private void moveBalls() {
        x += x_speed;
        y += y_speed;
        if(x - size/2 <= 0 || x + size/2 >= getWidth()) x_speed = -x_speed;
        if(y - size/2 <= 0 || y + size/2 >= getHeight()) y_speed = -y_speed;
        if(y + size/2 - 1 >= y_rect && y + size/2 - 1 <= y_rect + size_heigth - 1
                && x + size/2 - 1 >= x_rect && x <= x_rect + size_width - 1){
            lb.setText("Результат: " + count++);
            if(size_width >= 90 && size_width <= 150 ) size_width -= 5;
            x_speed += 1;
            y_speed += 1;
            y_speed *= -1;
        }
        if (y + size/2 >= getHeight()){
            timer.stop();
            lb.setVisible(false);
            label.setVisible(true);

        }
        }
    }
}

