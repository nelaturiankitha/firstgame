import javax.swing.*;
import java.awt.*; //The Java library includes a simple package for drawing 2D graphics, called java. awt.
// AWT stands for “Abstract Window Toolkit”.

public class GamePanel extends JPanel implements Runnable { //works like a game screen
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16
    //modern computers have very high resolution so 16x16 looks very small so we have to scale them
    final int scale = 3 ;
    final int tileSize = originalTileSize * scale; // final keyword makes the variable immutable tilesize = 48x48
    final int maxScreenCol = 16;
    final int getMaxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * getMaxScreenRow; //576 pixels
    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // using this we will make time in our game, to use this we need to implement runnable

    //set player's default position
    int playerX = 100,playerY = 100, playerSpeed =4;
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true); //if it is true all the drawing from this component will be done in an offscreen painting buffer.
        //enabling this can improve game's rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { // it is created automatically when we implemented runnable
        // here we will make our game loop

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            // UPDATE : update info like character position
            update();
            // DRAW : draw the screen with the updates information
            repaint();
//            Calling object.paintComponent(g) is an error.
//            Instead this method is called automatically when the panel is created.
//            The paintComponent() method can also be called explicitly by the repaint() method defined in Component class.
//            The effect of calling repaint() is that Swing automatically clears the graphic on the panel and executes
//            the paintComponent method to redraw the graphics on this panel.

            try {
                double remainingTime =  nextDrawTime - System.nanoTime(); // we want to know how much time is been used to update and repaint
                remainingTime = remainingTime/1000000;

                if(remainingTime<0){
                    remainingTime =0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void update(){
        if(keyH.upPressed == true){
            playerY -= playerSpeed;
        }
        else if(keyH.downPressed == true){
            playerY += playerSpeed;
        }
        else if(keyH.leftPressed == true){
            playerX -= playerSpeed;
        }
        else if(keyH.rightPressed == true){
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //graphis 2d extends graphics class to gain better control
        g2.setColor(Color.blue);
        g2.fillRect(playerX,playerY,48,48);
        g2.dispose();
    }
}
