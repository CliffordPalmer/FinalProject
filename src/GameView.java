import javax.swing.*;
import java.awt.*;

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
    }

    public void paint(Graphics g) {
        // Clear the window.
        g.setColor(Color.white);
        g.fillRect(0, getInsets().top, getWidth(), getHeight());
        p1.draw(g);
        p2.draw(g);
    }



}
