import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (running) {
            /*
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            } */
            // Draw the apple
            g.setColor(Color.RED);  // Apple is red
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);  // Draw the apple at the current coordinates

            // Draw the snake
            for (int j = 0; j < bodyParts; j++) {
                if (j == 0) {  // Head of the snake
                    g.setColor(Color.GREEN);
                    g.fillRect(x[j], y[j], UNIT_SIZE, UNIT_SIZE);
                } else {  // Body of the snake
                    g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));  // Random colors for the body
                    g.fillRect(x[j], y[j], UNIT_SIZE, UNIT_SIZE);
                }
            }

            // Draw the score
            g.setColor(Color.BLUE);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics fm = g.getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fm.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);  // Game over screen
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
                case 'D':
                    y[0] = y[0] + UNIT_SIZE;
                    break;
                    case 'L':
                        x[0] = x[0] - UNIT_SIZE;
                        break;
                        case 'R':
                            x[0] = x[0] + UNIT_SIZE;
                            break;
        }

    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public void checkCollisions(){
        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
// Check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
// Check if head touches right border
        if (x[0] >= SCREEN_WIDTH) {  // Use '>=' to handle screen edges
            running = false;
        }
// Check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
// Check if head touches bottom border
        if (y[0] >= SCREEN_HEIGHT) {  // Use '>=' to handle screen edges
            running = false;
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.BLUE);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics fm = g.getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - fm.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        g.setColor(Color.BLUE);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics fm2 = g.getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - fm.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            checkApple();
            checkCollisions();

        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                    case KeyEvent.VK_RIGHT:
                        if(direction != 'L') {
                            direction = 'R';
                        }
                        break;
                        case KeyEvent.VK_UP:
                            if(direction != 'D') {
                                direction = 'U';
                            }
                            break;
                            case KeyEvent.VK_DOWN:
                                if(direction != 'U') {
                                    direction = 'D';
                                }
                                break;
            }
        }
    }

}