import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameView extends JFrame{
    private int windowWidth, windowHeight;

    private Game game;

    private Player p1, p2;
    public GameView(int width, int height, Game game){
        windowWidth = width;
        windowHeight = height;

        this.game = game;

        this.p1 = game.getP1();
        this.p2 = game.getP2();

        // Show the window with the ball in its initial position.
        this.setTitle("Stickman Boxing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
        createBufferStrategy(2);
    }
    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;

        Graphics g2 = null;

        try {
            g2 = bf.getDrawGraphics();
            // myPaint does the actual drawing, as described in ManyBallsView
            myPaint(g2);
        } finally {
            // It is best to dispose() a Graphics object when done with it.
            g2.dispose();
        }

        // Shows the contents of the backbuffer on the screen.
        bf.show();

        //Tell the System to do the Drawing now, otherwise it can take a few extra ms until
        //Drawing is done which looks very jerky
        Toolkit.getDefaultToolkit().sync();
    }
    public void myPaint(Graphics g) {
        // Clear the window.
        g.setColor(Color.white);
        g.fillRect(0, getInsets().top, getWidth(), getHeight());
        p1.draw(g);
        p2.draw(g);
        game.draw(g);
    }



}
