import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    private GameView window;
    private static final int MAX_WIDTH = 800;		// Window size
    private static final int MAX_HEIGHT = 600;		// Window size
    private static final int TOP_OF_WINDOW = 22;

    private Player p1, p2;

    public Game(){
        p1 = new Player("Player 1", 1);
        p2 = new Player("Player 2", 2);
        this.window = new GameView(MAX_WIDTH, MAX_HEIGHT, this);
        window.addKeyListener(this);
    }

    public static void main(String[] args) {
        Game g = new Game();
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // Nothing required for this program.
        // However, as a KeyListener, this class must supply this method
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nothing required for this program.
        // However, as a KeyListener, this class must supply this method
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // The keyCode lets you know which key was pressed
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                p1.changeX(-5);
                break;
            case KeyEvent.VK_D:
                p1.changeX(5);
                break;
            case KeyEvent.VK_LEFT:
                p2.changeX(-5);
                break;
            case KeyEvent.VK_RIGHT:
                p2.changeX(5);
                break;
        }
        window.repaint();
    }
}
