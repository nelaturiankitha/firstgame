import javax.swing.*; //this library is required to make the Jframe

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Play when leg broken");
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null); //the window will be displayed at the center of screen
        window.setVisible(true); //to see this window
        gamePanel.startGameThread();

    }
}