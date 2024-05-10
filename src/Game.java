import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Game implements ActionListener, KeyListener{
    private GameView window;
    private static final int MAX_WIDTH = 800;		// Window size
    private static final int MAX_HEIGHT = 600;		// Window size
    private static final int TOP_OF_WINDOW = 22;
    private int gameState;
    private Player p1, p2;

    public Game(){
        p1 = new Player("Player 1", 1);
        p2 = new Player("Player 2", 2);
        gameState = 0;
        this.window = new GameView(MAX_WIDTH, MAX_HEIGHT, this);
        window.addKeyListener(this);
        p1.addWindow(window);
        p2.addWindow(window);
        Timer clock = new Timer(30, this);
        clock.start();
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

    public void actionPerformed(ActionEvent e)
    {

        window.repaint();
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
                p1.changeX(-15);
                break;
            case KeyEvent.VK_D:
                p1.changeX(15);
                break;
            case KeyEvent.VK_LEFT:
                p2.changeX(-15);
                break;
            case KeyEvent.VK_RIGHT:
                p2.changeX(15);
                break;
            case KeyEvent.VK_F:
                if((p2.getX()- p1.getX()) > 0 && (p2.getX()- p1.getX()) < 125){
                    if(p1.isCanHit() && !p2.isBlocking()) {
                        p2.damage();
                    }
                }
                if(p1.isCanHit()) {
                    p1.hit();
                    resetArm(p1);
                }
                break;
            case KeyEvent.VK_L:
                if((p2.getX()- p1.getX()) > 0 && (p2.getX()- p1.getX()) < 125){
                    if(p2.isCanHit() && !p1.isBlocking()) {
                        p1.damage();
                    }
                }
                if(p2.isCanHit()) {
                    p2.hit();
                    resetArm(p2);
                }
                break;
            case KeyEvent.VK_G:
                if(!p1.isBlocking()) {
                    p1.block();
                    resetArm(p1);
                }
                break;
            case KeyEvent.VK_K:
                if(!p2.isBlocking()) {
                    p2.block();
                    resetArm(p2);
                }
                break;
        }
        window.repaint();
        checkWin();
        window.repaint();
    }

    private void resetArm(Player player) {
        // Gradually reset arm angles after a delay
        new Thread(() -> {
            try {
                Thread.sleep(400);
                player.normal();
                window.repaint();
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void checkWin(){
        if(p2.isDead()){
            gameState = 1;
        }
        if(p1.isDead()){
            gameState = 2;
        }
    }

    public void draw(Graphics g){
        if(gameState == 0){
            g.setColor(Color.black);
            g.drawLine(0, 476, window.getWidth(), 476);
        }
        if(gameState == 1){
            g.setColor(Color.blue);
            g.fillRect(0, 0, window.getWidth(), window.getHeight());
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.PLAIN, 80));
            g.drawString("P1 Wins!", 250, 350);
        }
        if(gameState == 2){
            g.setColor(Color.red);
            g.fillRect(0, 0, window.getWidth(), window.getHeight());
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.PLAIN, 80));
            g.drawString("P2 Wins!", 250, 350);
        }
    }
}
