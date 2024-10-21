import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        new GameFrame();

        // Create an instance of GamePanel
        GamePanel gamePanel = new GamePanel();

        // Add the game panel to the frame
        frame.add(gamePanel);

        // Set frame settings
        frame.setTitle("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);  // Makes sure the window size is fixed
        frame.pack();  // Sizes the frame based on the preferred size of the GamePanel
        frame.setVisible(true);  // Make the frame visible
        frame.setLocationRelativeTo(null);  // Centers the window on the screen
    }
}